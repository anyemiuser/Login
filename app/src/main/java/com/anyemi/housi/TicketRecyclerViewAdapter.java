package com.anyemi.housi;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.model.TicketsModel;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class TicketRecyclerViewAdapter extends RecyclerView.Adapter<TicketRecyclerViewAdapter.ViewHolder> {

 private List<List<List<Integer>>> mData;
 private LayoutInflater mInflater;
 private ItemClickListener mClickListener;
 private List<Integer> ticketIds;
 Context context;
    int ticketId;

    ArrayList<Integer> tickedValues=new ArrayList<>();

 // data is passed into the constructor
 TicketRecyclerViewAdapter(Context context, List<List<List<Integer>>>data,List<Integer> ticketIds) {
     this.mInflater = LayoutInflater.from(context);
     this.mData = data;
     this.context=context;
     this.ticketIds=ticketIds;
 }

 // inflates the cell layout from xml when needed
 @Override
 @NonNull
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = mInflater.inflate(R.layout.recycleview_item_ticket_main, parent, false);
     return new ViewHolder(view);
 }

 // binds the data to the TextView in each cell

    //uma***************


   

 @Override
 public void onBindViewHolder(@NonNull  ViewHolder holder,  int position) {
     List<List<Integer>> oneticket=mData.get(position);
     final List<Integer> lists=new ArrayList<>();
   //  final int ticketId=ticketIds.get(position);
     for (int i=0;i<oneticket.size();i++){
         LinearLayout linearLayout=new LinearLayout(context);
        // linearLayout.setPadding(0,5,0,0);
         View layout2 = LayoutInflater.from(context).inflate(R.layout.ticket_recycleview_item, holder.linearLayout, false);
         LinearLayout layout=layout2.findViewById(R.id.layout_text);
         List<Integer> list=oneticket.get(i);
         Log.e("list",list.toString());

       //  Collections.sort(list);

         for(int j=0;j<list.size();j++){
             final TextView view1=new TextView(context);
             LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                     55,
                     LinearLayout.LayoutParams.MATCH_PARENT,
                     1.0f
             );
             if(j%2==1) {
                 param.setMargins(0, 0, 0, 0);
                 view1.setLayoutParams(param);
                 view1.setBackgroundColor(context.getResources().getColor(R.color.grey));
                 view1.setPadding(1, 1, 1, 1);
                 view1.setGravity(Gravity.CENTER);
             }
             else{
                 param.setMargins(0, 0, 0, 0);
                 view1.setLayoutParams(param);
                 view1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                 view1.setTextColor(context.getResources().getColor(R.color.white));
                 view1.setPadding(1, 1, 1, 1);
                 view1.setGravity(Gravity.CENTER);
             }
             if(list.get(j)>0) {
                 lists.add(list.get(j));
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
     final int no=position+1;
     final int pos=position;
     holder.btn_claim.setText("TICKET"+no+" CLAIM");
     final ViewHolder holder1=holder;
     holder.btn_claim.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Button btn = (Button) v;

             if(btn.getText().toString().equals("TICKET"+no+" CLAIM")){
                 holder1.btn_claim.setText("TICKET"+no+" HIDE");
               ticketId=ticketIds.get(pos);
                 holder1.claim_layout.setVisibility(View.VISIBLE);
               //  claimTicket(ticketId,lists);

             }else {
                 holder1.btn_claim.setText("TICKET"+no+" CLAIM");
                 holder1.claim_layout.setVisibility(View.GONE);
             }

         }
     });
     holder.btn_first.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             claimTicket(ticketId,lists,"first");
         }
     });
     holder.btn_top.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             claimTicket(ticketId,lists,"top");
         }
     });
     holder.btn_middle.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             claimTicket(ticketId,lists,"middle");
         }
     });
     holder.btn_bottom.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             claimTicket(ticketId,lists,"last");
         }
     });
     holder.btn_full.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             claimTicket(ticketId,lists,"full");
         }
     });


 }

 // total number of cells
 @Override
 public int getItemCount() {
     return mData.size();
 }


 // stores and recycles views as they are scrolled off screen
 public class ViewHolder extends RecyclerView.ViewHolder {
     LinearLayout linearLayout;
     Button btn_claim,btn_first,btn_top,btn_middle,btn_bottom,btn_corners,btn_full;
     LinearLayout claim_layout;

     ViewHolder(View itemView) {
         super(itemView);
         linearLayout = itemView.findViewById(R.id.row_layout);
         btn_claim=itemView.findViewById(R.id.btn_claim);
         claim_layout=itemView.findViewById(R.id.claim_layout);
         btn_first=itemView.findViewById(R.id.btn_first);
         btn_top=itemView.findViewById(R.id.btn_top);
         btn_middle=itemView.findViewById(R.id.btn_middle);
         btn_bottom=itemView.findViewById(R.id.btn_bottom);
         btn_corners=itemView.findViewById(R.id.btn_corners);
         btn_full=itemView.findViewById(R.id.btn_full);

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
    private void claimTicket(final int ticketId, final List<Integer> claimTicket, final String claim_type) {

        new BackgroundTask(context, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.claimTicket(context, claimRequestModel(ticketId,claimTicket,claim_type));
            }

            public void taskCompleted(Object data) {
                  Log.e("response",data.toString());
                 Globals.showToast(context, data.toString());
                Gson gson = new Gson();
            }
        }, context.getString(R.string.loading_txt)).execute();
    }
    private String claimRequestModel(int ticketId,List<Integer> tiket_nos,String claim_type) {


        String user_id , game_id;
        user_id= SharedPreferenceUtil.getId(context);
        game_id=SharedPreferenceUtil.getRoom_id(context);
        ArrayList<JSONObject> claimUsers=new ArrayList();
        JSONObject cliamObj=new JSONObject();
        try {
            cliamObj.put("user_id",user_id);
            cliamObj.put("ticket_id",ticketId);
            cliamObj.put("tiket_nos",tiket_nos);
            cliamObj.put("claim_type",claim_type);
            claimUsers.add(cliamObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* {"claimusers":[{"user_id":"929",
                "ticket_id":"148",
                "tiket_nos":"[51,66,17,43,26,78,22,93,25,31,32,33,34,35]","claim_type":"top"},{"user_id":"980",

                "ticket_id":"567",
                "tiket_nos":"[51,66,17,43,26,78,22,67,29,45,3,70,5,71,84,88,18,81,44,12,19,49,75,4,60,90,2,65,21,48,40,54,62,34,28,30,15,9,32,47,58,79,35,83,7,52,13,24,10,8,56,38,20,55,82,76,69,16,46,33,74,85,89,64,39,80,1,6,59,57,36,68,61,73,14,77,50,87,42,37,63,86,53,41,27,72,11,23,25,31]","claim_type":"first"}],
            "game_id":"827"}*/

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("claimUsers", claimUsers);
            requestObject.put("game_id", game_id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }

}


