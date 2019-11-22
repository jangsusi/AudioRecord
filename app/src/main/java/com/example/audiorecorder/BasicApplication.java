package com.example.audiorecorder;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;

public class BasicApplication extends Application {
    public static Context context;
    public static NotificationCompat.Builder builder;
    public void onCreate(){
        super.onCreate();
        BasicApplication.context=getApplicationContext();
        builder = new NotificationCompat.Builder(context, "compat");
    }
    public static void createNotification() {

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("녹음");
        builder.setContentTitle("녹음시작");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
            builder.setChannelId("default");
            notificationManager.notify(1, builder.build());

        }
    }

    public static void removeNotification(){
        NotificationManagerCompat.from(context).cancel(1);
    }


    public static class MyHandler extends Handler{
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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void check_permission(final Activity activity){
        int write= ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read=ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        if (write== PackageManager.PERMISSION_DENIED||read==PackageManager.PERMISSION_DENIED) {
            //사용자가 다시보지않깅 체크하지않고 거절한경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                new AlertDialog.Builder(context).setTitle("알림").setMessage("권한을 허가해야만 이용가능합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //사용자가확인버튼을눌렀을때 권한다이얼로그요청
                        if (Build.VERSION.SDK_INT >= M) {
                            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 0);
                        }
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "권한취소", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            }
            //권한요청을 하지 않았던경우
            else {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 0);
            }
        }
        //권한승락한경우
        else {
            //권한이 있을 경우
        }

    }
}
