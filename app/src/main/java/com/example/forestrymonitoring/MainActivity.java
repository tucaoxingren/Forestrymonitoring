package com.example.forestrymonitoring;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.forestrymonitoring.Offline.OfflineDemo;
import com.example.forestrymonitoring.mode.AboutInfo;

public class MainActivity extends BaseActivity {

    private Button viewButton = null;
    private Button exitButton = null;
    private Button blueTooth = null;
    private Context mContext;
    private Button blueInfo = null;
    private Button blueTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        init();//初始化

        //查看监测点按钮点击事件
        viewButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //setContentView(R.layout.activity_main_map);
                //生成一个Intent对象
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        // 蓝牙信息 按钮点击事件
        blueInfo.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //生成一个Intent对象
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,BluetoothActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        // 蓝牙测试 点击事件
        blueTest.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //生成一个Intent对象
                Intent intent = new Intent();
                //intent.setClass(MainActivity.this,BlueTestActivity.class);
                intent.setClass(MainActivity.this,OfflineDemo.class);
                MainActivity.this.startActivity(intent);
            }
        });
        //退出按钮点击事件
        exitButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //MainActivity.super.finish();
                AtyContainer.getInstance().finishAllActivity();
            }
        });
        // 蓝牙配对按钮点击事件
        blueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到蓝牙设置界面
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
        });
    }

    /**
     * 初始化
     */
    private void init(){
        mContext = this;
        //获取按键
        viewButton = (Button)findViewById(R.id.button);
        exitButton = (Button)findViewById(R.id.button2);
        blueTooth = (Button)findViewById(R.id.button4);
        blueInfo = (Button)findViewById(R.id.button5);
        blueTest = (Button)findViewById(R.id.button6);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用menu布局文件构建menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {// 关于
            AboutInfo.displayAboutDialog(mContext);// 关于
            return true;
        } else if(id == R.id.menu_bluetooth){//蓝牙信息
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,BluetoothActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_bluetoothTest){//蓝牙测试
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,BlueTestActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_exit){// 退出
            AtyContainer.getInstance().finishAllActivity();
            return true;
        }
        else if(id == R.id.menu_main){// 监测界面
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,MapActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_home){// 首页
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,MainActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        System.out.print("onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        System.out.print("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        System.out.print("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.print("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.print("onStop");
        super.onStop();
    }
}
