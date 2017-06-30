package com.example.roombooking.roombooking.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;
import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.LandingActivity;
import com.example.roombooking.roombooking.models.ApiResponse;
import com.example.roombooking.roombooking.models.LoginDetails;
import com.example.roombooking.roombooking.rest.ApiClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    ProgressDialog progress;
    String emailId = "", password = "";
    Context context;
    private EditText email, loginPassword;
    private Button loginButton;
    private Call<ApiResponse> call;
    private ImageView passshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        registerClickListerners();
        registerTouchListner();
        context = this;

    }

    public void init() {
        email = (EditText) findViewById(R.id.email);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        passshow = (ImageView) findViewById(R.id.closeEye);
        passshow.setTag("eyeclose");

    }

    public void registerClickListerners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText())) {
                    email.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(loginPassword.getText())) {
                    loginPassword.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                    email.setText("");
                    email.setHint("Enter your Email Id");
                    email.setBackground(getResources().getDrawable(R.drawable.borderred));
                }
                if (!TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(loginPassword.getText())) {
                    Boolean emailValidate = Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();

                    if (emailValidate) {
                        emailId = email.getText().toString();
                        password = loginPassword.getText().toString();
                        loginApiCall();
                    }
                }

            }
        });

        passshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passshow.getTag().equals("eyeclose")) {
                    passshow.setImageResource(R.drawable.eye_open);
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passshow.setTag("eyeopen");
                } else {
                    passshow.setImageResource(R.drawable.eye_close);
                    loginPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passshow.setTag("eyeclose");
                }
            }
        });


    }

    public void registerTouchListner() {
        email.setOnTouchListener(this);
        loginPassword.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.setBackground(getResources().getDrawable(R.drawable.border));
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void loginApiCall() {


        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        Log.i("EMAIL", emailId);
        Log.i("PASSWORD", password);
        LoginDetails loginDetails = new LoginDetails(emailId, password);

        call = ApiClient.getInstance().loginUser(loginDetails);


        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getStatus() == 1) {
                    // progress.dismiss();
                    Intent intent = new Intent(getApplicationContext(), LandingActivity.class);


                    AppPreferences preference = new AppPreferences(context);

                    MRBSApplication.getPref().setString(preference.id, response.body().getData().getUserid());
                    MRBSApplication.getPref().setString(preference.name, response.body().getData().getName());
                    MRBSApplication.getPref().setString(preference.email, response.body().getData().getEmail());
                    MRBSApplication.getPref().setString(preference.islogin, "true");

//                    Calendar c = Calendar.getInstance();
//                    int year = c.get(Calendar.YEAR);
//                    int month = c.get(Calendar.MONTH);
//                    MRBSApplication.getPref().setString(preference.month,month+"");
//                    MRBSApplication.getPref().setString(preference.year, year+"");

                    startActivity(intent);
                    finish();
                }

                if (response.body().getStatus() == 0) {
                    progress.dismiss();
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("wrong password or username");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
                progress.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Please check your Internet Connection.");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

            }
        });

    }

}
