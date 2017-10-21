package com.example.forestrymonitoring.bluetoothCommunication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.forestrymonitoring.common.ChatConstant;

import java.io.IOException;

/**
 * Created by 吐槽星人 on 2017/10/14 0014.
 * 创建服务端线程
 */

public class AcceptThread extends Thread {

    private final BluetoothServerSocket mmServerSocket;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("ForestryMon", ChatConstant.UUID_SECURE);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    @Override
    public void run() {
        BluetoothSocket socket;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                //manageConnectedSocket(socket);//manageConnectedSocket
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    break;
                }
                break;
            }
        }
    }

    // Will cancel the listening socket, and cause the thread to finish

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            // Log.e();
        }
    }
}
