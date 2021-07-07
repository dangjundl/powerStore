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
import android.widget.TextView;

import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.adapter.recyclerView_adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd"); // 날짜 포맷
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TextView calender;
    PackageManager myPackageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //상태바 투명하게 만들기
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        calender = (TextView) findViewById(R.id.calender_text);
        Date date = new Date();
        String time = mFormat.format(date);
        calender.setText(time);
        //패키지 권한을 가져옴
        myPackageManager = getPackageManager();

        mRecyclerView = findViewById(R.id.home_recyclerView);

        //아이템의 크기가 변경되지않는다는것을 명시
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = myPackageManager.queryIntentActivities(intent, 0);

        recyclerView_adapter myAdapter = new recyclerView_adapter(intentList,this);
        mRecyclerView.setAdapter(myAdapter);

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

}
