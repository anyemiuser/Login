package com.anyemi.housi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileLoginActivity extends AppCompatActivity {
    private EditText  et_mobile,et_otp;
    private Button  btn_Next,btn_verify;
   // EditText  et_mobile2 = new EditText(CreateRoomActivity.this);

   // Button  btn_Next2 = new Button(CreateRoomActivity.this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
       // String reg_code = SharedPreferenceUtil.getRegCode(getApplicationContext());


        et_mobile = findViewById(R.id.et_mobile_no);
        et_otp = findViewById(R.id.et_otp);
        btn_Next = findViewById(R.id.btn_next);
        btn_verify = findViewById(R.id.btn_verify);
        et_otp.setVisibility(View.INVISIBLE);
        btn_verify.setVisibility(View.INVISIBLE);


      /*  btn_Next.setOnClickListener(new View.OnClickListener() {*/
        btn_Next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            /*Intent i=new Intent( getApplicationContext(),CreateRoomActivity.class);
            startActivity(i);*/
                et_mobile.setVisibility(View.INVISIBLE);
                btn_Next.setVisibility(View.INVISIBLE);
                et_otp.setVisibility(View.VISIBLE);
                btn_verify.setVisibility(View.VISIBLE);
                postMobileNumber();

            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*Intent i=new Intent( getApplicationContext(), MobileLoginActivity.class);
            startActivity(i);*/
                postOtpNumber();
               }
        });


    }


    private void postMobileNumber() {
        new BackgroundTask(MobileLoginActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.login(MobileLoginActivity.this, mobileloginRequestModel());
            }

            public void taskCompleted(Object data) {

                SharedPreferenceUtil.setMobile_number(getApplicationContext(), et_mobile.getText().toString());
                Globals.showToast(getApplicationContext(), data.toString());


            /*    Intent mediaActivity = new Intent(getApplicationContext(), MediaActivity.class);
                startActivity(mediaActivity);*/


            }
        }, getString(R.string.loading_txt)).execute();
    }
    private String mobileloginRequestModel() {

        String mobile_no, version_id;
        mobile_no = et_mobile.getText().toString();
     //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("mobile_number", mobile_no);
           /// requestObject.put("AppVersion", version_id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }
    private void postOtpNumber() {
        new BackgroundTask(MobileLoginActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.verifyotp(MobileLoginActivity.this, otpverifyRequestModel());
            }

            public void taskCompleted(Object data) {

                SharedPreferenceUtil.setMobile_number(getApplicationContext(), et_mobile.getText().toString());
                Globals.showToast(getApplicationContext(), data.toString());


            /*    Intent mediaActivity = new Intent(getApplicationContext(), MediaActivity.class);
                startActivity(mediaActivity);*/


            }
        }, getString(R.string.loading_txt)).execute();
    }

    //uma
    private String otpverifyRequestModel() {

        String otp_no,mobile_no, version_id;
        mobile_no = et_mobile.getText().toString();
        otp_no = et_otp.getText().toString();


        //version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("mobile_number", mobile_no);
            requestObject.put("otp", otp_no);
            //requestObject.put("AppVersion", version_id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }



}








