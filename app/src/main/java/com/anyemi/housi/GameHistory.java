 package com.anyemi.housi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.anyemi.housi.Adapters.GamehistoryAdapter;
import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.model.GameHistoryModel;
import com.anyemi.housi.model.TicketsModel;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

 public class GameHistory extends AppCompatActivity {
    RecyclerView rvHistory;
    ArrayList<GameHistoryModel.HistoryBean> arry_history;
    GameHistoryModel gameHistoryModel;
    GamehistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Game History");


        rvHistory=findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        getGameHistory();
    }
     private String ticketRequestModel() {


         String user_id , game_id;
         user_id= SharedPreferenceUtil.getId(getApplicationContext());
         game_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());
         // room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());

         //   version_id = BuildConfig.VERSION_NAME;

         JSONObject requestObject = new JSONObject();
         try {


             requestObject.put("user_id", user_id);
             System.out.println(requestObject.toString());
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return requestObject.toString();
     }

     private void getGameHistory() {

         new BackgroundTask(GameHistory.this, new BackgroundThread() {
             @Override
             public Object runTask() {
                 return ApiServices.getGameHistory(GameHistory.this,ticketRequestModel());
             }

             public void taskCompleted(Object data) {
               //  Globals.showToast(getApplicationContext(), data.toString());

                 //  Log.e("response",data.toString());
                 Gson gson=new Gson();
                 gameHistoryModel=gson.fromJson(data.toString(),GameHistoryModel.class);
                 arry_history=new ArrayList<>();
                 arry_history.addAll(gameHistoryModel.getHistory());
                 adapter=new GamehistoryAdapter(getApplicationContext(),arry_history);
                 rvHistory.setAdapter(adapter);

             }
         }, getString(R.string.loading_txt)).execute();
     }
     @Override
     public boolean onSupportNavigateUp() {
         finish();
         return true;
     }

 }
