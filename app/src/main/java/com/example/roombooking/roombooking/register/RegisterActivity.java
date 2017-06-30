package com.example.roombooking.roombooking.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.LandingActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnTouchListener {
    private EditText name,email,loginPassword,mobileNumber,confirmPassword;
    private Button registerButton;
    ImageView backImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerClickListerners();
        registerTouchListner();
    }

    public void init(){
        email=(EditText) findViewById(R.id.email);
        name=(EditText) findViewById(R.id.name);
        loginPassword=(EditText) findViewById(R.id.loginPassword);
        mobileNumber=(EditText) findViewById(R.id.mobileNumber);
        confirmPassword=(EditText) findViewById(R.id.confirmPassword);
        registerButton=(Button) findViewById(R.id.registerButton);
        backImage = (ImageView) findViewById(R.id.backImage);

    }


    public void registerClickListerners(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText())) {
                    email.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(loginPassword.getText())) {
                    loginPassword.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(name.getText())) {
                    name.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(mobileNumber.getText())) {
                    mobileNumber.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(confirmPassword.getText())) {
                    confirmPassword.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                 if(!Patterns.PHONE.matcher(mobileNumber.getText()).matches()||mobileNumber.getText().length()!=10){
                     mobileNumber.setText("");
                     mobileNumber.setHint("Enter your Mobile Number");
                     mobileNumber.setBackground(getResources().getDrawable(R.drawable.borderred));
                 }

                if(!loginPassword.getText().toString().equals(confirmPassword.getText().toString())){
                    loginPassword.setText("");
                    confirmPassword.setText("");
                    loginPassword.setBackground(getResources().getDrawable(R.drawable.borderred));
                    confirmPassword.setBackground(getResources().getDrawable(R.drawable.borderred));

                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
                    email.setText("");
                    email.setHint("Enter your Email Id");
                    email.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(loginPassword.getText())&& !TextUtils.isEmpty(mobileNumber.getText())&& !TextUtils.isEmpty(confirmPassword.getText()) ) {
                    Boolean emailValidate = Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();
                    Boolean phoneValidate = Patterns.PHONE.matcher(mobileNumber.getText()).matches();
                    int mobileNolength=mobileNumber.getText().length();
                    Boolean passwordMatch=loginPassword.getText().toString().equals(confirmPassword.getText().toString());

                    if (emailValidate && phoneValidate && passwordMatch && mobileNolength==10) {
                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                        startActivity(intent);
                        finish();
                        //  loginApiCall();
                    }
                }

            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void registerTouchListner(){
        email.setOnTouchListener(this);
        loginPassword.setOnTouchListener(this);
        name.setOnTouchListener(this);
        mobileNumber.setOnTouchListener(this);
        confirmPassword.setOnTouchListener(this);


    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.setBackground(getResources().getDrawable(R.drawable.border));
        return false;
    }
}
