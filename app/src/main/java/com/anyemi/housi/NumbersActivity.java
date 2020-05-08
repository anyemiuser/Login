package com.anyemi.housi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.housi.Adapters.GameUsersAdapter;
import com.anyemi.housi.connection.ApiServices;

import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.model.TicketsModel;
import com.anyemi.housi.model.UsersListModel;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;

public class NumbersActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    ArrayList<Integer> data = new ArrayList<>();
    ArrayList<Integer> data_all = new ArrayList<>();
    Button generate_btn;
    TextView textView;
    RecyclerView rvtickets;
    RecyclerView recyclerView,rvList;
    TicketsModel model;
    int random_number_index = 0;
    String noOfTickets = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        getSupportActionBar().hide();
        getUsers();
        generateTicket();

        for (int i = 0; i < 90; i++) {
            data.add(0);

        }
        noOfTickets = getIntent().getStringExtra("noOfTickets");
        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        rvList=(RecyclerView)findViewById(R.id.rvList);
        rvList.setLayoutManager(new GridLayoutManager(this, 1));

        rvtickets = findViewById(R.id.rvtickets);
        int numberOfColumns = 10;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 10));
        adapter = new MyRecyclerViewAdapter(this, data);
        //  adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        final Random r = new Random();
        textView = (TextView) findViewById(R.id.randomText);
        generate_btn = (Button) findViewById(R.id.generate_btn);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number = r.nextInt(90) + 1;
                textView.setText(Integer.toString(number));
                data.set(number - 1, number);
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
                return ApiServices.generateticket(NumbersActivity.this, ticketRequestModel());
            }

            public void taskCompleted(Object data) {
                  Log.e("response tickets",data.toString());
                // Globals.showToast(getApplicationContext(), data.toString());
                Gson gson = new Gson();
                model = gson.fromJson(data.toString(), TicketsModel.class);
                List<List<List<Integer>>> data1 = model.getTicket_nos();
                TicketRecyclerViewAdapter adapter1 = new TicketRecyclerViewAdapter(getApplicationContext(), data1,model.getTicket_id());
                //  adapter.setClickListener(this);
                rvtickets.setAdapter(adapter1);
                if(model.getNos()!=null) {
                    data_all.addAll(model.getNos());
                    startTimer();
                }
            }
        }, getString(R.string.loading_txt)).execute();
    }


    private String ticketRequestModel() {


        String user_id, game_id;
        user_id = SharedPreferenceUtil.getId(getApplicationContext());
        game_id = SharedPreferenceUtil.getRoom_id(getApplicationContext());
        // room_id=SharedPreferenceUtil.getRoom_id(getApplicationContext());

        //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("user_id", user_id);
            requestObject.put("game_id", game_id);
            requestObject.put("no_of_tickets", noOfTickets);
            requestObject.put("amount", "100");
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }

    private void startTimer() {
        int spam = 10 * 1000;  // 30 sec

        textView.setText(data_all.get(random_number_index) + "");
        data.set(data_all.get(random_number_index) - 1, data_all.get(random_number_index));
        adapter.notifyDataSetChanged();

        new CountDownTimer(spam, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {


                if (random_number_index >= data_all.size() - 1) {
                    Globals.showToast(getApplicationContext(), "Number Completed");
                } else {

                    random_number_index = random_number_index + 1;
                    startTimer();
                }
            }
        }.start();

    }


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Are you sure you want to exit from Game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                    }
                    })
                .setNegativeButton("No", null).show();
    }
  private void getUsers() {

        new BackgroundTask(NumbersActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {

                return ApiServices.getGameUsers(NumbersActivity.this,randomnumberRequestModel());
            }

            public void taskCompleted(Object data) {
                Globals.showToast(getApplicationContext(), data.toString());
                Log.e("responseusers",data.toString());
                Gson gson=new Gson();
                UsersListModel model=gson.fromJson(data.toString(), UsersListModel.class);
                List<UsersListModel.PlayersBean> data1=model.getPlayers();

                GameUsersAdapter adapter1 = new GameUsersAdapter(getApplicationContext(), data1);
                rvList.setAdapter(adapter1);
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

}


