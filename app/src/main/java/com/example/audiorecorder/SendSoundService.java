package com.example.audiorecorder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class SendSoundService extends Service {

    public SendSoundService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 서비스 최초 실행시 동작
    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 백그라운드 작동 동작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // 서비스 종료시 동작
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
