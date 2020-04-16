package com.anyemi.housi;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class TicketRecyclerViewAdapter extends RecyclerView.Adapter<TicketRecyclerViewAdapter.ViewHolder> {

 private List<List<List<Integer>>> mData;
 private LayoutInflater mInflater;
 private ItemClickListener mClickListener;
 Context context;
 ArrayList<Integer> tickedValues=new ArrayList<>();

 // data is passed into the constructor
 TicketRecyclerViewAdapter(Context context, List<List<List<Integer>>>data) {
     this.mInflater = LayoutInflater.from(context);
     this.mData = data;
     this.context=context;
 }

 // inflates the cell layout from xml when needed
 @Override
 @NonNull
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = mInflater.inflate(R.layout.recycleview_item_ticket_main, parent, false);
     return new ViewHolder(view);
 }

 // binds the data to the TextView in each cell
 @Override
 public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     List<List<Integer>> oneticket=mData.get(position);
     for (int i=0;i<oneticket.size();i++){
         LinearLayout linearLayout=new LinearLayout(context);
        // linearLayout.setPadding(0,5,0,0);
         View layout2 = LayoutInflater.from(context).inflate(R.layout.ticket_recycleview_item, holder.linearLayout, false);
         LinearLayout layout=layout2.findViewById(R.id.layout_text);
         List<Integer> list=oneticket.get(i);
         for(int j=0;j<list.size();j++){
             final TextView view1=new TextView(context);
             LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                     35,
                     LinearLayout.LayoutParams.MATCH_PARENT,
                     1.0f
             );
             param.setMargins(0,5,10,0);
             view1.setLayoutParams(param);
             view1.setBackgroundColor(context.getResources().getColor(R.color.grey));
             view1.setPadding(1,1,1,1);
             view1.setGravity(Gravity.CENTER);
             if(list.get(j)>0) {
                 view1.setText(String.valueOf(list.get(j)));
                 view1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         TextView myTextView = (TextView)v;
                         Integer val=Integer.parseInt(myTextView.getText().toString());
                         if(!tickedValues.contains(val)){
                             tickedValues.add(val);
                             view1.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                         }else {
                             tickedValues.remove(val);
                             view1.setBackgroundColor(context.getResources().getColor(R.color.grey));
                         }
                     }
                 });
             }
             else
                 view1.setText("");
             layout.addView(view1);
         }
         linearLayout.addView(layout2);
         holder.linearLayout.addView(linearLayout);
     }

    /* if(mData.get(position)>0&&mData.get(position).equals(position+1)){
         holder.myTextView.setText(String.valueOf(mData.get(position)));
         holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
     }else {
         holder.myTextView.setText("");
         holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

     }*/

 }

 // total number of cells
 @Override
 public int getItemCount() {
     return mData.size();
 }


 // stores and recycles views as they are scrolled off screen
 public class ViewHolder extends RecyclerView.ViewHolder {
     LinearLayout linearLayout;

     ViewHolder(View itemView) {
         super(itemView);
         linearLayout = itemView.findViewById(R.id.row_layout);

     }

 }

 // convenience method for getting data at click position
 List<List<Integer>> getItem(int id) {
     return mData.get(id);
 }

 // allows clicks events to be caught
 void setClickListener(ItemClickListener itemClickListener) {
     this.mClickListener = itemClickListener;
 }

 // parent activity will implement this method to respond to click events
 interface ItemClickListener {
     void onItemClick(View view, int position);
 }
}


