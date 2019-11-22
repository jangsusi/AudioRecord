package com.example.audiorecorder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AudioService1 extends Service {
    private AutoMethod autoVoiceRecorder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //createNotification();
        Log.e("service", "시작");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "command시작");
        BasicApplication.MyHandler myHandler=new BasicApplication.MyHandler();
        autoVoiceRecorder = new AutoMethod(myHandler);
        autoVoiceRecorder.startLevelCheck();
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        Log.e("service", "종료");
    }



}
