package com.example.forestrymonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;


public class MapActivity extends AppCompatActivity {

    public MapView mMapView = null;
    public BaiduMap mBaiduMap = null;
    public LocationClient mLocClient = null;
    public BDLocation location = null;

    public MyLocationListener myLocationListenner = null;
    public BDAbstractLocationListener myListener = new MyLocationListener();

    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                setContentView(R.layout.loading);
                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
                );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
                option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
                int span = 5000; //5秒发送一次
                option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
                option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
                option.setOpenGps(true);//可选，默认false,设置是否使用gps
                option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
                option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
                option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
                option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
                option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
                option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
                option.setNeedDeviceDirect(true); //返回的定位结果包含手机机头方向
                mLocClient.setLocOption(option);
                setContentView(R.layout.activity_main_map);
                mLocClient.start(); //启动位置请求
                mLocClient.requestLocation();//发送请求

            }
        });
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        BDLocationListener listener = new com.example.forestrymonitoring.baidu.MyLocationListener();

        //此处需要注意：LocationClient类必须在主线程中声明。需要Context类型的参数。
        //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
        mLocClient = new LocationClient(getApplicationContext());

        //注册位置监听器
        mLocClient.registerLocationListener(listener);

/*
        mLocClient = new LocationClient(MApplication.getContext());
        //声明LocationClient类
        mLocClient.registerLocationListener(myListener);
        //注册监听函数

        init();//初始化地图

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();//开始监听

        //定位SDK监听函数
        myLocationListenner = new MyLocationListenner();
        myLocationListenner.setmBaiduMap(mBaiduMap);
        myLocationListenner.setmMapView(mMapView);
        myLocationListenner.setisFirstLoc(true);
//        location = new BDLocation();
        location = mLocClient.getLastKnownLocation();
//        myListener.onReceiveLocation(location);
        myLocationListenner.onReceiveLocation(location);

        //myListener.onLocDiagnosticMessage(location.getLocType(),location.get);*/
    }

    class MyLocationListener extends BDAbstractLocationListener{

//        private BaiduMap mBaiduMap = null;

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }

                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.d("BaiduLocationApiDem", sb.toString());

                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.refresh_icon);
                //创建一个图层选项
                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
                mBaiduMap.addOverlay(options);
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(latlng)
                        .zoom(12)
                        .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
            }

        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * 自动回调，相同的diagnosticType只会回调一次
         *
         * @param locType           当前定位类型
         * @param diagnosticType    诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */

        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {

            if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {

                System.out.println("建议打开GPS"); //建议打开GPS

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {

                System.out.println("建议打开wifi，不必连接，这样有助于提高网络定位精度！");
                //建议打开wifi，不必连接，这样有助于提高网络定位精度！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_LOC_PERMISSION) {

                System.out.println("定位权限受限，建议提示用户授予APP定位权限！");
                //定位权限受限，建议提示用户授予APP定位权限！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET) {

                System.out.println("网络异常造成定位失败，建议用户确认网络状态是否异常！");
                //网络异常造成定位失败，建议用户确认网络状态是否异常！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE) {

                System.out.println("手机飞行模式造成定位失败，建议用户关闭飞行模式后再重试定位！");
                //手机飞行模式造成定位失败，建议用户关闭飞行模式后再重试定位！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI) {

                System.out.println("无法获取任何定位依据，建议用户打开wifi或者插入sim卡重试！");
                //无法获取任何定位依据，建议用户打开wifi或者插入sim卡重试！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_OPEN_PHONE_LOC_SWITCH) {

                System.out.println("无法获取有效定位依据，建议用户打开手机设置里的定位开关后重试！");
                //无法获取有效定位依据，建议用户打开手机设置里的定位开关后重试！

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_SERVER_FAIL) {

                System.out.println("百度定位服务端定位失败");
                //百度定位服务端定位失败
                //建议反馈location.getLocationID()和大体定位时间到loc-bugs@baidu.com

            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_FAIL_UNKNOWN) {

                System.out.println("无法获取有效定位依据");
                //无法获取有效定位依据，但无法确定具体原因
                //建议检查是否有安全软件屏蔽相关定位权限
                //或调用LocationClient.restart()重新启动后重试！

            }
        }
/*
        public void setmBaiduMap (BaiduMap baiduMap){
            mBaiduMap = baiduMap;
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //菜单组ID，菜单ID，排序，菜单名字
        menu.add(0, 1, 1, R.string.exit);
        menu.add(0, 2, 2, R.string.about);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //菜单按钮回调函数
        if (item.getItemId() == 1)
            finish();
        else if (item.getItemId() == 2) {
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(MapActivity.this, AboutActivity.class);
            MapActivity.this.startActivity(intent);//error
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化控件
        this.initControls();
        // 初始化地图
        this.initMap();
    }

    /**
     * 初始化控件
     */
    private void initControls() {
        // 获取地图视图
        mMapView = (MapView) findViewById(R.id.bmapView);//error mapView=null
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        // 不显示地图上比例尺
//        mapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
//        mapView.showZoomControls(false);
        // 获取百度地图对象
        mBaiduMap = mMapView.getMap();//error baiduMap=bull mapView=null
        mBaiduMap.setMyLocationEnabled(true);
        // 设置为一般地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //  卫星地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        // 设置初始大图大小500米MapStatusUpdateFactory
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
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