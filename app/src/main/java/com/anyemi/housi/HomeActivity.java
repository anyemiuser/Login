package com.anyemi.housi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anyemi.housi.Adapters.GameUsersAdapter;
import com.anyemi.housi.Adapters.JoinedUsersAdapter;
import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.model.UsersListModel;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends  AppCompatActivity {
    private EditText et_JoinId;
    private Button btn_playonline, btn_createroom, btn_joinroom,btn_Join,btn_game_history;
    ListView lv_users;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        IntentFilter intentFilter = new IntentFilter(FirebaseMessagingServices.ACTION_RECEIVE);
        registerReceiver(receiver, intentFilter);
        btn_playonline = findViewById(R.id.btn_play_online);
        btn_createroom = findViewById(R.id.btn_create_room);
        btn_joinroom = findViewById(R.id.btn_join_room);
        btn_game_history=findViewById(R.id.btn_game_history);
        btn_createroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });
        btn_joinroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_joinroom_popup);
                et_JoinId = findViewById(R.id.et_Join_id);
                btn_Join = findViewById(R.id.btn_join);
                lv_users=findViewById(R.id.lv_users);

                btn_Join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(performValidation()) {
                            postjoinroom();
                        }


                    }
                });
                lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showDialog();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
    private void getUsers() {

        new BackgroundTask(HomeActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.getGameUsers(HomeActivity.this,randomnumberRequestModel());
            }
            public void taskCompleted(Object data) {
                //   Globals.showToast(getApplicationContext(), data.toString());
                Log.e("responseusers",data.toString());
                Gson gson=new Gson();
                UsersListModel model=gson.fromJson(data.toString(), UsersListModel.class);
                List<UsersListModel.PlayersBean> data1=model.getPlayers();
                JoinedUsersAdapter adapter=new JoinedUsersAdapter(HomeActivity.this,R.layout.recycleview_users_item,data1);
                lv_users.setAdapter(adapter);
            }
        }, getString(R.string.loading_txt)).execute();
    }
    private String randomnumberRequestModel() {


        String user_id , game_id;
        user_id=SharedPreferenceUtil.getId(getApplicationContext());
        game_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());
        // room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());

        //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("game_id", game_id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
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
                startGame();
               // getUsers();

                Globals.showToast(getApplicationContext(), data.toString());

             /*   Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                startActivity(mediaActivity);*/
           }
        }, getString(R.string.loading_txt)).execute();
    }
    private void startGame() {

        new BackgroundTask(HomeActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.startGame(HomeActivity.this, startGameModel());
            }

            public void taskCompleted(Object data) {
                Log.e("response users notif",data.toString());
                Globals.showToast(getApplicationContext(), data.toString());
            }
        }, getString(R.string.loading_txt)).execute();
    }

    private String startGameModel() {


        String user_id, game_id;
        user_id = SharedPreferenceUtil.getMobile_number(getApplicationContext());
        game_id = SharedPreferenceUtil.getRoom_id(getApplicationContext());
        // room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());

        //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("regid", user_id);
            requestObject.put("game_id", game_id);
            requestObject.put("type", "GETUSERS");
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
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
    public void showDialog1(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final AlertDialog OptionDialog = alert.create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.create_room, null, false);
        Button proceed=dialogView.findViewById(R.id.proceed);
        Button cancel=dialogView.findViewById(R.id.cancel);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcreateroom();
                OptionDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionDialog.dismiss();

            }
        });

        OptionDialog.setView(dialogView);
        OptionDialog.show();

    }

    public void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.ticket_selection, null, false);
        LinearLayout layout1,layout2,layout3,layout4;
        layout1=dialogView.findViewById(R.id.layout1);
        layout2=dialogView.findViewById(R.id.layout2);
        layout3=dialogView.findViewById(R.id.layout3);
        layout4=dialogView.findViewById(R.id.layout4);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                mediaActivity.putExtra("noOfTickets", "1");
                startActivity(mediaActivity);
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                mediaActivity.putExtra("noOfTickets", "2");
                startActivity(mediaActivity);
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                mediaActivity.putExtra("noOfTickets", "3");
                startActivity(mediaActivity);
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediaActivity = new Intent(getApplicationContext(), NumbersActivity.class);
                mediaActivity.putExtra("noOfTickets", "4");
                startActivity(mediaActivity);
            }
        });




      /*  final EditText edittext = new EditText(getApplicationContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setMessage("Enter No Of Tickets");*/
       // alert.setTitle("Enter Your Title");

        alert.setView(dialogView);

/*
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
*/

/*
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
*/

        alert.show();

    }
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                String type=intent.getStringExtra("type");

                String game_no=SharedPreferenceUtil.getRoom_id(context);

                // if(game_id.equals(game_no)) {
                if (type.equals("GETUSERS")) {
                    getUsers();
                }
                //}
            }

        }
    };


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Are you sure you want to exit from Game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //unregisterReceiver(receiver);
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
