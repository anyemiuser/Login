package com.anyemi.housi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends  AppCompatActivity {
    private EditText et_JoinId;
    private Button btn_playonline, btn_createroom, btn_joinroom,btn_Join,btn_game_history;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        btn_playonline = findViewById(R.id.btn_play_online);
        btn_createroom = findViewById(R.id.btn_create_room);
        btn_joinroom = findViewById(R.id.btn_join_room);
        btn_game_history=findViewById(R.id.btn_game_history);
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
                        if(performValidation()) {
                            postjoinroom();
                        }


                    }
                });
            }

        });
        btn_game_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GameHistory.class);
                startActivity(intent);
            }
        });


    }

    private boolean performValidation() {
        boolean isValid = false;


        if  (et_JoinId.getText().toString().equals("")) {
            et_JoinId.setError(" Please Enter Room Id");
            et_JoinId.requestFocus();
        }

        else {


            isValid = true;
        }
        return isValid;

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
                showDialog();
             /*   Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                startActivity(mediaActivity);*/
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
            requestObject.put("no_of_tickets","2");
            requestObject.put("amount","100");
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }
    public void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(getApplicationContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setMessage("Enter No Of Tickets");
       // alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable YouEditTextValue = edittext.getText();
                //OR
                String noOfTickets = edittext.getText().toString();

                    Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                    mediaActivity.putExtra("noOfTickets", noOfTickets);
                    startActivity(mediaActivity);
                }

        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Are you sure you want to exit from Game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

finish();}
                })
                .setNegativeButton("No", null).show();
    }

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
