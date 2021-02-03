package com.example.attendance.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.beans.profiledatabase;
import com.example.attendance.global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

import static android.view.View.GONE;

public class profile extends Fragment {
    private TextView full_names,showcources,Birthofdates,Contacts,mails,addresss,uids,div,rollnumber,usertype,rollt,divt,showdegreet,showdegree;
    private ImageButton profile;
    private ProgressBar ppb;
    private StorageReference mStorageRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.profile,container,false);
        full_names=layout.findViewById(R.id.full_name);
        showcources=layout.findViewById(R.id.showcource);
        Birthofdates=layout.findViewById(R.id.Birthofdate);
        Contacts=layout.findViewById(R.id.Contact);
        mails=layout.findViewById(R.id.mail);
        addresss=layout.findViewById(R.id.address);
        uids=layout.findViewById(R.id.uid);
        profile=layout.findViewById(R.id.profileimage);
        ppb=layout.findViewById(R.id.profilepb);
        div=layout.findViewById(R.id.div);
        rollnumber=layout.findViewById(R.id.rollnumber);
        usertype=layout.findViewById(R.id.usertype);
        divt=layout.findViewById(R.id.divt);
        rollt=layout.findViewById(R.id.rollt);
        showdegree=layout.findViewById(R.id.showdegree);
        showdegreet=layout.findViewById(R.id.showdegreet);
        showdegree.setVisibility(GONE);
        showdegreet.setVisibility(GONE);
        getActivity().setTitle("Profile");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        return layout;
    }
    @Override
    public void onStart() {
        super.onStart();
        ppb.setVisibility(View.VISIBLE);
        String loginid=((global) Objects.requireNonNull(getActivity()).getApplication()).getUid();
        DatabaseReference notice;
        notice = FirebaseDatabase.getInstance().getReference("Profile/"+loginid);
        notice.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiledatabase addTeacherdatbase= dataSnapshot.getValue(profiledatabase.class);
                assert addTeacherdatbase != null;
                full_names.setText(addTeacherdatbase.getFullname());
                usertype.setText("Usertype:"+addTeacherdatbase.getUsertype());
                if(addTeacherdatbase.getCourece()!=null){
                    showcources.setText(addTeacherdatbase.getCourece());
                }
                if(addTeacherdatbase.getCourse()!=null){
                    showcources.setText(addTeacherdatbase.getCourse());
                }
                if(addTeacherdatbase.getDegree()!=null){
                    showdegree.setVisibility(View.VISIBLE);
                    showdegree.setText(addTeacherdatbase.getDegree());
                    showdegreet.setVisibility(View.VISIBLE);
                }
                Birthofdates.setText(addTeacherdatbase.getBirthofdate());
                Contacts.setText(addTeacherdatbase.getNumbers());
                mails.setText(addTeacherdatbase.getEmail());
                addresss.setText(addTeacherdatbase.getAddresss());
                uids.setText(addTeacherdatbase.getUid());
                if(addTeacherdatbase.getDivision()==null){
                    div.setVisibility(GONE);
                    divt.setVisibility(GONE);
                }
                div.setText(addTeacherdatbase.getDivision());
                if(addTeacherdatbase.getRollnumber()==null){
                    rollnumber.setVisibility(GONE);
                    rollt.setVisibility(GONE);
                }
                rollnumber.setText(addTeacherdatbase.getRollnumber());
                ppb.setVisibility(GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"error"+error,Toast.LENGTH_LONG).show();
                ppb.setVisibility(GONE);
            }
        });
        String pp=((global) Objects.requireNonNull(getActivity()).getApplication()).getLocalprofilepic();
        if(pp!=null){
            File newfile=new File(pp);
            Bitmap bitmapdatabase2 = BitmapFactory.decodeFile(newfile.getAbsolutePath());
            profile.setImageBitmap(bitmapdatabase2);
        }
    }
}
