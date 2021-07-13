package com.peng.plant.wattstore.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.peng.plant.wattstore.R;
import com.peng.plant.wattstore.activity.MainActivity;

public class CommandView extends View {
    public CommandView(Activity context , String cmd , int resId) {
        super(context);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(1,1);
        this.setContentDescription(String.format("hf_no_number|%s",cmd));
        this.setId(resId);
        context.addContentView(this , lp);

    }




}
