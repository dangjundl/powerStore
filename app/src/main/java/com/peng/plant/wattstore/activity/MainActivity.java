package com.peng.plant.wattstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peng.plant.wattstore.data.AppData;
import com.peng.plant.wattstore.data.DEFINE;
import com.peng.plant.wattstore.data.offlineAppinfos;
import com.peng.plant.wattstore.data.TiltScrollController;
import com.peng.plant.wattstore.listener.CenterScrollListener;
import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.adapter.recyclerView_adapter;
import com.peng.plant.wattstore.manager.AppVersionCompare;
import com.peng.plant.wattstore.manager.GalleryLayoutManager;
import com.peng.plant.wattstore.manager.ScrollZoomLayoutManager;
import com.peng.plant.wattstore.view.AppInfoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/* 1.1???????????? ??????????????? ????????? ??????????????? ????????? ??????????????????
   2.gif???????????? 1???????????? gif????????? ??????
   3.2??????,3?????? ?????? ?????? ????????? ??????
   4.??????????????? onresume???????????? onPause??? ????????? ????????????????????? ????????? ????????? ?????????
   5.xml????????? 3??????????????? ?????? ???????????? ???????????? dimen???? ???????????? ?????????????????? ???????????? ???????????????????????? ????????? ?????? ???????????????????????? ????????????
*/


public class MainActivity extends AppCompatActivity implements TiltScrollController.ScrollListener {
    private static final String TAG = "Daengjun";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd"); // ?????? ?????? ??????
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridlayout_two, mGridlayout_three;
    private TextView calender, solt_input_txt, developer_mode_on, developer_mode_off;
    private Button canclebutton, solt_dialog_apply_btn, solt_dialog_reset_btn, test_btn;
    private Animation animShow, animHide, fadeInAnim_in, fadeInAnim_out;
    private View line_solt;
    private GalleryLayoutManager galleryLayoutManager;
    private ScrollZoomLayoutManager scrollZoomLayoutManager;
    private RadioGroup line_num_check;
    private RecyclerView.LayoutManager layoutManager;
    private String toast;
    private RelativeLayout layout;
    private ConcurrentHashMap<Integer, AppData> apps = new ConcurrentHashMap<>();
    private TiltScrollController mTiltScrollController;
    private int layout_num = 1;
    private static String POWERSTORE_OFFLINE_MODE = "powerstore_offline_mode";
    private ImageView image_example2_anim;
    String json = "";


    @Override
    public void onTilt(int x, int y, float deltaX) {

        if (Math.abs(deltaX) > 1.8) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 3, 0);
        } else if (Math.abs(deltaX) > 1.4) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 7, 0);
        } else if (Math.abs(deltaX) > 1.0) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 20, 0);
        } else
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth() / 3000), 0);
    }

    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume: ====================================");
//        eventHandler.sendEmptyMessageDelayed(SignalingSendData.SCROLL_DELAY, 1000);
        mTiltScrollController.requestAllSensors();
////        languageChange();
////        mTiltScrollController.requestAllSensors();
////        Locale mSysLocale = this.getResources().getConfiguration().locale;
////        strLanguage = mSysLocale.getLanguage();
////        if (strLanguage.equals("ko")) {
////            tvEnPage.setVisibility(View.GONE);
////            tvKrPage.setVisibility(View.VISIBLE);
////        } else {
////            tvEnPage.setVisibility(View.VISIBLE);
////            tvKrPage.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mTiltScrollController.releaseAllSensors();
    }


//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        Log.d(TAG, "onBackPressed");
//    }
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findviewid ??????
        findview_id();

        //json????????? ??????
        getJsonString();
        jsonParsing(json);

        //??????????????? ??????
        initAnimation();

        //????????????????????? ?????????
        top_transparent();

        // ????????? landscape(??????) ???????????? ???????????? ?????? ??????
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //?????? ????????? ?????? ??????
        mTiltScrollController = new TiltScrollController(getApplicationContext(), this);
        SnapHelper snapHelper;
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);


        calender = (TextView) findViewById(R.id.calender_text);
        Date date = new Date();
        String time = mFormat.format(date);
        calender.setText(time);

        //????????? ????????? ?????????
//        myPackageManager = getPackageManager();

        //???????????? ????????? ?????????????????????????????? ??????
        mRecyclerView.setHasFixedSize(true);

        canclebutton = (Button) findViewById(R.id.solt_dialog_cancle);


