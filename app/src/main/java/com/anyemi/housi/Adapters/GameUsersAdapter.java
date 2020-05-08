package com.anyemi.housi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.housi.R;
import com.anyemi.housi.model.UsersListModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameUsersAdapter extends RecyclerView.Adapter<GameUsersAdapter.ViewHolder> {

private List<UsersListModel.PlayersBean> mData;
private LayoutInflater mInflater;
private ItemClickListener mClickListener;
        Context context;

        // data is passed into the constructor
        public GameUsersAdapter(Context context, List<UsersListModel.PlayersBean> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
        }

// inflates the cell layout from xml when needed
@Override
@NonNull
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_users_item, parent, false);
        return new ViewHolder(view);
        }

// binds the data to the TextView in each cell
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.myTextView.setText(mData.get(position).getName());
    Glide.with(context)
            .load(mData.get(position).getImg())
            .placeholder(R.drawable.user)
            .error(R.drawable.user)
            .into(holder.imageView);
        }

// total number of cells
@Override
public int getItemCount() {
        return mData.size();
        }


// stores and recycles views as they are scrolled off screen
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView myTextView;
    ImageView imageView;

    ViewHolder(View itemView) {
        super(itemView);
        myTextView = itemView.findViewById(R.id.info_text);
        imageView=itemView.findViewById(R.id.image_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}

    // convenience method for getting data at click position
    UsersListModel.PlayersBean getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(GameUsersAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

// parent activity will implement this method to respond to click events
interface ItemClickListener {
    void onItemClick(View view, int position);
}
}



