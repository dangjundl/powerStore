package com.peng.plant.wattstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.plant.wattstore.data.TiltScrollController;
import com.peng.plant.wattstore.listener.CenterScrollListener;
import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.adapter.recyclerView_adapter;
import com.peng.plant.wattstore.manager.GalleryLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TiltScrollController.ScrollListener {
    private static final String TAG = "Daengjun";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd"); // 날짜 포맷 형식
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridlayout_two, mGridlayout_three;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView calender,test_one;
    private PackageManager myPackageManager;
    private Button canclebutton, solt_dialog_apply_btn,test_btn;
    private Animation animShow, animHide;
    private View line_solt;
    private GalleryLayoutManager galleryLayoutManager;
    private RadioGroup line_num_check;
    private RecyclerView.LayoutManager layoutManager;
    private String toast;
    private TiltScrollController mTiltScrollController;
    private int layout_num = 1;


    @Override
    public void onTilt(int x, int y, float deltaX) {

        if (Math.abs(deltaX) > 1.8) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 3, 0);
        } else if (Math.abs(deltaX) > 1.4) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 7, 0);
        } else if (Math.abs(deltaX) > 1.0) {
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()) / 20, 0);
        }
        else
            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth() / 3000), 0);
    }



        //        if (Math.abs(deltaX) > 0.6) {
//            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth()), 0);
//        } else
//            mRecyclerView.smoothScrollBy(x * (galleryLayoutManager.getEachItemWidth() / 6), 0);



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
        line_solt = findViewById(R.id.line_solt_dialog);
        mRecyclerView = findViewById(R.id.home_recyclerView);
        line_num_check = (RadioGroup) findViewById(R.id.line_radioGroup);
        solt_dialog_apply_btn = (Button) findViewById(R.id.solt_dialog_apply);
        test_one = (TextView) findViewById(R.id.text_text);
        test_btn = (Button) findViewById(R.id.test_test_test);

        initAnimation();
        //갤러리 레이아웃매니저 객체 생성
        top_transparent();
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //틸트 컨트롤 객체 생성
        mTiltScrollController = new TiltScrollController(getApplicationContext(), this);
        SnapHelper snapHelper;
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);


        calender = (TextView) findViewById(R.id.calender_text);
        Date date = new Date();
        String time = mFormat.format(date);
        calender.setText(time);

        //패키지 권한을 가져옴
        myPackageManager = getPackageManager();

        //아이템의 크기가 변경되지않는다는것을 명시
        mRecyclerView.setHasFixedSize(true);

        canclebutton = (Button) findViewById(R.id.solt_dialog_cancle);

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = myPackageManager.queryIntentActivities(intent, 0);

        configureRecyclerView();
        recyclerView_adapter myAdapter = new recyclerView_adapter(intentList, this,layout_num);

        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.scrollToPosition(2); // 요게 머지? 찾아보기


        solt_dialog_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                padding_control(layout_num);
                mRecyclerView.setLayoutManager(layoutManager);
                recyclerView_adapter myAdapter = new recyclerView_adapter(intentList, getApplicationContext(),layout_num);
                mRecyclerView.setAdapter(myAdapter);

                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

            }
        });



        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_num_check.clearCheck();

                line_solt.setVisibility(View.VISIBLE);
                line_solt.startAnimation( animShow );

            }
        });

        line_num_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.one_line_btn:
                        layoutManager = galleryLayoutManager;
                        layout_num = 0;
                        toast = "1line으로 변경되었습니다";
                        break;
                    case R.id.two_line_btn:
                        layoutManager = mGridlayout_two;
                        layout_num = 1;
                        toast = "2line으로 변경되었습니다";
                        break;
                    case R.id.three_line_btn:
                        layoutManager = mGridlayout_three;
                        layout_num = 2;
                        toast = "3line으로 변경되었습니다";
                        break;

                }

            }
        });

//        //정렬버튼 열기
//        solt_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });



//        //정렬버튼 닫기
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_solt.startAnimation( animHide );
                line_solt.setVisibility(View.GONE);

            }
        });

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.text_text:
                        //라디오 초기화
                        line_num_check.clearCheck();

                        line_solt.setVisibility(View.VISIBLE);
                        line_solt.startAnimation( animShow );
                        break;



                }
            }
        };


        test_one.setOnClickListener(onClickListener);

        //설치된 어플 눌렀을때 동작
        myAdapter.setOnItemClickListener(new recyclerView_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                ResolveInfo cleckedResolveInfo =
                        intentList.get(pos);
                ActivityInfo clickedActivityInfo =
                        cleckedResolveInfo.activityInfo;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClassName(
                        clickedActivityInfo.applicationInfo.packageName,
                        clickedActivityInfo.name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);

            }
        });

    }


    private void padding_control(int layout_num){

        if(layout_num==0){
        mRecyclerView.setPadding(0,0,0,0);

        }
        else if(layout_num==1){
            mRecyclerView.setPadding(80,80,80,80);

        }
        else{
            mRecyclerView.setPadding(30,30,30,30);

        }

    }



    private void configureRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerView);
//        circleLayoutManager = new CircleLayoutManager(this, true);
//        circleZoomLayoutManager = new CircleZoomLayoutManager(this, true);
//        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(10));
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        galleryLayoutManager = new GalleryLayoutManager(this, Dp2px(10));
        mGridlayout_two = new GridLayoutManager(this, 2,LinearLayoutManager.HORIZONTAL,false);
        mGridlayout_three = new GridLayoutManager(this, 3,LinearLayoutManager.HORIZONTAL,false);


        mRecyclerView.addOnScrollListener(new CenterScrollListener()); //요것도 머지
        mRecyclerView.setLayoutManager(mGridlayout_two);

    }


    private int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //dialog 애니메이션 효과
    private void initAnimation()
    {
        animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);
    }

    //상단바 투명 효과
    private void top_transparent()
    {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }


}
