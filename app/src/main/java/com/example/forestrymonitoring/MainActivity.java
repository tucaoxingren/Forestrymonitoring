package com.example.forestrymonitoring;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
        // 发送控制指令按钮点击事件
        netSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到发送控制指令界面
                //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                //生成一个Intent对象
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SendCommandActivity.class);
                MainActivity.this.startActivity(intent);
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
		// 申请文件读写权限
        applyPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
		//创建应用目录
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

    // 申请所需权限
    protected void applyPermission(String permission, int resultCode){
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            Log.i("info", "1,需要申请权限。");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //TODO 用户未拒绝过 该权限 shouldShowRequestPermissionRationale返回false  用户拒绝过一次则一直返回true
                //TODO   注意小米手机  则一直返回是 false
                Log.i("info", "3,用户已经拒绝过一次该权限，需要提示用户为什么需要该权限。\n" +
                        "此时shouldShowRequestPermissionRationale返回：" + ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission));
                //TODO  解释为什么  需要该权限的  对话框
                showMissingPermissionDialog();
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(this, new String[]{permission}, resultCode);
                Log.i("info", "2,用户拒绝过该权限，或者用户从未操作过该权限，开始申请权限。-\n" +
                        "此时shouldShowRequestPermissionRationale返回：" +
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission));
            }
        } else {
            Log.i("info", "7,授权成功");
        }
    }
    /**
     * 提示用户的 dialog
     */
    protected void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少读写手机存储权限\n\n请点击\"设置\"-\"权限\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "8--权限被拒绝,此时不会再回调onRequestPermissionsResult方法");
                    }
                });
        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "4,需要用户手动设置，开启当前app设置界面");
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 打开     App设置界面
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
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
        else if(id == R.id.netSetting){// 发送控制指令
            // 跳转到发送控制指令界面
            //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,SendCommandActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults){

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
