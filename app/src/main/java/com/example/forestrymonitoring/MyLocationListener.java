package com.example.forestrymonitoring;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by 吐槽星人 on 2017/9/26 0026.
 *
 */
public class MyLocationListener extends BDAbstractLocationListener{

    private BaiduMap mBaiduMap = null;

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
            //Log.d("zwf", sb.toString());

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
        /*
        //获取定位结果
        location.getTime();    //获取定位时间
        location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
        location.getLocType();    //获取定位类型
        location.getLatitude();    //获取纬度信息
        location.getLongitude();    //获取经度信息
        location.getRadius();    //获取定位精准度
        location.getAddrStr();    //获取地址信息
        location.getCountry();    //获取国家信息
        location.getCountryCode();    //获取国家码
        location.getCity();    //获取城市信息
        location.getCityCode();    //获取城市码
        location.getDistrict();    //获取区县信息
        location.getStreet();    //获取街道信息
        location.getStreetNumber();    //获取街道码
        location.getLocationDescribe();    //获取当前位置描述信息
        location.getPoiList();    //获取当前位置周边POI信息

        location.getBuildingID();    //室内精准定位下，获取楼宇ID
        location.getBuildingName();    //室内精准定位下，获取楼宇名称
        location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息

        if (location.getLocType() == BDLocation.TypeGpsLocation){

            //当前为GPS定位结果，可获取以下信息
            location.getSpeed();    //获取当前速度，单位：公里每小时
            location.getSatelliteNumber();    //获取当前卫星数
            location.getAltitude();    //获取海拔高度信息，单位米
            location.getDirection();    //获取方向信息，单位度
//            location.

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

            //当前为网络定位结果，可获取以下信息
            location.getOperators();    //获取运营商信息

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

            System.out.println("当前为网络定位结果");//当前为网络定位结果

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            System.out.println("当前网络定位失败");//当前网络定位失败
            //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            System.out.println("当前网络不通");//当前网络不通

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            System.out.println("当前缺少定位依据"); //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
            //可进一步参考onLocDiagnosticMessage中的错误返回码

        }*/
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

    public void setmBaiduMap (BaiduMap baiduMap){
        mBaiduMap = baiduMap;
    }

}