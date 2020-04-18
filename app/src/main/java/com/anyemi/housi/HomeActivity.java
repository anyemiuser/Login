package com.anyemi.housi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends  AppCompatActivity {
    private EditText et_JoinId;
    private Button btn_playonline, btn_createroom, btn_joinroom,btn_Join;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        btn_playonline = findViewById(R.id.btn_play_online);
        btn_createroom = findViewById(R.id.btn_create_room);
        btn_joinroom = findViewById(R.id.btn_join_room);
        btn_createroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcreateroom();
            }
        });
        btn_joinroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_joinroom_popup);
                et_JoinId = findViewById(R.id.et_Join_id);
                btn_Join = findViewById(R.id.btn_join);

                btn_Join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postjoinroom();

                    }
                });
            }

        });


    }
    private void getcreateroom() {

        new BackgroundTask(HomeActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {

                return ApiServices.createroom(HomeActivity.this, SharedPreferenceUtil.getId(getApplicationContext()));
            }

           public void taskCompleted(Object data) {


               Globals.showToast(getApplicationContext(), data.toString());


              /* Intent i = new Intent(getApplicationContext(), MobileLoginActivity.class);
                startActivity(i);*/


           }
        }, getString(R.string.loading_txt)).execute();
    }
   private void postjoinroom() {

        new BackgroundTask(HomeActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {


                return ApiServices.joinroom(HomeActivity.this,joinroomRequestModel());
            }

            public void taskCompleted(Object data) {

               // CreateRoom createRoom =new CreateRoom();
               // createRoom= new Gson().fromJson(data.toString(),CreateRoom.class);
                SharedPreferenceUtil.setRoom_id(getApplicationContext(), et_JoinId.getText().toString());
               // SharedPreferenceUtil.setUser_id(getApplicationContext(), createRoom.getUser_id());

                Globals.showToast(getApplicationContext(), data.toString());

                Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                startActivity(mediaActivity);


           }
        }, getString(R.string.loading_txt)).execute();
    }
    private String joinroomRequestModel() {


        String user_id , game_id;
        user_id=SharedPreferenceUtil.getId(getApplicationContext());
        game_id=et_JoinId.getText().toString();
       // room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());

        //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {


            requestObject.put("user_id", user_id);
             requestObject.put("game_id", game_id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }
/*private String prepareRequest(){
    String room_id;
    room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());
    JSONObject requestObject = new JSONObject();
    try {
        requestObject.put("Room_id", room_id);
       // requestObject.put("game_id", game_id);
        System.out.println(requestObject.toString());
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return requestObject.toString();
}*/
}
