package com.example.forestrymonitoring.bluetoothCommunication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.provider.Settings;

import java.util.Iterator;
import java.util.Set;


/**
 * Bluetooth 管理类
 *
 * @author  zch
 *
 */
public class BluetoothManager
{
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
     * @param MapActivity mapActivity
     */
    public static void turnOnBluetooth(Activity MapActivity)
    {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        MapActivity.startActivity(intent);
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

    public static BluetoothDevice getDevice(String deviceMac){
        // 得到所有的已经配对的蓝牙适配器对象
        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (devices.size() > 0){
            for (Iterator iterator = devices.iterator(); iterator.hasNext();){
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
               if (deviceMac.equals(bluetoothDevice.getAddress()));
                return bluetoothDevice;
            }
        }
        return null;
    }

}