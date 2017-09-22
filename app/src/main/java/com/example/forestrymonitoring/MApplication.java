package com.example.forestrymonitoring;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 吐槽星人 on 2017/9/22 0022.
 */

public class MApplication extends Application {

    /**
     * 上下文对象
     */
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this.getApplicationContext());
    }

}