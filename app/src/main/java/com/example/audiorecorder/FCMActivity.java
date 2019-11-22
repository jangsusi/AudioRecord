package com.example.audiorecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class FCMActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        FirebaseMessaging.getInstance().subscribeToTopic("TOPIC_NAME");
        Log.e("sdf",FirebaseInstanceId.getInstance().getToken()+"dd");
//ecGuxrm2wfA:APA91bH1jh9L4IfmsNxr33WB5cXBf-5dJebP_HxsRYvqRi3vARC4kuEb88kMDztiudRy8_EgGFtV7j2bIaa7Dve4TngJ0rhVmFgCFUDFshGAqvgpr5xp9WwXrDmTix6JzEX3dnpLHROJ
    }
}
