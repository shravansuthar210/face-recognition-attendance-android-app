package com.example.attendance.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.Sms4India;

public class forgetpassword extends AppCompatActivity {
    private TextView title,phonenumber,emailtitle;
    private Button backtologin;
    private String msg,numbers,passwordc,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword2);
        phonenumber = findViewById(R.id.phonenumber);
        title = findViewById(R.id.titleaction);
        backtologin=findViewById(R.id.backtologin);
        emailtitle=findViewById(R.id.emailtitle);
        onTitleChanged("Forget Password",Color.parseColor("#ffffff"));
        ActionBar actionBar= getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0052c5"));
        actionBar.setBackgroundDrawable(colorDrawable);
        if(getIntent()==null){
            title.setText("password send to your phone");
            phonenumber.setText("");
            emailtitle.setText("");
            Toast.makeText(getApplicationContext(), "Null Phone number", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = getIntent();
            numbers = intent.getStringExtra("number");
            passwordc=intent.getStringExtra("password");
            email=intent.getStringExtra("email");
            title.setText("password send to your phone");
            String s1=numbers.substring(7,10);
            phonenumber.setText("*******"+s1);
            emailtitle.setText(email);

            msg="Your Attendance Account\nemail:"+email+"\npassword:"+passwordc;


            try{
                android.telephony.SmsManager mSmsManager = android.telephony.SmsManager.getDefault();
                mSmsManager.sendTextMessage(numbers, null, msg, null, null);
                Toast.makeText(getApplicationContext(), "Your SMS has sent successfully!", Toast.LENGTH_LONG).show();
            }
            catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Your SMS sent has failed!"+e, Toast.LENGTH_LONG).show();
            }

//        sendSMS(numbers,msg);
        }

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgetpassword.this, login.class);
                startActivity(intent);
                numbers="";
                email="";
                passwordc="";
            }
        });
    }

    private void sendSMS(String numbers, String msg) {
        Sms4India sms4India = new Sms4India(numbers,msg);
        sms4India.doInBackground();
    }
}
