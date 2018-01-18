package com.brandon.codewater.mybizness;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by brand on 2017-09-15.
 */

public class UtilityFunctions {

    public void tutorialDialogSettings(Dialog tutDialog, @Nullable int yValue){
        Window wind = tutDialog.getWindow();
        WindowManager.LayoutParams params = wind.getAttributes();
        params.y = yValue;
        wind.setAttributes(params);
        wind.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void tutorialDialogSettings(Dialog tutDialog, String position, int yValue, int xValue){

        Window wind = tutDialog.getWindow();
        WindowManager.LayoutParams params = wind.getAttributes();

        params.y = yValue;
        params.x = xValue;
        wind.setAttributes(params);

        wind.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


        if(position.equals("TOP")){
            wind.setGravity(Gravity.TOP);
        }
        else if(position.equals("BOTTOM")){
            wind.setGravity(Gravity.BOTTOM);
        }
        else if(position.equals("CENTER")){
            wind.setGravity(Gravity.CENTER);
        }

        tutDialog.show();
    }

    public void setDialogLocation(Dialog dialog, int x, int y){
        WindowManager.LayoutParams wp = dialog.getWindow().getAttributes();

        //TOP = 48; RIGHT = 5; LEFT = 3; BOTTOM = 80; CENTER = 17;
        //wp.gravity = gravityY | gravityX;
        wp.gravity = Gravity.TOP | Gravity.RIGHT;
        wp.x = x;
        wp.y = y;

        dialog.show();
    }
}
