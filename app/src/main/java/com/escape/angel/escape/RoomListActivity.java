package com.escape.angel.escape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class RoomListActivity extends AppCompatActivity {

    private String myJSON; //json객체를 저장할 변수

    private ImageView iv_Main;

    private static final String TAG_RESULTS="RESULT";
    private static final String TAG_RNO = "RNO";
    private static final String TAG_RNAME = "RNAME";
    private static final String TAG_RTYPE ="RTYPE";
    private static final String TAG_RHOST ="RHOST";
    private static final String TAG_RPNO ="RPNO";
    private static final String TAG_HOSTIP ="HOSTIP";

    private JSONArray jroom = null;


    private ArrayList<HashMap<String, String>> roomList;

    private ListView list;
    private Button btn_CreateRoom,btn_Refresh;

    private Server server = new Server();
    private String serverIP = server.getSERVERIP();

    private SharedPreferences prefs;
    private String Utype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_room_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        iv_Main = (ImageView)findViewById(R.id.iv_Main);

        iv_Main.setImageResource(R.drawable.background);
        list = (ListView) findViewById(R.id.listView);
        btn_CreateRoom = (Button)findViewById(R.id.btn_CreateRoom);
        btn_Refresh = (Button)findViewById(R.id.btn_Refresh);

        roomList = new ArrayList<HashMap<String,String>>();

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        Utype = prefs.getString("Utype","");
        Toast.makeText(getApplicationContext(),Utype,Toast.LENGTH_SHORT).show();

        /*
        if(!RoomListActivity.this.isFinishing()) {
            Toast mToast = Toast.makeText(getApplicationContext(),"해당 액티비티가 종료되었습니다.",Toast.LENGTH_LONG);
            mToast.show();

        }
        */
        btn_CreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(getApplicationContext(),CreateRoomActivity.class);
                startActivity(mIntent);
            }
        });
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomList.clear();
                getData(serverIP+"getRoomlist.php");
            }
        });
    }

    protected void onStart(){
        super.onStart();
        roomList.clear();
        getData(serverIP+"getRoomlist.php");
    }


    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            jroom = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<jroom.length();i++){
                JSONObject c = jroom.getJSONObject(i);
                String rno = c.getString(TAG_RNO);
                String rname = c.getString(TAG_RNAME);
                String rtype = c.getString(TAG_RTYPE);
                String rhost = c.getString(TAG_RHOST);
                String rpno = c.getString(TAG_RPNO);
                String hostip = c.getString(TAG_HOSTIP);

                HashMap<String,String> hroom = new HashMap<String, String>();

                hroom.put(TAG_RNO,rno);
                hroom.put(TAG_RNAME,rname);
                hroom.put(TAG_RTYPE,rtype);
                hroom.put(TAG_RHOST,rhost);
                hroom.put(TAG_RPNO,rpno);
                hroom.put(TAG_HOSTIP,hostip);

                roomList.add(hroom);
            }

            final ListAdapter adapter = new SimpleAdapter(
                    RoomListActivity.this, roomList, R.layout.room_list_item,
                    new String[]{TAG_RNO,TAG_RNAME,TAG_RTYPE,TAG_RHOST,TAG_RPNO},
                    new int[]{R.id.rno, R.id.rname, R.id.rtype,R.id.rhost,R.id.rpno}
            );

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //아이템클릭이벤트 방클릭이벤트
                    String getPositiondata = adapterView.getItemAtPosition(i).toString();
                    String firstlast=getPositiondata.substring(1,getPositiondata.length()-1); //{}중괄호 없애기
                    String splitcomma[]=firstlast.split(","); //콤마를 토큰으로 문자열분리
                    String HOSTIP = splitcomma[0].substring(splitcomma[0].indexOf("=")+1,splitcomma[0].length());
                    String RTYPE =  splitcomma[1].substring(splitcomma[1].indexOf("=")+1,splitcomma[1].length());
                    String RNO =    splitcomma[2].substring(splitcomma[2].indexOf("=")+1,splitcomma[2].length());
                    String RPNO =   splitcomma[3].substring(splitcomma[3].indexOf("=")+1,splitcomma[3].length());
                    String RHOST =  splitcomma[4].substring(splitcomma[4].indexOf("=")+1,splitcomma[4].length());
                    String RNAME =  splitcomma[5].substring(splitcomma[5].indexOf("=")+1,splitcomma[5].length());

                    String toastMessage = "HOSTIP : "+HOSTIP+","+"RTYPE : "+RTYPE+","+"RNO : "+RNO+","+
                            "RPNO : "+RPNO+","+"RNAME : "+RNAME+","+"RHOST : "+RHOST;

                    //액티비티 이동 > RTYPE,RNAME,HOSTIP 값넘김
                    Intent mIntent = new Intent(getApplicationContext(),RoomActivity.class);
                    mIntent.putExtra("RNAME",RNAME);
                    mIntent.putExtra("RTYPE",RTYPE);
                    mIntent.putExtra("HOSTIP",HOSTIP);
                    startActivity(mIntent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(),
                    "서버와 연결되지 않았습니다.",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}

