package com.example.attendance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.beans.notificationSendAndReceive;
import com.example.attendance.messagelist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class notification extends Fragment {
    private ListView listView;
    private DatabaseReference notice;
    private List<notificationSendAndReceive> messagelist;
    private ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View listviewitem =inflater.inflate(R.layout.notification,container,false);
        listView = listviewitem.findViewById(R.id.listview);
        pb=listviewitem.findViewById(R.id.notiprogressbar);
        notice = FirebaseDatabase.getInstance().getReference("Message");
        getActivity().setTitle("Notification");
        messagelist = new ArrayList<>();
        return listviewitem;
    }
    @Override
    public void onStart() {
        super.onStart();
        pb.setVisibility(View.VISIBLE);
        notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagelist.clear();

                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    notificationSendAndReceive notificationSendAndReceive;
                    notificationSendAndReceive = message.getValue(notificationSendAndReceive.class);
                    messagelist.add(notificationSendAndReceive);
                }

                if (getActivity()!=null){
                    Collections.reverse(messagelist);
                    com.example.attendance.messagelist adapter = new messagelist(getActivity(),messagelist);
                    listView.setAdapter(adapter);
                }
                pb.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        });

    }
}
