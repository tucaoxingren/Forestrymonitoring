package com.example.forestrymonitoring;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.forestrymonitoring.Offline.OfflineDemo;
import com.example.forestrymonitoring.common.ChatConstant;
import com.example.forestrymonitoring.mode.AboutInfo;

import java.io.File;

public class MainActivity extends BaseActivity {

    private Button viewButton = null;
    private Button exitButton = null;
    private Button offlineMap = null;
    private Context mContext;
    private Button clearCache = null;
    private Button netSetting = null;

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
        // 离线地图 点击事件
        offlineMap.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //生成一个Intent对象
                Intent intent = new Intent();
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
        // 网络设置按钮点击事件
        netSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到网络设置界面
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        //清除缓存按钮点击事件
        clearCache.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if(clearCache())
                    Toast.makeText(mContext,"缓存清除成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext,"缓存清除失败",Toast.LENGTH_SHORT).show();
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
        clearCache = (Button)findViewById(R.id.button4);
        netSetting = (Button)findViewById(R.id.button5);
        offlineMap = (Button)findViewById(R.id.button6);
		//创建应用目录
		//String fileDirPath = Environment.getExternalStorageDirectory()+"/"+"2forestrymonitoring/";
		createFile(ChatConstant.appDirectory);
    }
	// 创建应用目录 
	private void createFile(String fileDirPath) {  
        //String filePath = fileDirPath + "/" + fileName;// 文件路径  
        try {
            File dir;// 目录路径
            dir = new File(fileDirPath);
            if (!dir.exists()) {// 如果不存在，则创建路径名  
                System.out.println("要存储的目录不存在");
                if (dir.mkdirs()) {// 创建该路径名，返回true则表示创建成功  
                    System.out.println("已经创建文件存储目录");
                } else {
                    System.out.println("创建目录失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除应用目录下的缓存文件
     */
    private boolean clearCache() {
        File appDirectory = new File(ChatConstant.appDirectory);
        File[] files = appDirectory.listFiles();
        if(files.length>0) {
            for (int x = 0; x < files.length; x++) {
                if (!files[x].isDirectory()) {
                    files[x].delete();
                }
            }
            return  true;
        }
        else
            return false;
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
        else if(id == R.id.netSetting){// 网络设置
            // 跳转到网络设置界面
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
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
