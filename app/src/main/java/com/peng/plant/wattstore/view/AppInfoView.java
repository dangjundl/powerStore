package com.peng.plant.wattstore.view;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.data.AppData;

public class AppInfoView extends RelativeLayout implements View.OnClickListener{

    private static final String TAG = "AppInfoView";

    //    RelativeLayout bg;
    TextView appName;
    ImageView appUpdateIcon;
    TextView appVersion;
    //    TextView appNewVersion;
    ImageView appIcon;
    Activity mAct;
    View.OnClickListener onClickListener;

    public AppInfoView(Context context , Activity act , View.OnClickListener onClickListener) {
        super(context);
        initView();
        this.mAct = act;
        this.onClickListener = onClickListener;
    }

    public AppInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AppInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.activity_app_item, this, false);
        addView(v);

//        bg = (RelativeLayout) findViewById(R.id.app_bg);
        appName = (TextView) findViewById(R.id.app_name);
        appName.setOnClickListener(this);

        appUpdateIcon = (ImageView) findViewById(R.id.app_status_icon);
        appVersion = (TextView) findViewById(R.id.app_version);
//        appNewVersion = (TextView) findViewById(R.id.app_new_version);
        appIcon = (ImageView) findViewById(R.id.icon_imageView);
    }

//    private void addCommandView(String cmd)
//    {
//        int res_id = appName.getId() & R.id.id_app_delete;
//        new CommandView(mAct , cmd , res_id).setOnClickListener(this);
//    }

    public ImageView getResourceAppIcon()
    {
        return this.appIcon;
    }

    public void setData(AppData data)
    {
        this.appName.setText(data.getAppName());
//        addCommandView(String.format("%s %s" , data.getAppName() , getResources().getString(R.string.delete)));
        setAppVersion(data.appCurrentVersion ,data.appUpdateVersion);
        drawUpdateStatus(data.isInstalled , data.isNewVersion);
    }

    public void setAppVersion(String currVersion , String newVersion)
    {
        appVersion.setText(String.format("v%s" , currVersion));
//        appNewVersion.setText(newVersion);
    }

    public void showAppVersion(boolean isVisible)
    {
        appVersion.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

//    public void resizeAppBox()
//    {
//        bg.getLayoutParams().height = bg.getHeight() - 100;
//    }

    public void drawUpdateStatus(boolean isInstalled , int isNewValue)
    {
//        Log.d(TAG , "Noah drawUpdateStatus: " + isInstalled + " / " + isNewValue);
        if (!isInstalled)
        {
            appUpdateIcon.setVisibility(View.GONE);
        }
        else
        {
            if(isNewValue > 0)
            {
                appUpdateIcon.setVisibility(View.VISIBLE);
            }
            else
            {
//                appUpdateIcon.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        this.onClickListener.onClick(v);
    }
}
