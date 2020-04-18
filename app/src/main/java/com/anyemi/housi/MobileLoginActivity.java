package com.anyemi.housi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileLoginActivity extends AppCompatActivity {
    private TextView tv_Resendotp;
    private EditText  et_mobile,et_otp_no;
    private Button  btn_Next,btn_verify_otp;
   // EditText  et_mobile2 = new EditText(CreateRoomActivity.this);

   // Button  btn_Next2 = new Button(CreateRoomActivity.this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_mobile_login);
       // String reg_code = SharedPreferenceUtil.getRegCode(getApplicationContext());


        et_mobile = findViewById(R.id.et_mobile_no);
        et_otp_no = findViewById(R.id.et_otp);
        tv_Resendotp = findViewById(R.id.tv_resend_otp);
        btn_Next = findViewById(R.id.btn_next);
        btn_verify_otp = findViewById(R.id.btn_verify);
        et_otp_no.setVisibility(View.INVISIBLE);
        btn_verify_otp.setVisibility(View.INVISIBLE);
        tv_Resendotp.setVisibility(View.INVISIBLE);



      /*  btn_Next.setOnClickListener(new View.OnClickListener() {*/
        btn_Next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isvalidphonenumber()) {
                    et_mobile.setVisibility(View.INVISIBLE);
                    btn_Next.setVisibility(View.INVISIBLE);
                    et_otp_no.setVisibility(View.VISIBLE);
                    btn_verify_otp.setVisibility(View.VISIBLE);
                    tv_Resendotp.setVisibility(View.VISIBLE);

                    postMobileNumber();

                }


            }
        });

        tv_Resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postResendOtp();
            }
        });
        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(isvalidotpnumber()) {
                  postVerifyOtpNumber();

            /*Intent i=new Intent( getApplicationContext(), HomeActivity.class);
            startActivity(i);*/
              }               }
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
    private void postVerifyOtpNumber() {
        new BackgroundTask(MobileLoginActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.verifyotp(MobileLoginActivity.this, otpverifyRequestModel());
            }

            public void taskCompleted(Object data) {


                VerifyOtp verifyOtp =new VerifyOtp();

                verifyOtp= new Gson().fromJson(data.toString(),VerifyOtp.class);

                SharedPreferenceUtil.setMobile_number(getApplicationContext(), et_mobile.getText().toString());
                SharedPreferenceUtil.setId(getApplicationContext(), verifyOtp.getId());
                Globals.showToast(getApplicationContext(), data.toString());


              Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);


            }
        }, getString(R.string.loading_txt)).execute();
    }

    //uma
    private String otpverifyRequestModel() {

        String otp_no,mobile_no, version_id;
        mobile_no = et_mobile.getText().toString();
        otp_no = et_otp_no.getText().toString();


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

    private void postResendOtp() {
        new BackgroundTask(MobileLoginActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return ApiServices.resendotp(MobileLoginActivity.this, resendotpRequestModel());
            }

            public void taskCompleted(Object data) {

                SharedPreferenceUtil.setMobile_number(getApplicationContext(), et_mobile.getText().toString());
                Globals.showToast(getApplicationContext(), data.toString());


            /*    Intent mediaActivity = new Intent(getApplicationContext(), MediaActivity.class);
                startActivity(mediaActivity);*/


            }
        }, getString(R.string.loading_txt)).execute();
    }
    private String resendotpRequestModel() {

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
    private boolean isvalidphonenumber() {

        boolean isValid = false;
        if (et_mobile.getText().toString().equals("")) {
            et_mobile.setError("Please enter phone number");
            et_mobile.requestFocus();}

      /* else if(Double.parseDouble(et_mobile.getText().toString())!=10){
            et_mobile.setError("please enter valid Phone Number");
            et_mobile.requestFocus();}*/


         else {


        isValid = true;
    }

        return isValid;

        }
    private boolean isvalidotpnumber() {

        boolean isValid = false;
        if (et_otp_no.getText().toString().equals("")) {
            et_otp_no.setError("Please enter OTP ");
            et_otp_no.requestFocus();}



        else {


            isValid = true;
        }

        return isValid;

    }



}








