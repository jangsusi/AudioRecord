package com.example.audiorecorder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AudioService1 extends Service {
    private AutoMethod autoVoiceRecorder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        createNotification();
        Log.e("service","시작");
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.e("service","command시작");
        autoVoiceRecorder = new AutoMethod( handler );
        autoVoiceRecorder.startLevelCheck();
        return super.onStartCommand(intent,flags,startId);

    }

    @Override
    public void onDestroy(){
        Log.e("service","종료");
    }

    public void createNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"compat");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("녹음");
        builder.setContentTitle("녹음시작");

        NotificationManager notificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel("default","기본채널", NotificationManager.IMPORTANCE_DEFAULT));
            builder.setChannelId("default");
            notificationManager.notify(1,builder.build());

        }


    }

    public void removeNotification(){
        NotificationManagerCompat.from(this).cancel(1);
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch( msg.what ){
                case AutoMethod.VOICE_READY:
                    Log.e("service","준비...");
                    break;
                case AutoMethod.VOICE_RECONIZING:

                    Log.e("service","목소리 인식중...");
                    break;
                case AutoMethod.VOICE_RECONIZED :

                    Log.e("service","목소리 감지... 녹음중...");
                    break;
                case AutoMethod.VOICE_RECORDING_FINSHED:
                    Log.e("service","목소리 녹음 완료 재생 버튼을 누르세요...");


                    break;
                case AutoMethod.VOICE_PLAYING:
                    Log.e("service","플레이중...");
                    break;
            }
        }
    };




}
