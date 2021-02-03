package com.example.attendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.beans.profiledatabase;
import com.example.attendance.index;
import com.example.attendance.student.studentIndex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static int splash_time_out=100;
    private ProgressBar loader;
    private FirebaseAuth mAuth;
    private DatabaseReference notice3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        loader.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loader.setVisibility(View.VISIBLE);
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if( currentUser != null ){
                    final String uid = currentUser.getUid();
                    Toast.makeText(MainActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    notice3 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);
                    notice3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                            String usertypelogin=profiledatabase.getUsertype();
                            Toast.makeText(MainActivity.this,"Successfull login ",Toast.LENGTH_SHORT).show();
                            if(usertypelogin.equals("Teacher")){
                                loader.setVisibility(View.GONE);
                                Intent i = new Intent(MainActivity.this, index.class);
                                i.putExtra("uid",uid);
                                startActivity(i);
                            }else if(usertypelogin.equals("Student")){
                                loader.setVisibility(View.GONE);
                                Intent i = new Intent(MainActivity.this, studentIndex.class);
                                i.putExtra("uid",uid);
                                startActivity(i);
                            }else {
                                loader.setVisibility(View.GONE);
                                Log.d("login","Error");
                                Toast.makeText(MainActivity.this,"Error not usertype",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loader.setVisibility(View.GONE);
                            Log.d("login","Error--"+error);
                            Toast.makeText(MainActivity.this,"Error"+error,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else{
                    loader.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_time_out);
    }
}
