package com.example.audiorecorder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
        setContentView(R.layout.activity_auto_activity);


        BasicApplication.MyHandler myHandler=new BasicApplication.MyHandler();
        autoVoiceRecorder = new AutoMethod( myHandler );
        statusTextView = (TextView)findViewById( R.id.text_view_status );
        btnStart = (Button)findViewById( R.id.btn_start );
        btnStop = (Button)findViewById( R.id.btn_stop );
        statusTextView.setText("준비..");
        autoVoiceRecorder.startLevelCheck();
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicApplication.createNotification();
            }
        });

        btnStop.setOnClickListener( new OnClickListener(){
            @Override
            public void onClick(View v) {
                autoVoiceRecorder.stopLevelCheck();
            }
        });
    }

}
