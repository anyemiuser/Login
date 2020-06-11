package com.anyemi.housi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.housi.connection.ApiServices;
import com.anyemi.housi.connection.Constants;
import com.anyemi.housi.connection.bgtask.BackgroundTask;
import com.anyemi.housi.connection.bgtask.BackgroundThread;
import com.anyemi.housi.utils.Globals;
import com.anyemi.housi.utils.SharedPreferenceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    //  private LoginButton loginButton;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;
    private Button signout, btn_mobile,btn_guest;
    LoginButton login_button_fb;
    // private SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    int RC_SIGN_IN = 6;
    private String username = "", password = "", email = "", language = "en", fullname = "", photo_url = "", referrer = "", facebook_id = "";
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        String user_id;

        user_id = SharedPreferenceUtil.getId(getApplicationContext());
        if (!user_id.equals("")) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);

        }


        //  loginButton = findViewById(R.id.login_button);
        circleImageView = findViewById(R.id.profile_pic);
        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        signout = findViewById(R.id.gsignout);
        btn_mobile = findViewById(R.id.btn_mobile_signin);
        btn_guest = findViewById(R.id.btn_guest_play);
        login_button_fb=findViewById(R.id.login_button_fb);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        // signInButton = findViewById(R.id.sign_in_button);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("getInstanceId", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        //  String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("token", token);
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MobileLoginActivity.class);
                startActivity(i);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //  updateUI(account);
        login_button_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_button_fb.setPermissions("public_profile"); // "email",

                // Registering CallbackManager with the LoginButton
                login_button_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("Retrieving","success");
                        // Retrieving access token using the LoginResult
                        AccessToken accessToken = loginResult.getAccessToken();

                        useLoginInformation(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Retrieving","cancel");

                    }
                    @Override
                    public void onError(FacebookException error) {
                        Log.e("Retrieving","error");

                    }
                });

            }
        });


        //loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        // checkLoginStatus();
       /* loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


 @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           // handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }



    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                if(currentAccessToken == null){
                    txtName.setText("");
                    txtEmail.setText("");
                    circleImageView.setImageResource(0);
                    Toast.makeText(MainActivity.this, "User LoggedOut", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadUserProfile(currentAccessToken);
                }
        }
    };

    public void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    txtEmail.setText(email);
                    txtName.setText(first_name+""+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(MainActivity.this).load(image_url).into(circleImageView);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });
        Bundle paremeters = new Bundle();
        paremeters.putString("fields","first_name,last_name,email,id");
        request.setParameters(paremeters);
        request.executeAsync();

    }

    private void checkLoginStatus(){
        if(AccessToken.getCurrentAccessToken()!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           // updateUI(null);
                        }
                    }
                });
    }

   /* private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

   /* private void updateUI(GoogleSignInAccount account) {
        //Account is not null then user is logged in
        if (account != null) {
            signout .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut();
                }
            });
            signInButton.setVisibility(View.GONE);
            signout.setVisibility(View.VISIBLE);
            txtEmail.setText(account.getEmail());
            txtName.setText(account.getDisplayName());
            Glide.with(MainActivity.this).load(account.getPhotoUrl()).into(circleImageView);
        } else {

           // signInButton.setSize(SignInButton.SIZE_WIDE);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
            signInButton.setVisibility(View.VISIBLE);
            signout.setVisibility(View.GONE);

        }

    }*/


    }
    private void useLoginInformation(AccessToken accessToken) {

        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/

       // showpDialog();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.e("object fb",object.toString());

                try {

                    if (object.has("id")) {

                        facebook_id = object.getString("id");
                    }

                    if (object.has("name")) {

                        fullname = object.getString("name");
                    }

                    if (object.has("email")) {

                        email = object.getString("email");
                    }

                } catch (JSONException e) {

                    Log.e("Facebook Login", "Could not parse malformed JSON: \"" + object.toString() + "\"");

                } finally {

                    if (AccessToken.getCurrentAccessToken() != null) LoginManager.getInstance().logOut();

                    if (!facebook_id.equals("")) {
                        Log.e("object fb",facebook_id);
                        postMobileNumber();

                        //signinByFacebookId();

                    } else {
                        Globals.showToast(getApplicationContext(),"Invalid Login...!!");

                        //hide();
                    }
                }
            }
        });

        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        // parameters.putString("fields", "id,name,email,picture.width(200)");
        parameters.putString("fields", "id, name");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }
    private void postMobileNumber() {

        new BackgroundTask(MainActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {

                return ApiServices.login(MainActivity.this, fbLoginRequestModel(facebook_id));
            }

            public void taskCompleted(Object data) {

              //  SharedPreferenceUtil.setMobile_number(getApplicationContext(), et_mobile.getText().toString());
                Globals.showToast(getApplicationContext(), data.toString());

                SharedPreferenceUtil.setUsername(getApplicationContext(), fullname);
               SharedPreferenceUtil.setId(getApplicationContext(), facebook_id);


                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);


            /*    Intent mediaActivity = new Intent(getApplicationContext(), MediaActivity.class);
                startActivity(mediaActivity);*/


            }
        }, getString(R.string.loading_txt)).execute();
    }

    private String fbLoginRequestModel(String id) {

        String mobile_no, version_id;
      //  mobile_no = et_mobile.getText().toString();
        //   version_id = BuildConfig.VERSION_NAME;

        JSONObject requestObject = new JSONObject();
        try {
          //  requestObject.put("mobile_number", mobile_no);
            requestObject.put("device_id", token);
            requestObject.put("type","FB");
             requestObject.put("fb_id", id);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return requestObject.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}