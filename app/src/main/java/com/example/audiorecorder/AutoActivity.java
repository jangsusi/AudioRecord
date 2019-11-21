package com.example.audiorecorder;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AutoActivity extends AppCompatActivity {
    private Button btnStart;
    private Button btnStop;
    private AutoMethod autoVoiceRecorder;

    private TextView statusTextView;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        autoVoiceRecorder = new AutoMethod( handler );
        statusTextView = (TextView)findViewById( R.id.text_view_status );
        btnStart = (Button)findViewById( R.id.btn_start );
        btnStop = (Button)findViewById( R.id.btn_stop );
        statusTextView.setText("준비..");
        autoVoiceRecorder.startLevelCheck();
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });

        btnStop.setOnClickListener( new OnClickListener(){
            @Override
            public void onClick(View v) {
                autoVoiceRecorder.stopLevelCheck();
            }
        });
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch( msg.what ){
                case AutoMethod.VOICE_READY:
                    statusTextView.setText("준비...");
                    break;
                case AutoMethod.VOICE_RECONIZING:
                    statusTextView.setTextColor( Color.YELLOW );
                    statusTextView.setText("목소리 인식중...");
                    break;
                case AutoMethod.VOICE_RECONIZED :
                    statusTextView.setTextColor( Color.GREEN );
                    statusTextView.setText("목소리 감지... 녹음중...");
                    break;
                case AutoMethod.VOICE_RECORDING_FINSHED:
                    statusTextView.setTextColor( Color.YELLOW );
                    statusTextView.setText("목소리 녹음 완료 재생 버튼을 누르세요...");
                    break;
                case AutoMethod.VOICE_PLAYING:
                    statusTextView.setTextColor( Color.WHITE );
                    statusTextView.setText("플레이중...");
                    break;
            }
        }
    };
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
}
