package com.peng.plant.wattstore.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.peng.plant.wattstore.R;
import java.util.List;

public class recyclerView_adapter extends RecyclerView.Adapter<recyclerView_adapter.MyViewHolder>{

    private static final String TAG = "daengjun";
    private List<ResolveInfo> MyAppList;
    private PackageManager myPackageManager;
    private Context context;
    private OnItemClickListener mListener = null ;
    private View view;
    private int num;

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
    public recyclerView_adapter(List<ResolveInfo> MyAppList, Context context,int layout_num){
        this.MyAppList = MyAppList;
        this.context = context;
        this.num=layout_num;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewTypem) {

        itemSelection(parent,viewTypem);


        //리사이클러뷰 아이템

        return new MyViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ResolveInfo resolveInfo = MyAppList.get(position);

        myPackageManager = context.getPackageManager();

        myViewHolder.ivPicture.setImageDrawable(resolveInfo.loadIcon(myPackageManager));
        myViewHolder.tvPrice.setText(resolveInfo.loadLabel(myPackageManager));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MyAppList.size();
    }


    public View itemSelection(ViewGroup parent,int viewTypem){
        Log.d(TAG, "itemSelection: num값?"+num);

        if(num==0) {
            Log.d(TAG, "itemSelection: 여기 타나요? 1번");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item, parent, false);
        }
        else if(num==1){
            Log.d(TAG, "itemSelection: 여기 타나요? 2번");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item2, parent, false);
        }
        else{
            Log.d(TAG, "itemSelection: 여기 타나요? 3번");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_app_item3, parent, false);

        }

        return view;
    }

}