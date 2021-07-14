package com.peng.plant.wattstore.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.activity.MainActivity;
import com.peng.plant.wattstore.data.AppData;
import com.peng.plant.wattstore.data.DEFINE;
import com.peng.plant.wattstore.view.AppInfoView;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class recyclerView_adapter extends RecyclerView.Adapter<recyclerView_adapter.MyViewHolder>{

    private static final String TAG = "daengjun";
    private Context context;
    private PackageManager myPackageManager;
    private OnItemClickListener mListener = null ;
    private ConcurrentHashMap<Integer, AppData> appInfos;
    private View view;
    private int num;
    String paths;
    String imageStr;

    // private List<ResolveInfo> MyAppList; 전체 앱목록

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v,int pos);

    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvPrice;

        MyViewHolder(View itemView){
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            ivPicture = itemView.findViewById(R.id.icon_imageView);
            tvPrice = itemView.findViewById(R.id.app_name);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음
    public recyclerView_adapter(ConcurrentHashMap<Integer, AppData> appInfos, Context context, int layout_num){
        this.appInfos = appInfos;
        this.context = context;
        this.num=layout_num;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewTypem) {

        //리사이클러뷰 아이템
        itemSelection(parent,viewTypem);

        return new MyViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        AppData appDatas = appInfos.get(position);

        myPackageManager = context.getPackageManager();

        //어플 이미지 주소
//        path = "https://watt.powertalk.kr:8357/powerstore/icons/icon_powertalk.png";
//        Glide.with(context.getApplicationContext())
//                .load("https://watt.powertalk.kr:8357/powerstore/icons/icon_powertalk.png")
//                .override(100, 100)
//                .into(holder.ivPicture);

//        path = String.format("%sicon_%s_off.png" , "https://watt.powertalk.kr" + ":8357/" + "powerstore" + "/"+"icons"+"/" , appDatas.appNameEng.toLowerCase());
         imageStr = "https://watt.powertalk.kr:8357/powerstore/icons/icon_powermemo.png";

//        Glide.with(context).load(imageStr).error(R.drawable.icon_powertalk).into(holder.ivPicture);
        paths = String.format("%sicon_%s.png" , DEFINE.SERVER_LOGO_DOWNLOAD_URL , appDatas.appNameEng.toLowerCase());

       String test = imageStr;

        Glide.with(context)
                .load(paths)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivPicture);


        Log.d(TAG, "onBindViewHolder: path"+test);

//                Glide.with(context).load("http://watt.powertalk.kr:8357/powerstore/icons/icon_powermemo.png").into(holder.ivPicture);





        myViewHolder.tvPrice.setText(appDatas.getAppName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }


    public View itemSelection(ViewGroup parent,int viewTypem){
        Log.d(TAG, "itemSelection: num값?"+num);

        if(num==0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item, parent, false);
        }
        else if(num==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item2, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item3, parent, false);

        }

        return view;
    }





}