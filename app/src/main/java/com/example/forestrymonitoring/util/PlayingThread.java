package com.example.forestrymonitoring.util;

import android.media.MediaPlayer;
import android.os.Looper;

import com.example.forestrymonitoring.common.ChatConstant;

import java.io.IOException;

/**
 * 播放wav
 * Created by tucaoxingren on 2018/4/12 0012.
 */

public class PlayingThread implements Runnable {
    @Override
    public void run() {
        Looper.prepare();
        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(ChatConstant.appDirectory+"music.wav");
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }
        Looper.loop();
    }
}
