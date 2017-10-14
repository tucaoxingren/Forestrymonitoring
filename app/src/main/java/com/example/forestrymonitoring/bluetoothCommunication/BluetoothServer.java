package com.example.forestrymonitoring.bluetoothCommunication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by 吐槽星人 on 2017/10/14 0014.
 *  Bluetooth的ServerSocket包装类
 */

public class BluetoothServer {

    private String UUIDStr = "5d0b9e95-d5fe-41d2-80af-375b45d2159f";
    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothServer(BluetoothAdapter adapter) throws IOException {
        this.adapter = adapter;
    }

    public BluetoothServer() throws IOException {
    }

    // 要建立一个ServerSocket对象，需要使用adapter.listenUsingRfcommWithServiceRecord方法
    private BluetoothServerSocket serverSocket = adapter.listenUsingRfcommWithServiceRecord("ForestryMon", UUID.fromString(UUIDStr));
    private BluetoothSocket socket = serverSocket.accept();

    public void openServe() throws IOException {
        if (socket != null) {
            // 释放server socket以及相关的资源，但是并不会关闭
            // 从accept()中返回的，已经连接的BluetoothSocket
            serverSocket.close();

            InputStream inputStream = socket.getInputStream();
            int read = -1;
            final byte[] bytes = new byte[1024];
            for (; (read = inputStream.read(bytes)) > -1;) {
                final int count = read;
                Thread _start = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < count; i++) {
                            if (i > 0) {
                                sb.append(' ');
                            }
                            String _s = Integer.toHexString(bytes[i] & 0xFF);
                            if (_s.length() < 2) {
                                sb.append('0');
                            }
                            sb.append(_s);
                        }
                        System.out.println(sb.toString());
                    }
                });
                _start.start();
            }
            //serverSocket.close();
        }
    }
}
