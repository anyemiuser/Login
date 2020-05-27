package com.anyemi.housi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyemi.housi.HomeActivity;
import com.anyemi.housi.R;
import com.anyemi.housi.model.UsersListModel;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class JoinedUsersAdapter extends ArrayAdapter<UsersListModel.PlayersBean> {
    private int resourceLayout;
    private Context mContext;
    public JoinedUsersAdapter(@NonNull Context context, int resource, @NonNull List<UsersListModel.PlayersBean> objects) {
        super(context, resource, objects);
        this.resourceLayout=resource;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        LinearLayout layout=v.findViewById(R.id.user_layout);
        LinearLayout layout1=v.findViewById(R.id.join_layout);
        layout.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        CircleImageView image = (CircleImageView) v.findViewById(R.id.profile_pic);
        TextView name = (TextView) v.findViewById(R.id.profile_name);
        Button button=v.findViewById(R.id.select_btn);
        UsersListModel.PlayersBean p = getItem(position);
        name.setText(p.getName());
        String user_id= SharedPreferenceUtil.getId(mContext);

        if(p.getUser_id().equals(user_id)){
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof HomeActivity) {
                        ((HomeActivity)mContext).showDialog();
                    }
                }
            });
        }

        if(p.getImg()!=null){
            Glide.with(mContext)
                    .load(p.getImg())
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(image);
        }

        return v;
    }
}
