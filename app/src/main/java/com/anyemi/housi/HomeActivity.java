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

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends  AppCompatActivity {
    private Button btn_playonline, btn_createroom, btn_joinroom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }


    private void getcreateroom() {

        new BackgroundTask(HomeActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {

                return ApiServices.createroom(HomeActivity.this, SharedPreferenceUtil.getId(getApplicationContext()));
            }

           public void taskCompleted(Object data) {




            /*    Intent mediaActivity = new Intent(getApplicationContext(), MediaActivity.class);
                startActivity(mediaActivity);*/


           }
        }, getString(R.string.loading_txt)).execute();
    }


}