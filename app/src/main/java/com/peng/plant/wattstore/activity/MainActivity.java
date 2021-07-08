package com.peng.plant.wattstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.plant.wattstore.listener.CenterScrollListener;
import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.adapter.recyclerView_adapter;
import com.peng.plant.wattstore.manager.GalleryLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd"); // 날짜 포맷 형식
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridlayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView calender;
    private PackageManager myPackageManager;
    private Button solt_btn,canclebutton,solt_dialog_apply_btn;
    private Animation animShow, animHide;
    private View line_solt;
    private GalleryLayoutManager galleryLayoutManager;
    private RadioGroup line_num_check;
    private RecyclerView.LayoutManager layoutManager;
    private String toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        line_solt = findViewById(R.id.line_solt_dialog);
        mRecyclerView = findViewById(R.id.home_recyclerView);
        line_num_check = (RadioGroup) findViewById(R.id.line_radioGroup);
        solt_dialog_apply_btn = (Button) findViewById(R.id.solt_dialog_apply);
        initAnimation();
        //갤러리 레이아웃매니저 객체 생성
        top_transparent();
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        calender = (TextView) findViewById(R.id.calender_text);
        Date date = new Date();
        String time = mFormat.format(date);
        calender.setText(time);

        //패키지 권한을 가져옴
        myPackageManager = getPackageManager();

        //아이템의 크기가 변경되지않는다는것을 명시
        mRecyclerView.setHasFixedSize(true);

        canclebutton = (Button) findViewById(R.id.solt_dialog_cancle);
        solt_btn = (Button) findViewById(R.id.solt_btn);

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = myPackageManager.queryIntentActivities(intent, 0);

        configureRecyclerView();
        recyclerView_adapter myAdapter = new recyclerView_adapter(intentList, this);

        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.scrollToPosition(5); // 요게 머지? 찾아보기


        solt_dialog_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.setLayoutManager(layoutManager);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

            }
        });

        line_num_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.one_line_btn:
                        layoutManager = mGridlayout;
                        toast = "1line으로 변경되었습니다";
                        break;
                    case R.id.two_line_btn:
                        layoutManager = galleryLayoutManager;
                        toast = "2line으로 변경되었습니다";
                        break;
                    case R.id.three_line_btn:
                        layoutManager = mLayoutManager;
                        toast = "3line으로 변경되었습니다";
                        break;

                }

            }
        });

        //정렬버튼 열기
        solt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                line_solt.setVisibility(View.VISIBLE);
                line_solt.startAnimation( animShow );
                solt_btn.setVisibility(View.INVISIBLE);

            }
        });


        //정렬버튼 닫기
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_solt.startAnimation( animHide );
                line_solt.setVisibility(View.GONE);
                solt_btn.setVisibility(View.VISIBLE);

            }
        });


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

    private void configureRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerView);
//        circleLayoutManager = new CircleLayoutManager(this, true);
//        circleZoomLayoutManager = new CircleZoomLayoutManager(this, true);
//        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(10));
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mGridlayout = new GridLayoutManager(this, 2,LinearLayoutManager.HORIZONTAL,false);
        galleryLayoutManager = new GalleryLayoutManager(this, Dp2px(10));

        mRecyclerView.addOnScrollListener(new CenterScrollListener()); //요것도 머지
        mRecyclerView.setLayoutManager(galleryLayoutManager);

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
