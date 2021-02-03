package com.example.attendance.student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.attendance.R;
import com.example.attendance.activity.login;
import com.example.attendance.beans.profiledatabase;
import com.example.attendance.fragment.StudentAttandant;
import com.example.attendance.fragment.detailteacher;
import com.example.attendance.fragment.notification;
import com.example.attendance.fragment.profile;
import com.example.attendance.global;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;

public class studentIndex extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private CircularImageView circularImageView;
    private TextView nameprofime,nameemail;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_index);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.scm, new notification()).commit();
            navigationView.setCheckedItem(R.id.nav_not);
        }
        Intent intent=getIntent();
        String uids=intent.getStringExtra("uid");
        ((global) getApplication()).setUid(uids);

        View header = navigationView.getHeaderView(0);
        circularImageView = header.findViewById(R.id.nameimage1);
        nameprofime=header.findViewById(R.id.nameprofime1);
        nameemail=header.findViewById(R.id.nameemail1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Profile/"+uids);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                assert profiledatabase != null;
                String fullnameindex=profiledatabase.getFullname();
                String usertype=profiledatabase.getUsertype();
                String email=profiledatabase.getEmail();
                nameprofime.setText(fullnameindex);
                nameemail.setText(email);
                String address=profiledatabase.getAddresss();
                String birthofdate=profiledatabase.getBirthofdate();
                String number=profiledatabase.getNumbers();

                String course=profiledatabase.getCourse();
                String rollnumber=profiledatabase.getRollnumber();
                String year=profiledatabase.getYear();
                String division=profiledatabase.getDivision();

                ((global) getApplication()).setDivision(division);
                ((global) getApplication()).setCourse(course);
                ((global) getApplication()).setRollnumber(rollnumber);
                ((global) getApplication()).setYear(year);

                ((global) getApplication()).setFullname(fullnameindex);
                ((global) getApplication()).setAddress(address);
                ((global) getApplication()).setBirthofdate(birthofdate);
                ((global) getApplication()).setNumber(number);
                ((global) getApplication()).setUsertype(usertype);
                ((global) getApplication()).setEmail(email);
                Log.d("student","detail"+division+course+rollnumber+year+fullnameindex+address+birthofdate+number+usertype+email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(studentIndex.this,"Error"+error,Toast.LENGTH_LONG).show();
                Log.d("index","Error"+error);
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
            StorageReference riversRef = mStorageRef.child("Profile/"+uids+".jpg");
            final File finalLocalFile = localFile;
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapdatabase2 = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                            circularImageView.setImageBitmap(bitmapdatabase2);
                            ((global) getApplication()).setLocalprofilepic(finalLocalFile.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("studentIndex","Error--"+exception);
                    Toast.makeText(studentIndex.this,"Error"+exception,Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("studentIndex","Error--"+e);
            Toast.makeText(studentIndex.this,"Error"+e,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
            else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }
            mBackPressed = System.currentTimeMillis();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Studentnoti) {
            getSupportFragmentManager().beginTransaction().replace(R.id.scm , new notification()).commit();
        } else if (id == R.id.nav_StudentAttandance) {
            getSupportFragmentManager().beginTransaction().replace(R.id.scm , new StudentAttandant()).commit();
        } else if (id == R.id.nav_studentprofile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.scm , new profile()).commit();
        } else if (id == R.id.nav_teacherdetail) {
            getSupportFragmentManager().beginTransaction().replace(R.id.scm , new detailteacher()).commit();
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(studentIndex.this, login.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
