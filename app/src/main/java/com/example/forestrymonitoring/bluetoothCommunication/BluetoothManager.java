package com.example.forestrymonitoring.bluetoothCommunication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.forestrymonitoring.BluetoothActivity;
import com.example.forestrymonitoring.mode.BlueInfo;
import com.example.forestrymonitoring.mode.GroupInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Bluetooth 管理类
 *
 * @author  zch
 *
 */
public class BluetoothManager
{

    // Bluetooth 设备可见时间，单位：秒。
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;

    //
    public static BroadcastReceiver mReceiver = null;

    /**
     * 当前 Android 设备是否支持 Bluetooth
     *
     * @return true：支持 Bluetooth false：不支持 Bluetooth
     */
    public static boolean isBluetoothSupported()
    {
        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
    }

    /**
     * 当前 Android 设备的 bluetooth 是否已经开启
     *
     * @return true：Bluetooth 已经开启 false：Bluetooth 未开启
     */
    public static boolean isBluetoothEnabled()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    public static boolean turnOnBluetooth()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    /**
     *  弹出系统提示开启当前 Android 设备的 Bluetooth
     * @param Activity Activity.this
     */
    public static void turnOnBluetooth(Activity Activity)
    {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        Activity.startActivity(intent);
        // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
        //requestBluetoothOn.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // 设置 Bluetooth 设备可见时间
//        requestBluetoothOn.putExtra(
//                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
//                BLUETOOTH_DISCOVERABLE_DURATION);

        // 请求开启 Bluetooth
//        Activity.startActivityForResult(requestBluetoothOn,
//                REQUEST_CODE_BLUETOOTH_ON);
    }

    /**
     * 强制关闭当前 Android 设备的 Bluetooth
     *
     * @return  true：强制关闭 Bluetooth　成功　false：强制关闭 Bluetooth 失败
     */
    public static boolean turnOffBluetooth()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }

        return false;
    }

    /**
     *  所有的已经配对的蓝牙适配器对象
     * @return  List<BluetoothDevice>
     */
    public static List<BluetoothDevice> getDevice(){
        // 得到所有的已经配对的蓝牙适配器对象
        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
        if (devices.size() > 0){
            for (Iterator iterator = devices.iterator(); iterator.hasNext();){
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                bluetoothDeviceList.add(bluetoothDevice);
            }
        }
        return bluetoothDeviceList;
    }

    /**
     * 所有的已经配对的蓝牙适配器对象
     * @param context Activity.this
     * @return GroupInfo
     */
    public static GroupInfo findPareDevice(Context context){
        //得到BluetoothAdapter对象
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        List<BlueInfo> blueInfoList = new ArrayList<>();
        if(adapter != null){
            System.out.println("本机拥有蓝牙设备");
            // 判断蓝牙设备是否打开
            if(!adapter.isEnabled()){//如果蓝牙没有打开
                //调用系统打开蓝牙的activity
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(intent);
                // 设置请求权限的提示内容 可以不设置
                //turnOnBluetooth();
            }
            // 得到所有的已经配对的蓝牙适配器对象
            Set<BluetoothDevice> devices = adapter.getBondedDevices();
            if (devices.size() > 0){
                //blueAddress = new StringBuffer("已配对蓝牙设备地址：\n");
                for (Iterator iterator = devices.iterator(); iterator.hasNext();){
                    BlueInfo blueInfo = new BlueInfo();
                    // 得到远程蓝牙设备
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                    blueInfo.setIdentificationName(bluetoothDevice.getName());
                    blueInfo.setDeviceAddress(bluetoothDevice.getAddress());
                    blueInfo.setBluetoothDevice(bluetoothDevice);
                    // 加入蓝牙列表
                    blueInfoList.add(blueInfo);
                }
            }
        } else {
            Log.e("NoBluetooth", "本机没有蓝牙设备");
            Toast.makeText(context,"本机没有蓝牙设备",Toast.LENGTH_SHORT).show();
        }
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName("已配对蓝牙列表");
        groupInfo.setBlueInfoList(blueInfoList);
        return groupInfo;
    }

    /**
     *  未适配蓝牙列表
     *  @param context Activity.this
     * @return GroupInfo
     */
    public static GroupInfo findUnPareDevice(Context context){
        GroupInfo groupInfo = new GroupInfo();
        List<BlueInfo> blueInfoList = new ArrayList<>();
        BlueInfo blueInfo = new BlueInfo();
        blueInfo.setIdentificationName("暂未实现");
        blueInfo.setDeviceAddress("FF:FF:FF:FF:FF:FF");
        blueInfoList.add(blueInfo);
        groupInfo.setBlueInfoList(blueInfoList);
        groupInfo.setGroupName("未配对蓝牙列表");
        return groupInfo;
    }

    /**
     *  未配对蓝牙列表
     * @param context Activity.this
     * @return List<BluetoothDevice>
     */
    public static List<BluetoothDevice> getUnPareDevice(Context context){
        List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
        //开始收索 搜索接收函数：
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction(); // 当发现找到一个设备
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // 从Inten中获取一个 BlueToothDevice对象
                    BluetoothDevice device =  intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show  in a ListView
                    // mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // 搜索接收函数需要注册：
        // 注册 BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);// Don't forget to unregister  during onDestroy
        context.unregisterReceiver(mReceiver);

        return bluetoothDeviceList;
    }
}