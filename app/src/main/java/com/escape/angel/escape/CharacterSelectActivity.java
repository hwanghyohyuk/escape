package com.escape.angel.escape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class CharacterSelectActivity extends AppCompatActivity {
//작성자 한귤 작업 두번째
    private ImageView iv_Character1,iv_Character2,iv_Character3,iv_Character4;
    private CheckBox cb_Character1,cb_Character2,cb_Character3,cb_Character4;
    private Button btn_confirm;

    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4;

    private SharedPreferences prefs;
    private Integer character;

    private Integer selectedCharacter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_character_select);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        iv_Character1 = (ImageView)findViewById(R.id.iv_Character1);
        iv_Character2 = (ImageView)findViewById(R.id.iv_Character2);
        iv_Character3 = (ImageView)findViewById(R.id.iv_Character3);
        iv_Character4 = (ImageView)findViewById(R.id.iv_Character4);
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.dao);
        bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.dao);
        bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.dao);
        bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.dao);
        cb_Character1 = (CheckBox)findViewById(R.id.cb_Character1);
        cb_Character2 = (CheckBox)findViewById(R.id.cb_Character2);
        cb_Character3 = (CheckBox)findViewById(R.id.cb_Character3);
        cb_Character4 = (CheckBox)findViewById(R.id.cb_Character4);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);

        iv_Character1.setImageBitmap(bitmap1);
        iv_Character2.setImageBitmap(bitmap2);
        iv_Character3.setImageBitmap(bitmap3);
        iv_Character4.setImageBitmap(bitmap4);

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        character = prefs.getInt("Character",0);

        //캐릭터 프리퍼런스 값을 가져와서 값에 해당하는 이미지 뷰와 체크박스의 선택표시
        if(character!=0){
            switch (character){
                case 1:
                    iv_Character1.setBackgroundResource(R.drawable.image_border);
                    cb_Character1.setChecked(true);
                    break;
                case 2:
                    iv_Character2.setBackgroundResource(R.drawable.image_border);
                    cb_Character2.setChecked(true);
                    break;
                case 3:
                    iv_Character3.setBackgroundResource(R.drawable.image_border);
                    cb_Character3.setChecked(true);
                    break;
                case 4:
                    iv_Character4.setBackgroundResource(R.drawable.image_border);
                    cb_Character4.setChecked(true);
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        iv_Character1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_border);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(true);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(false);
                selectedCharacter=1;
            }
        });
        iv_Character2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_border);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(true);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(false);
                selectedCharacter=2;
            }
        });
        iv_Character3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_border);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(true);
                cb_Character4.setChecked(false);
                selectedCharacter=3;
            }
        });
        iv_Character4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_border);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(true);
                selectedCharacter=4;
            }
        });
        cb_Character1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_border);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(true);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(false);
                selectedCharacter=1;
            }
        });
        cb_Character2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_border);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(true);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(false);
                selectedCharacter=2;
            }
        });
        cb_Character3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_border);
                iv_Character4.setBackgroundResource(R.drawable.image_nonborder);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(true);
                cb_Character4.setChecked(false);
                selectedCharacter=3;
            }
        });
        cb_Character4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_Character1.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character2.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character3.setBackgroundResource(R.drawable.image_nonborder);
                iv_Character4.setBackgroundResource(R.drawable.image_border);
                cb_Character1.setChecked(false);
                cb_Character2.setChecked(false);
                cb_Character3.setChecked(false);
                cb_Character4.setChecked(true);
                selectedCharacter=4;
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs = getSharedPreferences("Pref",MODE_PRIVATE);
                prefs.edit().putInt("Character",selectedCharacter).apply();
                Intent mIntent = new Intent(getApplicationContext(),MyPageActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }
}