//        Intent intent = new Intent(Intent.ACTION_MAIN, null);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> intentList = myPackageManager.queryIntentActivities(intent, 0);

        configureRecyclerView();
        recyclerView_adapter myAdapter = new recyclerView_adapter(apps, this, layout_num);
        mRecyclerView.setAdapter(myAdapter);
//        mRecyclerView.scrollToPosition(2); // ????????? ??????????????? ?????? ????????? ??????

        //???????????? ??????
        solt_dialog_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });

        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_num_check.clearCheck();
                mRecyclerView.startAnimation(fadeInAnim_out);
                mRecyclerView.setVisibility(View.INVISIBLE);
                line_solt.setVisibility(View.VISIBLE);
                line_solt.startAnimation(animShow);

            }
        });

        line_num_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.one_line_btn:
                        layoutManager = galleryLayoutManager;
                        layout_num = 0;
                        toast = "1line?????? ?????????????????????";
                        image_example2_anim.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        line_solt_image(0);
                        break;
                    case R.id.two_line_btn:
                        layoutManager = mGridlayout_two;
                        layout_num = 1;
                        toast = "2line?????? ?????????????????????";
                        image_example2_anim.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        line_solt_image(1);
                        break;
                    case R.id.three_line_btn:
                        layoutManager = mGridlayout_three;
                        layout_num = 2;
                        toast = "3line?????? ?????????????????????";
                        image_example2_anim.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        line_solt_image(2);
                        break;

                }

            }
        });

        //???????????? ??????
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solt_line_close();
                image_example2_anim.setScaleType(ImageView.ScaleType.FIT_XY);
                line_solt_image(-1);

            }
        });


        //????????? ??????
        solt_dialog_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_num = 0;
                toast = "??????????????? ?????????????????????";
                line_reset(layout_num, toast);
                Log.d(TAG, "onClick: apps" + apps);
                image_example2_anim.setScaleType(ImageView.ScaleType.FIT_XY);
                line_solt_image(-1);


            }
        });


        //?????? ?????????
        developer_mode_on.setOnClickListener(MainonClickListener);
        developer_mode_off.setOnClickListener(MainonClickListener);
        solt_input_txt.setOnClickListener(MainonClickListener);

        //????????? ?????? ???????????? ??????
        myAdapter.setOnItemClickListener(new recyclerView_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                AppData cleckedResolveInfo =
                        apps.get(pos);
                openApp(getApplicationContext(), cleckedResolveInfo.appPackageName);

//                AppData cleckedResolveInfo =
//                        apps.get(pos);
//                ActivityInfo clickedActivityInfo =
//                        cleckedResolveInfo.;
//
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setClassName(
//                        clickedActivityInfo.applicationInfo.packageName,
//                        clickedActivityInfo.name);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                startActivity(intent);
//
//
//                ComponentName compName = new ComponentName("com.home.myapp","com.home.myapp.CMyapp");
//
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
////Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP
////??????????????? ???????????? ??????????????? ????????? ????????? ?????? ?????? ACTIVITY ?????? ?????? ???????????????.
//
//                intent.setComponent(compName);
//                intent.putExtra("psj_test", "this is test"); //?????????
//                startActivity(intent);


            }
        });

    }


    private View.OnClickListener MainonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.solt_global_txt:
                    //????????? ?????????
                    line_num_check.clearCheck();
                    line_solt.setVisibility(View.VISIBLE);
                    line_solt.startAnimation(animShow);
                    break;

                case R.id.developer_mode:
                    changeDeveloperMode(true);
                    break;
                case R.id.developer_mode_off:
                    changeDeveloperMode(false);
                    break;

            }
        }
    };


    private void padding_control(int layout_num) {

        if (layout_num == 0) {
            mRecyclerView.setPadding(0, 0, 0, 0);

        } else if (layout_num == 1) {
            mRecyclerView.setPadding(70, 70, 70, 70);

        } else {
            mRecyclerView.setPadding(30, 30, 30, 30);

        }

    }


    //?????? ????????? ??????
    private void configureRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerView);

        //        circleLayoutManager = new CircleLayoutManager(this, true);
