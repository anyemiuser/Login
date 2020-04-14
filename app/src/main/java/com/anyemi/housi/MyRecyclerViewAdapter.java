package com.anyemi.housi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

 private ArrayList<Integer> mData;
 private LayoutInflater mInflater;
 private ItemClickListener mClickListener;
 Context context;

 // data is passed into the constructor
 MyRecyclerViewAdapter(Context context, ArrayList<Integer>data) {
     this.mInflater = LayoutInflater.from(context);
     this.mData = data;
     this.context=context;
 }

 // inflates the cell layout from xml when needed
 @Override
 @NonNull
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = mInflater.inflate(R.layout.recycleview_item, parent, false);
     return new ViewHolder(view);
 }

 // binds the data to the TextView in each cell
 @Override
 public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     if(mData.get(position)>0&&mData.get(position).equals(position+1)){
         holder.myTextView.setText(String.valueOf(mData.get(position)));
         holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
     }else {
         holder.myTextView.setText("");
         holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

     }

 }

 // total number of cells
 @Override
 public int getItemCount() {
     return mData.size();
 }


 // stores and recycles views as they are scrolled off screen
 public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
     TextView myTextView;

     ViewHolder(View itemView) {
         super(itemView);
         myTextView = itemView.findViewById(R.id.info_text);
         itemView.setOnClickListener(this);
     }

     @Override
     public void onClick(View view) {
         if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
     }
 }

 // convenience method for getting data at click position
 Integer getItem(int id) {
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


