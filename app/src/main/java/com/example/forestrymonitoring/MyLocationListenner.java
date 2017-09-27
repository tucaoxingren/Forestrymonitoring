package com.example.forestrymonitoring;

/**
 *
 * Created by 吐槽星人 on 2017/9/26 0026.
 *
 */

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 *
 * 定位SDK监听函数
 *
 */
public class MyLocationListenner implements BDLocationListener {

    private static BaiduMap mBaiduMap = null;
    private static boolean isFirstLoc = false;
    private static MapView mMapView = null;
    @Override
    public void onReceiveLocation(BDLocation location) {
        // map view 销毁后不在处理新接收的位置
        if (location == null || mMapView == null)
            return;
        MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {//isFirstLoc
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
        }
    }

    public static void setmBaiduMap(BaiduMap baiduMap){
        mBaiduMap = baiduMap;
    }
    public static void setmMapView(MapView mapView){
        mMapView = mapView;
    }
    public static void setisFirstLoc(boolean flag){
        isFirstLoc = flag;
    }

    public void onReceivePoi(BDLocation poiLocation) {
    }
}