//        circleZoomLayoutManager = new CircleZoomLayoutManager(this, true);

        galleryLayoutManager = new GalleryLayoutManager(this, Dp2px(10));
        mGridlayout_two = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        mGridlayout_three = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.addOnScrollListener(new CenterScrollListener()); //????????? ??????
        mRecyclerView.setLayoutManager(galleryLayoutManager);

    }

    private int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //dialog ??????????????? ??????
    private void initAnimation() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
        //?????????????????? ????????? ??? ,?????? ??????
        fadeInAnim_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeInAnim_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
    }

    //????????? ?????? ??????
    private void top_transparent() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }


    boolean isDeveloperMode = false;

    //????????? ?????? , ?????? ?????????????????? ??????
    private void changeDeveloperMode(boolean isDeveloper) {
        if (isDeveloper) {
            layout.setBackgroundColor(0xff000000);
        } else {
            layout.setBackgroundResource(R.drawable.store_bg);
        }
        isDeveloperMode = isDeveloper;
//        sendCommands();
    }


    private void solt_line_close() {

        line_solt.startAnimation(animHide);
        line_solt.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }


    private void line_reset(int num, String toast_msg) {
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(10));

        layout_num = num;
        toast = toast_msg;
        layoutManager = scrollZoomLayoutManager;
        padding_control(layout_num);
        mRecyclerView.setLayoutManager(layoutManager);
        recyclerView_adapter myAdapter = new recyclerView_adapter(apps, getApplicationContext(), layout_num);
        mRecyclerView.setAdapter(myAdapter);
        solt_line_close();
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();


    }


    private void findview_id() {

        line_solt = findViewById(R.id.line_solt_dialog);
        mRecyclerView = findViewById(R.id.home_recyclerView);
        line_num_check = (RadioGroup) findViewById(R.id.line_radioGroup);
        solt_dialog_apply_btn = (Button) findViewById(R.id.solt_dialog_apply);
        solt_dialog_reset_btn = (Button) findViewById(R.id.solt_dialog_reset);
        solt_input_txt = (TextView) findViewById(R.id.solt_global_txt);
        test_btn = (Button) findViewById(R.id.test_test_test);
        developer_mode_on = (TextView) findViewById(R.id.developer_mode);
        developer_mode_off = (TextView) findViewById(R.id.developer_mode_off);
        layout = (RelativeLayout) findViewById(R.id.relativelayout_main);
        image_example2_anim = (ImageView) findViewById(R.id.image_example2);
    }

    //?????? ???????????????
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("??????????????? ???????????????????").setMessage(layout_num + 1 + " Line ??????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        padding_control(layout_num);
                        mRecyclerView.setLayoutManager(layoutManager);
                        recyclerView_adapter myAdapter = new recyclerView_adapter(apps, getApplicationContext(), layout_num);
                        mRecyclerView.setAdapter(myAdapter);
                        solt_line_close();
                        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    //json ?????? ????????????
    private String getJsonString() {


        try {
            InputStream is = getAssets().open("lists.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }


    private void jsonParsing(String json) {
        getJsonString();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray movieArray = jsonObject.getJSONArray("lists");

            for (int app_pos = 0; app_pos < movieArray.length(); app_pos++) {
                JSONObject movieObject = movieArray.getJSONObject(app_pos);
                AppData data = new AppData();
                data.position = app_pos;
                data.appNameKor = movieObject.getString("appnamekor");
                data.appNameEng = movieObject.getString("appnameeng");
                data.appPackageName = movieObject.getString("apppkgname");
                data.appUpdateVersion = movieObject.getString("appversion");

                apps.put(app_pos, data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //?????????????????? ????????????
    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void line_solt_image(int img_num) {

        switch (img_num) {
            case -1:
                Glide.with(getApplicationContext())
                        .load(R.drawable.android_logo)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(image_example2_anim);
                break;
            case 0:
                Glide.with(getApplicationContext())
                        .load(R.drawable.first)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(image_example2_anim);
                break;
            case 1:
                Glide.with(getApplicationContext())
                        .load(R.drawable.animation2)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(image_example2_anim);
                break;
            case 2:
                Glide.with(getApplicationContext())
                        .load(R.drawable.animation3)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(image_example2_anim);
                break;

        }
    }


//    private String getVersionInfo(String packageName) {
//        String version = "Unknown";
//        PackageInfo packageInfo;
//        try {
//            packageInfo = getApplicationContext()
//                    .getPackageManager()
//                    .getPackageInfo(packageName, 0);
//            version = packageInfo.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
////            Log.d(TAG, "Noah getVersionInfo :" + e.getMessage());
//            version = "0.0.0";
//        }
//        return version;
//    }
//
//    private boolean getInstallPackage(String packagename) {
//        try {
//            PackageManager pm = getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(packagename.trim(), PackageManager.GET_META_DATA);
//            ApplicationInfo appInfo = pi.applicationInfo;
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//    private void drawApps(AppData data) {
//        // set data //
//        data.appCurrentVersion = getVersionInfo(data.appPackageName).replaceAll("-", "");
//        data.isInstalled = getInstallPackage(data.appPackageName);
////        Log.d(TAG , "Noah pkg: " + data.appPackageName + " curr: " + data.appCurrentVersion + " next: " + data.appUpdateVersion);
//
//        try {
//            data.isNewVersion = new AppVersionCompare().compare(data.appCurrentVersion, data.appUpdateVersion);
//        } catch (NumberFormatException e) {
//            data.appCurrentVersion = "0.0.0";
//            data.isNewVersion = -1;
//        }
//
//        // draw //
//        apps.put(data.position, data);
//        mTiltScrollController.setAppCount(apps.size());
//
//        AppInfoView appInfoView = drawAppByOrder(data);
//
//        downloadAppsIcon(data, appInfoView.getResourceAppIcon());
//    }
//
//    private AppInfoView drawAppByOrder(AppData data) {
//        AppInfoView appInfoView = new AppInfoView(getApplicationContext(), this,MainonClickListener);
//
//        appInfoView.setData(data);
////        Log.d(TAG , "Noah data pos: " + data.position);
//
//        layout.addView(appInfoView);
//
//        return appInfoView;
//    }
//
//    public void downloadAppsIcon(AppData data, ImageView view) {
//        String path;
//        if (data.isInstalled)
//            path = String.format("%sicon_%s.png", DEFINE.SERVER_LOGO_DOWNLOAD_URL, data.appNameEng.toLowerCase());
//        else
//            path = String.format("%sicon_%s_off.png", DEFINE.SERVER_LOGO_DOWNLOAD_URL, data.appNameEng.toLowerCase());
//
////        Log.d(TAG , "Noah downloadLogo path: " + path);
//        Glide.with(this)
//                .load(path)
//                .error(R.drawable.icon_default)
//                .into(view);
//    }
//
//    private void changeOfflineMode() {
//        Message msg = new Message();
//        msg.what = 500;
//        msg.obj = new offlineAppinfos().get();
//        eventHandler.sendMessage(msg);
//    }
//
//    public Handler eventHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            AppData data;
//            int result;
//            switch (msg.what) {
//                case 0:
//                    data = (AppData) msg.obj;
//                    drawApps(data);
//                    break;
//
//                /** Dialog Message box*/
//
//                case 500:
//                    if (getOfflineMode()) {
//                        changeOfflineMode();
//                    }
//
//                case 503: // onGetAppLists
//                    ConcurrentHashMap<Integer, AppData> appLists = new ConcurrentHashMap<Integer, AppData>((ConcurrentHashMap<Integer, AppData>) msg.obj);
//                    if (apps != null) apps.clear();
////                    Log.d(TAG , "Noah aaa : "+ appLists.size() + " / " + apps.size());
//                    for (int i = 0; i < appLists.size(); i++) {
//                        addApplicationUI(appLists.get(i));
//                    }
//                    eventHandler.sendEmptyMessageDelayed(1000, 500);
//                    break;
//
//
//            }
//        }
//
//
//    };

//    public void onGetAppLists(ConcurrentHashMap<Integer , AppData> data)
//    {
////            Log.d(TAG , "Noah onGetAppLists data len: " + data.size());
//        Message msg = new Message();
//        msg.what = 503;
//        msg.obj = data;
//        eventHandler.sendMessage(msg);
//    }


//    private boolean getOfflineMode() {
//        SharedPreferences prefs = getSharedPreferences(POWERSTORE_OFFLINE_MODE, MODE_PRIVATE);
//        return prefs.getBoolean("offline_mode", false);
//    }
//
//
//    private void addApplicationUI(AppData data) {
////        homeAppLayout.removeAllViews();
////        layout.removeAllViews();
//        Message msg = new Message();
//        msg.what = 0; // drawApps();
//        Log.d(TAG, "addApplicationUI: "+data);
//        msg.obj = data;
//        eventHandler.sendMessageDelayed(msg, 100); // ?????? ?????? ??????
//
//    }


}


