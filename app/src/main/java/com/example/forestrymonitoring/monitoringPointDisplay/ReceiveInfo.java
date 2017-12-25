package com.example.forestrymonitoring.monitoringPointDisplay;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 接收数据并进行封装
 */

public class ReceiveInfo {

    private ArrayList<MonitoringPoint> monArray = new ArrayList<>();
    private static final String ICON_PATH = "./src/main/res/raw/";

    /**
     *  通过蓝牙接收监测点信息
     * @return 信息字符串
     */
    private String  BluetoothReceInfo(){
        //String str = "";
        return "";
    }

    /**
     *  主线程最先调用此方法
     * @param mBaiduMap 百度地图视图
     * @return List<Marker>标记点
     */
    public List<MarkerOptions> pullInfo(BaiduMap mBaiduMap,int[] iconId,float alpha) {//throws IOException
        //String deviceMac = "38:A4:ED:3C:FC:9C";//  MI5
        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        // 从监听到的蓝牙获取信息
        /*
        // 启动服务端
        BluetoothServer bluetoothServer = new BluetoothServer();
        bluetoothServer.openServe();
        // 启动监听
        BluetoothDevice bluetoothDevice;
        bluetoothDevice = BluetoothManager.getDevice(deviceMac);
        BluetoothClient bluetoothClient = new BluetoothClient(bluetoothDevice);
        bluetoothClient.connetServer();
        */


        //将收到的信息封装
        //PackageMonitoringPoint();
        transformString();
        //返回并展示
        markerOptionsList = PackageMonitoringPoint(mBaiduMap,monArray,iconId,alpha);
        return markerOptionsList;
    }

    /**
     * 封装监测点信息在一个list中
     */
    private void PackageMonitoringPoint(){
        MonitoringPoint monitoringPoint1 = new MonitoringPoint(25.04056f,102.73911f,26.5f,75f,ICON_PATH+"a1.png","1号监测点");
        MonitoringPoint monitoringPoint2 = new MonitoringPoint(25.08056f,102.77911f,25.4f,94f,ICON_PATH+"a2.png","2号监测点");
        MonitoringPoint monitoringPoint3 = new MonitoringPoint(25.09056f,102.79911f,27.1f,63f,ICON_PATH+"a3.png","3号监测点");

        monArray.add(monitoringPoint1);
        monArray.add(monitoringPoint2);
        monArray.add(monitoringPoint3);

    }

    private void transformString(){
        String str = "25.04056f,102.73911f,26.5f,75f,a1.png,1号监测点";
        PackageMonitoringPoint(str);
    }

    /**
     * 封装监测点信息在一个list中
     * 监测点
     */
    private void PackageMonitoringPoint(String info){
        String[] temp = info.split(",");
        MonitoringPoint monPoint = new MonitoringPoint();
        for(int i=0;i<temp.length/6;i++){
            monPoint.setLatitude(Float.parseFloat(temp[i*6+0]));
            monPoint.setLongitude(Float.parseFloat(temp[i*6+1]));
            monPoint.setTemperature(Float.parseFloat(temp[i*6+2]));
            monPoint.setHumidity(Float.parseFloat(temp[i*6+3]));
            monPoint.setImg(temp[i*6+4]);
            monPoint.setName(temp[i*6+5]);
            monArray.add(monPoint);
        }
    }

    /**
     * 在地图上添加标记
     * @param mBaiduMap 地图视图
     * @param arrayList 标记点封装类 组
     * @param iconId 图标资源id数组
     * @return List<Marker>
     */
    private List<MarkerOptions> PackageMonitoringPoint(BaiduMap mBaiduMap,ArrayList<MonitoringPoint> arrayList,int[] iconId,float alpha){

        LatLng point;
        BitmapDescriptor bitmap;
        MarkerOptions option;
        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        int i = 0;
        for (MonitoringPoint monArray : arrayList){
            //定义Maker坐标点
            point = new LatLng(monArray.getLatitude(),monArray.getLongitude());
            //构建Marker图标
            bitmap = BitmapDescriptorFactory.fromResource(iconId[i]);
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions().position(point).icon(bitmap);
            // 掉下动画
            option.animateType(MarkerOptions.MarkerAnimateType.drop);
            // Marker 透明度
            option.alpha(alpha);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            markerOptionsList.add(option);
            // 图标数组下标自增
            i++;
        }
        return markerOptionsList;
    }

    /**
     * 改变已经显示的Marker的透明度
     * @param mBaiduMap 地图视图
     * @param markerOptionsList 标记点封装类 组
     */
    public static void PackageMonitoringPoint(BaiduMap mBaiduMap,List<MarkerOptions> markerOptionsList){
        mBaiduMap.clear();
        for (MarkerOptions markerOptions : markerOptionsList){
            markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }
    }

    /**
     *  根据坐标查找对应坐标的监测点信息
     * @param latLng 要查找的经纬度
     * @return String 监测点信息
     */
    public String returnMounInfo(LatLng latLng ){
        String info = BluetoothReceInfo();
        PackageMonitoringPoint(info);
        // 根据坐标查找监测点
        MonitoringPoint mo;
        mo = findMonPoInfo(latLng);
        if (mo == null){
            mo = new MonitoringPoint(0f,0f,0f,0f,"","无此坐标");
        }
        float latitude = mo.getLatitude();      // 纬度
        float longitude = mo.getLongitude();// 经度
        float temperature = mo.getTemperature();
        float humidity = mo.getHumidity();
        //String img = mo.getImg();
        String name = mo.getName();
        /*
        Drawable drawable = new DrawableContainer();
        drawable.createFromPath(img);*/
        //Drawable drawable= getResources().getDrawable(img);

        /// 这一步必须要做,否则不会显示.
        //drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //左上右下
        //textView.setCompoundDrawables(null,null, drawable,null);
        //                           纬度                        经度
        return name+"\n  纬度 : "+latitude+"\n  经度 : "+longitude+"\n  温度 : "+temperature+"        湿度 : "+humidity;
    }

    /**
     * 遍历monArray找出对应坐标监测点信息
     * @param latLng 坐标值
     * @return MonitoringPoint  对应坐标的信息
     */
    private MonitoringPoint findMonPoInfo(LatLng latLng){
        // 遍历monArray找出对应坐标监测点信息
        for (MonitoringPoint mo: monArray) {
            if(mo.getLatitude() == latLng.latitude && mo.getLongitude() == latLng.longitude)
                return mo;
        }
        return null;
    }

}
