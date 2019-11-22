package com.example.audiorecorder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AutoOrPassive extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.autoor);
        BasicApplication.check_permission(this);
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

}
