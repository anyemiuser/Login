package com.anyemi.housi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NumbersActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    ArrayList<Integer> data=new ArrayList<>();
    Button generate_btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

       // String[] data = null;

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

    }
}


