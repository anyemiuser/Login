package com.anyemi.housi.connection;

import android.content.Context;


/**
 * Created by SuryaTejaChalla on 19-08-2017.
 */

public class ApiServices {

  /*  public static Object get(Context aContext) {
        try {
            return Connection.callHttpGetRequestsV2(aContext, Constants.REGISTER, null);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }


   */




    public static Object login(Context aContext, String loginRequest) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.LOGIN, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
    public static Object resendotp(Context aContext, String loginRequest) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.RESEND_OTP, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    public static Object verifyotp(Context aContext, String loginRequest) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.VERIFY_OTP, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    public static Object createroom(Context aContext, String loginRequest) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.CREATE_ROOM, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
    public static Object joinroom(Context aContext, String loginRequest) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.JOIN_ROOM, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }


 /*


    public static Object postPayment(Context aContext, PaymentRequestModel request) {
        try {
            return Connection.callHttpPostRequestsV2(aContext, Constants.POST_PAYMENT_DETAILS, request);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }


     */
}
