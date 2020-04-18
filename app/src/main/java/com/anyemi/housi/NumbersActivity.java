package com.anyemi.housi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.model.TicketModel;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NumbersActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    ArrayList<Integer> data=new ArrayList<>();
    Button generate_btn;
    TextView textView;
    RecyclerView rvtickets;
    TicketModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_numbers);
        rvtickets=findViewById(R.id.rvtickets);

        this.generateTicket();
        for(int i=0;i<90;i++){
            data.add(0);
        }
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 10;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
      //  adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        final Random r = new Random();
        textView= (TextView) findViewById(R.id.randomText);
        generate_btn=(Button)findViewById(R.id.generate_btn);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number=r.nextInt(90)+1;
                textView.setText(Integer.toString(number));
                data.set(number-1,number);
               // adapter = new MyRecyclerViewAdapter(getApplicationContext(), data);
                adapter.notifyDataSetChanged();

            }
        });
        rvtickets.setLayoutManager(new GridLayoutManager(this, 1));


    }
    private void generateTicket() {

        new BackgroundTask(NumbersActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {

                return ApiServices.generateticket(NumbersActivity.this,ticketRequestModel());
            }

            public void taskCompleted(Object data) {
                Globals.showToast(getApplicationContext(), data.toString());
                Log.e("response",data.toString());
                Gson gson=new Gson();
                 model=gson.fromJson(data.toString(),TicketModel.class);
                List<List<List<Integer>>> data1=model.getTicket_nos();

                TicketRecyclerViewAdapter adapter1 = new TicketRecyclerViewAdapter(getApplicationContext(), data1);
                //  adapter.setClickListener(this);
                rvtickets.setAdapter(adapter1);
        }
        }, getString(R.string.loading_txt)).execute();
    }
    private String ticketRequestModel() {


        String user_id , game_id;
        user_id=SharedPreferenceUtil.getId(getApplicationContext());
        game_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());
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


}


