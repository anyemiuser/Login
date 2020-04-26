package com.anyemi.housi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyemi.housi.R;
import com.anyemi.housi.model.GameHistoryModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GamehistoryAdapter extends RecyclerView.Adapter<GamehistoryAdapter.ViewHolder> {

    private ArrayList<GameHistoryModel.HistoryBean> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    public GamehistoryAdapter(Context context, ArrayList<GameHistoryModel.HistoryBean>data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_history_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
       holder.tv_gameNo.setText(mData.get(i).getGame_id());
        holder.tv_tickets.setText(mData.get(i).getNooftickets());
        holder.tv_paymentType.setText(mData.get(i).getPayment_type());
        holder.tv_gameNo.setText(mData.get(i).getGame_id());
        holder.tv_amount.setText(mData.get(i).getTotoalamount());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_gameNo,tv_tickets,tv_paymentType,tv_amount;

        ViewHolder(View itemView) {
            super(itemView);
            tv_gameNo = itemView.findViewById(R.id.tv_gameNo);
            tv_tickets=itemView.findViewById(R.id.tv_tickets);
            tv_paymentType=itemView.findViewById(R.id.tv_paymentType);
            tv_amount=itemView.findViewById(R.id.tv_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    GameHistoryModel.HistoryBean getItem(int id) {
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


