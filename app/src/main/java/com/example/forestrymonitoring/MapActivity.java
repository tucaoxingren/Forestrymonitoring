package com.example.forestrymonitoring;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.mode.AboutInfo;
import com.example.forestrymonitoring.monitoringPointDisplay.ReceiveInfo;
import com.example.forestrymonitoring.util.FTPThread;
import com.example.forestrymonitoring.util.FTPUtils;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private Button refresh = null;
    private TextView textView = null;
    private LatLng latLng = null;
    private ReceiveInfo receiveInfo = null;
    private int[] iconId = new int[7];//图标Id
    private Context mContext;
    private SeekBar alphaSeekBar = null;
    private List<MarkerOptions> markerOptionsList;
    //FTP工具类
    private FTPUtils ftpUtils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        /*
        //强制在主线程中进行网络请求，最好不这样做，后续再改
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        //new Thread(runnable).start();

        init();//初始化控件
		//初始化和FTP服务器交互的类  
        //InitFTPServerSetting(); 
//		FTPThread ftpThread = new FTPThread();
//	    new Thread(ftpThread).start();

        //刷新按钮监听事件
        refresh.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取SeekBar值 即Marker标记
                final float alpha = ((float) alphaSeekBar.getProgress()) / 10;
                // 开启新线程 访问ftp服务器 并下载数据到本地
                FTPThread ftpThread = new FTPThread();
                ftpThread.setParam(mBaiduMap,markerOptionsList,iconId,mContext,alpha);
                new Thread(ftpThread).start();
/*
                // 刷新并展示坐标
                clearOverlay(mMapView);
                receiveInfo = new ReceiveInfo();
                markerOptionsList = receiveInfo.pullInfo(mBaiduMap,iconId,alpha);
                if(!receiveInfo.isOperatingFlag())
                {
                    Toast.makeText(MapActivity.this,"重试",Toast.LENGTH_SHORT).show();
                    System.out.print("获取坐标失败");
                }
*/
                /*
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        receiveInfo = new ReceiveInfo();
                        markerOptionsList = receiveInfo.pullInfo(mBaiduMap,iconId,alpha);
                        if(!receiveInfo.isOperatingFlag())
                        {
                            Toast.makeText(MapActivity.this,"重试",Toast.LENGTH_SHORT).show();
                            System.out.print("ceshi ");
                        }
                    }
                }).start();*/
            }
        });
        // Marker(地图标记)点击监听事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 设置监测点信息文本控件为 可见
                textView.setVisibility(View.VISIBLE);
                latLng = marker.getPosition();
                String str = receiveInfo.returnMounInfo(latLng);
                textView.setText("  监测点信息:"+str);
                return false;
            }
        });
        // 点击地图任何一点 不包括 标记点的点击监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //设置监测点信息文本控件为 不可见
                textView.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        // SeekBar 改变监听事件
        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alpha = ((float) seekBar.getProgress()) / 10;
                for(MarkerOptions markerOptions : markerOptionsList){
                    markerOptions.alpha(alpha);
                }
                ReceiveInfo.PackageMonitoringPoint(mBaiduMap,markerOptionsList);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
	
	public boolean InitFTPServerSetting() {
        // TODO Auto-generated method stub  
        ftpUtils = FTPUtils.getInstance();
        //cs3.swfu.edu.cn  20141151062  19951024
        boolean flag = ftpUtils.initFTPSetting("202.203.132.245", 21, "20141151062", "19951024");
        return flag;
    }

    /**
     * 清除所有Overlay
     * @param view  百度地图View
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }

    /**
     * 初始化
     */
    private void init(){
        // 初始化控件
        this.initControls();
        // 初始化地图
        this.initMap();
        //初始化图标id
        this.initIconId();
        mContext = this;
        alphaSeekBar = (SeekBar) findViewById(R.id.alphaBar);
        markerOptionsList = new ArrayList<>();
    }

    /**
     *  初始化图标id
     */
    private void initIconId(){
        iconId[0] = R.drawable.icon_marka;
        iconId[1] = R.drawable.icon_markb;
        iconId[2] = R.drawable.icon_markc;
        iconId[3] = R.drawable.icon_markd;
        iconId[4] = R.drawable.icon_marke;
        iconId[5] = R.drawable.icon_markf;
        iconId[6] = R.drawable.icon_gcoding;
    }
    /**
     * 初始化控件
     */
    private void initControls(){
        // 获取地图视图
        mMapView = (MapView) findViewById(R.id.bmapView);
        // 获取刷新按钮
        refresh = (Button) findViewById(R.id.refresh);
        // 获取监测点信息文本
        textView = (TextView) findViewById(R.id.markInfo);
        textView.setVisibility(View.GONE);
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
//            finish();
            AtyContainer.getInstance().finishAllActivity();
            return true;
        }
        else if(id == R.id.menu_main){// 监测界面
            Intent intent = new Intent();
            intent.setClass(MapActivity.this,MapActivity.class);
            MapActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_home){// 首页
            Intent intent = new Intent();
            intent.setClass(MapActivity.this,MainActivity.class);
            MapActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
