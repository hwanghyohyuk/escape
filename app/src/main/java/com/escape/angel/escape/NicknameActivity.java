package com.escape.angel.escape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.Settings.Secure;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class NicknameActivity extends AppCompatActivity {
    /*버튼 선언*/
    Button button1;
    EditText et_Nickname;

    public char check;

    public SharedPreferences prefs;

    public String isFirstRun;

    Server server = new Server();
    String serverIP = server.getSERVERIP();

    String setName;

    public void checkNickRun(){
        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        prefs.edit().putString("UserName","").apply(); //닉네임 초기화
        isFirstRun = prefs.getString("UserName","");
        if(isFirstRun.equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(),"닉네임을 설정해주세요.",Toast.LENGTH_LONG);
            mToast.show();
        }else {
            Intent mIntent = new Intent(NicknameActivity.this, MainActivity.class);
            startActivity(mIntent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
    }
//시간차를 둬서 데이터입력 및 중복확인
    Handler handler = new Handler();
    Runnable confirm = new Runnable() {
        @Override
        public void run() {
            if(check=='0') {
                //프리퍼런스에 저장
            prefs.edit().putString("UserName", setName).apply();
            Intent mIntent = new Intent(NicknameActivity.this, MainActivity.class);
            startActivity(mIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            }else{
                Toast.makeText(getApplicationContext(),"닉네임이 중복됩니다.",Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);


        checkNickRun();

        button1 = (Button)findViewById(R.id.button1);
        et_Nickname = (EditText)findViewById(R.id.et_Nickname);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에디트텍스트내용을 문자열변수에 저장
                setName = et_Nickname.getText().toString();
                if(setName.equals("")) {//닉네임 유효성검사
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요.",Toast.LENGTH_LONG).show();
                }else{
                    //디비에 저장
                    insertToDatabase(setName);
                    //디비에 저장할동안 0.1초의 딜레이를 줌
                    handler.postDelayed(confirm, 100);
                }
            }
        });
    }

    private void insertToDatabase(String name){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                loading = ProgressDialog.show(NicknameActivity.this,
                        "Please Wait", null, true, true);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];

                    String link= serverIP+"insertUser.php";

                    String data  = URLEncoder.encode("NAME", "UTF-8") + "="
                            + URLEncoder.encode(name, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    String v = sb.toString();
                    check = v.charAt(1);
                    return null;
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name);
    }


}
