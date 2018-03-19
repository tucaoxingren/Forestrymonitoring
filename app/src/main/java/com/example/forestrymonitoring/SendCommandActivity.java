package com.example.forestrymonitoring;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestrymonitoring.common.ChatConstant;
import com.example.forestrymonitoring.mode.AboutInfo;
import com.example.forestrymonitoring.util.FTPUploadThread;
import com.example.forestrymonitoring.util.FileUtils;

public class SendCommandActivity extends BaseActivity {

    private Button sendUp = null;                   //向上按钮
    private Button sendDown = null;              //向下按钮
    private Button sendLeft = null;                 //向左按钮
    private Button sendRight = null;              //向右按钮
    private Button sendCommand = null;      //发送指令按钮
    private Spinner selectPointSpinner = null;//选择监测点标号下拉框
    private TextView textView = null;
    private TextView selectPointText = null;
    private Context mContext = null;
    private int flag = 0;//1:up 2:down 3:left 4:right
    private String controlCommand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_command);
        initControl();//初始化控件
        //发送指令按钮点击事件
        sendCommand.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if(flag == 0)
                    Toast.makeText(mContext,"尚未选择摄像头转动方向",Toast.LENGTH_SHORT).show();
                else {
                    switch (flag){
                        case 1: Toast.makeText(mContext,"已选择向上转动，即将发送指令",Toast.LENGTH_SHORT).show();break;
                        case 2: Toast.makeText(mContext,"已选择向下转动，即将发送指令",Toast.LENGTH_SHORT).show();break;
                        case 3: Toast.makeText(mContext,"已选择向左转动，即将发送指令",Toast.LENGTH_SHORT).show();break;
                        case 4: Toast.makeText(mContext,"已选择向右转动，即将发送指令",Toast.LENGTH_SHORT).show();break;
                        default: System.out.println("未知错误"); break;
                    }
                    // 写入控制指令至machineSerial.dat
//                    InputStream inputStream   =   new ByteArrayInputStream(controlCommand.getBytes());
//                    FileUtils.write2SDFromInput(ChatConstant.commandFilePath,inputStream);
                    FileUtils.wirteFile(ChatConstant.commandFilePath,controlCommand);
                    // 开启新线程 访问ftp服务器 上传控制指令machineSerial.txt文件至FTP服务器
                    FTPUploadThread ftpUploadThread = new FTPUploadThread();
                    ftpUploadThread.setParam(mContext);
                    new Thread(ftpUploadThread).start();
                    textView.setVisibility(View.VISIBLE);//设置文本框可见
                    textView.setText("指令已发送，请在30s后返回\n查看监测点界面点击刷新按钮");
                }
            }
        });
        //向上按钮点击事件
        sendUp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                flag = ChatConstant.up;
                controlCommand = "U";
            }
        });
        //向下按钮点击事件
        sendDown.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                flag = ChatConstant.down;
                controlCommand = "D";
            }
        });
        //向左按钮点击事件
        sendLeft.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                flag = ChatConstant.left;
                controlCommand = "L";
            }
        });
        //向右按钮点击事件
        sendRight.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                flag = ChatConstant.right;
                controlCommand = "R";
            }
        });
        //下拉框选择监听
        selectPointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initControl(){
        sendUp = (Button)findViewById(R.id.sendUp);
        sendDown = (Button)findViewById(R.id.sendDown);
        sendLeft = (Button)findViewById(R.id.sendLeft);
        sendRight = (Button)findViewById(R.id.sendRight);
        sendCommand = (Button)findViewById(R.id.sendCommandButton);
        selectPointSpinner = (Spinner) findViewById(R.id.selectPointSpinner);
        textView = (TextView) findViewById(R.id.textView);
        selectPointText = (TextView) findViewById(R.id.selectPointText);
        textView.setVisibility(View.GONE);//初始化隐藏textView
        selectPointSpinner.setVisibility(View.GONE);
        selectPointText.setVisibility(View.GONE);
        flag = 0;
        mContext = this;
        //ArrayAdapterpter arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select, mList);
        //传入的参数分别为 Context , 未选中项的textview , 数据源List
        //单独设置下拉的textview
        //arrayAdapter.setDropDownViewResource(R.layout.item_drop);
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
        } else if (id == R.id.menu_exit) {// 退出
//            finish();
            AtyContainer.getInstance().finishAllActivity();
            return true;
        } else if (id == R.id.menu_main) {// 监测界面
            Intent intent = new Intent();
            intent.setClass(SendCommandActivity.this, MapActivity.class);
            SendCommandActivity.this.startActivity(intent);
            return true;
        } else if (id == R.id.menu_home) {// 首页
            Intent intent = new Intent();
            intent.setClass(SendCommandActivity.this, MainActivity.class);
            SendCommandActivity.this.startActivity(intent);
            return true;
        } else if (id == R.id.netSetting) {// 发送控制指令
            // 跳转到发送控制指令界面
            //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            Intent intent = new Intent();
            intent.setClass(SendCommandActivity.this, SendCommandActivity.class);
            SendCommandActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
