package com.example.forestrymonitoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

public class MainActivity extends AppCompatActivity {

    private Button viewButton = null;
    private Button exitButton = null;

    private MapView mapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        // 初始化
        this.init();

        //获取按键
        viewButton = (Button)findViewById(R.id.button);
        exitButton = (Button)findViewById(R.id.button2);

        //查看监测点按钮点击事件
        viewButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                setContentView(R.layout.activity_main_map);
            }
        });
        //退出按钮点击事件
        exitButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                MainActivity.super.finish();
            }
        });
    }

    /**
     * 初始化
     */
    private void init(){
        // 初始化控件
        this.initControls();
        // 初始化地图
       // this.initMap();
    }

    /**
     * 初始化控件
     */
    private void initControls(){
        // 获取地图视图
        this.mapView = (MapView) findViewById(R.id.bmapView);
    }

    /**
     * 初始化地图
     */
    private void initMap(){
        // 不显示地图上比例尺
//        mapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
//        mapView.showZoomControls(false);
        // 获取百度地图对象
        BaiduMap baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        // 设置为一般地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //  卫星地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        // 设置初始大图大小500米MapStatusUpdateFactory
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
    }

    /**
     * 当Activity处于可见状态时运行
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Activity销毁时运行
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 管理地图的生命周期：销毁
        if(mapView != null){
            mapView.onDestroy();
        }
    }

    /**
     * Activity可以得到用户焦点时运行
     */
    @Override
    protected void onResume() {
        super.onResume();
        // 显示
        if(mapView != null){
            mapView.onResume();
        }
    }

    /**
     * Activity被遮挡住时运行
     */
    @Override
    protected void onPause() {
        super.onPause();
        // 暂停
        if(mapView != null){
            mapView.onPause();
        }
    }

    /**
     * Activity处于不可见状态时运行
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

}
