package com.example.forestrymonitoring.bluetoothCommunication;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by 吐槽星人 on 2017/10/14 0014.
 *  蓝牙通信监听
 */

public class BluetoothClient {

    private String UUIDStr = "5d0b9e95-d5fe-41d2-80af-375b45d2159f";
    BluetoothDevice device = null;

    //通过构造函数来传入一个BluetoothDevice实例
    public BluetoothClient(BluetoothDevice device) {
        this.device = device;
    }
    BluetoothSocket socket = null;
    public void connetServer() throws IOException {
        Thread _clientThread = new Thread(new Runnable() {
            public void run() {
                try {
                    //通过BluetoothDevice实例的createRfcommSocketToServiceRecord方法
                    // 可以返回一个带有UUID的BluetoothSocket实例
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString(UUIDStr));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    socket.connect();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        });
        _clientThread.start();
    }
}
