package com.escape.angel.escape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.animation.*;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {
    private ImageView iv_Intro;
    private SharedPreferences prefs;
    private boolean Bgm, Eff;

    private Handler handler = new Handler();
    private Runnable translatePage = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  // 페이지 페이드인 페이드아웃 전환
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        Bgm = prefs.getBoolean("BGM",true);//해당 키에 값이 없으면 TRUE 값으로 초기화하며 값을 가져온다.
        Eff = prefs.getBoolean("EFF",true);//해당 키에 값이 있으면 해당 값을 가져온다.

        iv_Intro = (ImageView)findViewById(R.id.iv_Intro);  //이미지뷰 링킹

        /* 이미지 뷰 페이드인 페이드아웃*/
        Animation fadeIn = new AlphaAnimation(0, 1);  //애니메이션 fadeIn 객체 선언
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setStartOffset(1000);  //시작되는데 걸리는 시간
        fadeIn.setDuration(1000);  //실행시간

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(2500);  //시작되는데 걸리는 시간
        fadeOut.setDuration(1000);  //실행시간

        AnimationSet animation = new AnimationSet(false); // change to false

        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);

        iv_Intro.setAnimation(animation);
        iv_Intro.setVisibility(View.INVISIBLE);

        handler.postDelayed(translatePage, 4000);
    }
    @Override
    protected void onDestroy(){
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }
}
