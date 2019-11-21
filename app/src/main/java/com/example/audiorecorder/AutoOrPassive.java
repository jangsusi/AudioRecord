package com.example.audiorecorder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;

public class AutoOrPassive extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.autoor);
        check_permission();
        Button y_b=findViewById(R.id.auto);
        Button n_b=findViewById(R.id.notauto);
        Button end=findViewById(R.id.service_end);
        y_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),AudioService1.class);
                startService(intent1);
            }

        });
        n_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PassiveActivity.class));
            }

        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),AudioService1.class);
                stopService(intent1);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void check_permission(){
        int write= ContextCompat.checkSelfPermission(AutoOrPassive.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read=ContextCompat.checkSelfPermission(AutoOrPassive.this, Manifest.permission.RECORD_AUDIO);
        if (write== PackageManager.PERMISSION_DENIED||read==PackageManager.PERMISSION_DENIED) {
            //사용자가 다시보지않깅 체크하지않고 거절한경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(AutoOrPassive.this, Manifest.permission.RECORD_AUDIO)) {
                new AlertDialog.Builder(AutoOrPassive.this).setTitle("알림").setMessage("권한을 허가해야만 이용가능합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //사용자가확인버튼을눌렀을때 권한다이얼로그요청
                        if (android.os.Build.VERSION.SDK_INT >= M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 0);
                        }
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "권한취소", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            }
            //권한요청을 하지 않았던경우
            else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 0);
            }
        }
        //권한승락한경우
        else {
            //권한이 있을 경우
        }

    }
}
