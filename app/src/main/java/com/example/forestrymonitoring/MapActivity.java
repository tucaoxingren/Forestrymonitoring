package com.example.forestrymonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.Offline.OfflineDemo;
import com.example.forestrymonitoring.monitoringPointDisplay.ReceiveInfo;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private Button refresh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        init();//初始化
        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新并展示坐标
                ReceiveInfo receiveInfo = new ReceiveInfo();
                receiveInfo.PackageMonitoringPoint(mBaiduMap);
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //菜单组ID，菜单ID，排序，菜单名字
        menu.add(0,1,1,R.string.offline);
        menu.add(0,2,2,R.string.about);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //菜单按钮回调函数
        if (item.getItemId() == 1){
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MapActivity.this,OfflineDemo.class);
            MapActivity.this.startActivity(intent);
        }
        else if (item.getItemId() == 2){
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MapActivity.this,AboutActivity.class);
            MapActivity.this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 初始化
     */
    private void init(){
        // 初始化控件
        this.initControls();
        // 初始化地图
        this.initMap();
    }
    /**
     * 初始化控件
     */
    private void initControls(){
        // 获取地图视图
        mMapView = (MapView) findViewById(R.id.bmapView);
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
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        // 设置为一般地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //  卫星地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        //设定中心点坐标 西南林业大学坐标
        LatLng cenpt =  new LatLng(25.06056,102.75911);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(12)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        // 设置初始大图大小500米MapStatusUpdateFactory
        MapStatusUpdate msu2 = MapStatusUpdateFactory.zoomTo(15.0f);
        //msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapStatus(msu2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
