package com.example.audiorecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedList;

public class NoticeSelectActivity extends AppCompatActivity {
    RadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9,radio10;
    LinkedList<RadioButton> radioButtons;
    Button selectd_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_select);
        final Intent intent=getIntent();
        radioButtons=new LinkedList<RadioButton>();

        String tag=intent.getStringExtra("selected");
        radio1=(RadioButton)findViewById(R.id.radio1);

        radio2=(RadioButton)findViewById(R.id.radio2);
        radio3=(RadioButton)findViewById(R.id.radio3);
        radio4=(RadioButton)findViewById(R.id.radio4);
        radio5=(RadioButton)findViewById(R.id.radio5);
        radio6=(RadioButton)findViewById(R.id.radio6);
        radio7=(RadioButton)findViewById(R.id.radio7);
        radio8=(RadioButton)findViewById(R.id.radio8);
        radio9=(RadioButton)findViewById(R.id.radio9);
        radio10=(RadioButton)findViewById(R.id.radio10);

        radioButtons.add(radio1);radioButtons.add(radio2);
        radioButtons.add(radio3);radioButtons.add(radio4);
        radioButtons.add(radio5);radioButtons.add(radio6);
        radioButtons.add(radio7);radioButtons.add(radio8);
        radioButtons.add(radio9);radioButtons.add(radio10);
        for(int i=0;i<10;i++){
            if(tag.equals(radioButtons.get(i).getTag().toString())){
                radioButtons.get(i).setChecked(true);
                selectd_btn=radioButtons.get(i);
            }
        }
        ImageView back=(ImageView)findViewById(R.id.back);
        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),NoticeMainActivity.class);
                intent1.putExtra("selected_back",selectd_btn.getTag().toString());
                intent1.putExtra("selected_txt",selectd_btn.getText().toString());

                startActivity(intent1);
                finish();
            }
        });
    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.radio1){

                Intent intent=new Intent(getApplicationContext(),NoticeMainActivity.class);
                intent.putExtra("radio",1);
                startActivity(intent);
                finish();
            }
            else if(i == R.id.radio2){
                Intent intent=new Intent(getApplicationContext(),NoticeMainActivity.class);
                intent.putExtra("radio",2);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        for(int i=0;i<10;i++){
                radioButtons.get(i).setVisibility(View.VISIBLE);
        }
    }
}
