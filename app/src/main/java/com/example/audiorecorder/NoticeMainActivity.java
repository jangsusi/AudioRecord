package com.example.audiorecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NoticeMainActivity extends AppCompatActivity {
    private final String TAG = "[NoticeMain]";
    private TextView txt;
    private ToggleButton btnToggleNotice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_main);
        txt=(TextView)findViewById(R.id.txt_thunder);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if(txt.getText().equals("천둥 소리"))
            txt.setTag("10");
        Intent intent1=getIntent();
        String selectd_tag=intent1.getStringExtra("selected_back");
        String selectd_txt=intent1.getStringExtra("selected_txt");
        Log.e("txt",selectd_tag+"");

        if(selectd_txt!=null&&selectd_tag!=null){
            txt.setText(selectd_txt);
            txt.setTag(selectd_tag);
        }
        else{

        }

        ImageView back=(ImageView)findViewById(R.id.back);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NoticeSelectActivity.class);
                intent.putExtra("selected",txt.getTag().toString());
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        Intent intent=getIntent();
        int radio_ch=intent.getIntExtra("radio",0);
        if(radio_ch==1){
            txt.setText("경적 소리");
            txt.setTag("1");
        }
        else if(radio_ch==2){
            txt.setText("사이렌 소리");
            txt.setTag("2");

        }

        final SendSoundThread thread = new SendSoundThread();

        btnToggleNotice = findViewById(R.id.toggle_notice);
        btnToggleNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnToggleNotice.isChecked()) {
                    // TODO : 무한루프 현상 수정
                    Toast.makeText(getApplicationContext(), "Toggle ON", Toast.LENGTH_SHORT).show();
                    thread.run();
                } else {
                    Toast.makeText(getApplicationContext(), "Toggle Off", Toast.LENGTH_SHORT).show();
                    thread.stopForever();
                }
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Intent intent=getIntent();
        String selectd_tag=intent.getStringExtra("selected_back");
        String selectd_txt=intent.getStringExtra("selected_txt");

        txt.setText(selectd_txt);
        txt.setTag(selectd_tag);

    }
}


