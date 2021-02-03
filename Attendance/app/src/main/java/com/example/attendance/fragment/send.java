package com.example.attendance.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.beans.notificationSendAndReceive;
import com.example.attendance.global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class send extends Fragment {
    private EditText subject,message;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private String sendername;
    private ProgressBar pb1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutview=inflater.inflate(R.layout.sendermessage,container,false);
        final Button sendbutton = layoutview.findViewById(R.id.send);
        subject=layoutview.findViewById(R.id.subject);
        message=layoutview.findViewById(R.id.message);
        pb1=layoutview.findViewById(R.id.sendprogressbar);
        pb1.setVisibility(View.GONE);
        sendername=((global) Objects.requireNonNull(getActivity()).getApplication()).getFullname();
        Log.d("send","sederid"+sendername);
        getActivity().setTitle("Send");
        sendbutton.setEnabled(true);
        subject.setEnabled(true);
        message.setEnabled(true);

        notice = FirebaseDatabase.getInstance().getReference("Message");

        final Spinner spinner = layoutview.findViewById(R.id.studentcourse);
        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spinner.setAdapter(dataAdapter);

        final Spinner spinner2 = layoutview.findViewById(R.id.studentyear);
        List<String> categories2 = new ArrayList<String>();
        categories2.add("FY");
        categories2.add("SY");
        categories2.add("TY");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
        spinner2.setAdapter(dataAdapter2);

        
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbutton.setEnabled(false);
                subject.setEnabled(false);
                message.setEnabled(false);
                pb1.setVisibility(View.VISIBLE);
                String courese = spinner.getSelectedItem().toString();
                String year = spinner2.getSelectedItem().toString();
                String subjec =  subject.getText().toString().trim();
                String messag = message.getText().toString().trim();
                String id= notice.push().getKey();
                if(subjec.isEmpty()){
                    subject.setError("Please enter Subject");
                    subject.requestFocus();
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }else if(messag.isEmpty()){
                    message.setError("Please enter Message");
                    message.requestFocus();
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }else if(sendername.isEmpty()){
                    sendername="Default";
                }else {
                    @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy ");
                    String currentDateandTime = sdf.format(new Date()).trim();
                    notificationSendAndReceive notificationSendAndReceive = new notificationSendAndReceive(subjec,messag,sendername,currentDateandTime,courese+year);
                    notice.child(id).setValue(notificationSendAndReceive);
                    Toast.makeText(getContext(),"Succcessful", Toast.LENGTH_SHORT).show();
                    subject.setText(" ");
                    message.setText(" ");
                    sendbutton.setEnabled(true);
                    subject.setEnabled(true);
                    message.setEnabled(true);
                    pb1.setVisibility(View.GONE);
                }
            }
        });
        return layoutview;
    }
}
