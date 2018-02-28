package com.example.forestrymonitoring.mode;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

//import com.example.forestrymonitoring.AtyContainer;
import com.example.forestrymonitoring.MainActivity;
import com.example.forestrymonitoring.MapActivity;
import com.example.forestrymonitoring.R;

/**
 * Created by 吐槽星人 on 2017/12/29 0029.
 */

public class menuInfo {
    public boolean menuClick(Context thisActivity, Context mContext, MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.menu_about) {// 关于
            AboutInfo.displayAboutDialog(mContext);// 关于
            return true;
        }
        else if(id == R.id.menu_exit){// 退出
            //AtyContainer.getInstance().finishAllActivity();
            return true;
        }
        else if(id == R.id.menu_main){// 监测界面
            Intent intent = new Intent();
            intent.setClass(thisActivity,MapActivity.class);
            thisActivity.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_home){// 首页
            Intent intent = new Intent();
            intent.setClass(thisActivity,MainActivity.class);
            thisActivity.startActivity(intent);
            return true;
        }
        return true;
    }
}
