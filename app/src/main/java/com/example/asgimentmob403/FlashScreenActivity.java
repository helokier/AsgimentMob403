package com.example.asgimentmob403;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.asgimentmob403.Login.LoginActivity;
import com.example.asgimentmob403.Login.RegsiterActivity;

public class FlashScreenActivity extends AppCompatActivity {
    private static int Timeout = 3000;
     Animation top ,bot,mid;
     View line1,line2,line3,line4,line5,line6,line7;
     TextView tx1,tx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        ActionBar  actionBar =getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       Hook();
        Animation();
        change();


    }
    public void Animation(){
        //set Animation
        line1.setAnimation(top);
        line2.setAnimation(top);
        line3.setAnimation(top);
        line4.setAnimation(top);
        line5.setAnimation(top);
        line6.setAnimation(top);
        line7.setAnimation(top);
        tx1.setAnimation(mid);
        tx2.setAnimation(bot);
    }
    public void Hook(){
        top = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        mid = AnimationUtils.loadAnimation(this,R.anim.midde_anim);
        bot = AnimationUtils.loadAnimation(this,R.anim.botom_anim);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        //Hook
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
    }
    public void change(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },Timeout);
    }
}