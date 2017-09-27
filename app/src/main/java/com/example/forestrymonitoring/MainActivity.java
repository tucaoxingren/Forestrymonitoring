package com.example.forestrymonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//import com.baidu.mapapi.map.MapView;

public class MainActivity extends AppCompatActivity {

    private Button viewButton = null;
    private Button exitButton = null;
   // private MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
//        mMapView = (MapView) findViewById(R.id.bmapView);

        //获取按键
        viewButton = (Button)findViewById(R.id.button);
        exitButton = (Button)findViewById(R.id.button2);

        //查看监测点按钮点击事件
        viewButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                //setContentView(R.layout.activity_main_map);
                //生成一个Intent对象
                Intent intent = new Intent();
                //intent.putExtra("Element","元素值");//传递参数
                intent.setClass(MainActivity.this,MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        //退出按钮点击事件
        exitButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                MainActivity.super.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //菜单组ID，菜单ID，排序，菜单名字
        menu.add(0,1,1,R.string.exit);
        menu.add(0,2,2,R.string.about);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //菜单按钮回调函数
        if (item.getItemId() == 1)
            finish();
        else if (item.getItemId() == 2){
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,AboutActivity.class);
            MainActivity.this.startActivity(intent);//error
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