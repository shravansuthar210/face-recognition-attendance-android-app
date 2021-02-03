package com.example.attendance.fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.activity.CameraAttandance;
import com.example.attendance.activity.selectrollnumber;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attandance extends Fragment {
    private Spinner attancourse,attanyear,attansubject;
    private EditText division,starttime,endtime;
    private Button camera,rollnumber;
    int CAMERA_PIC_REQUEST=1;
    private List<String> FY_1BSCIT=new ArrayList<>(10);
    private List<String> FY_2BSCIT=new ArrayList<>(10);
    private List<String> SY_3BSCIT=new ArrayList<>(10);
    private List<String> SY_4BSCIT=new ArrayList<>(10);
    private List<String> TY_5BSCIT=new ArrayList<>(10);
    private List<String> TY_6BSCIT=new ArrayList<>(10);

    private int sub;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.attandance,container,false);
        attancourse=layout.findViewById(R.id.spinner);
        attanyear=layout.findViewById(R.id.spinner2);
        attansubject=layout.findViewById(R.id.spinner3);

        division=layout.findViewById(R.id.divisionattndanc);
        starttime=layout.findViewById(R.id.starttime);
        endtime=layout.findViewById(R.id.endtime);

        camera=layout.findViewById(R.id.camera);
        rollnumber=layout.findViewById(R.id.rollnumber);
        getActivity().setTitle("Attandance");

        FY_1BSCIT.add("Communication Skill");
        FY_1BSCIT.add("Operating Systems");
        FY_1BSCIT.add("Digital Electronics");
        FY_1BSCIT.add("Imperative Programming");
        FY_1BSCIT.add("Discrete Mathematics");
        FY_1BSCIT.add("Imperative Programming Practical");
        FY_1BSCIT.add("Digital Electronics Practical");
        FY_1BSCIT.add("Operating Systems Practical");
        FY_1BSCIT.add("Discrete Mathematics Practical");
        FY_1BSCIT.add("Communication Skills Practical");

        FY_2BSCIT.add("Object oriented Programming");
        FY_2BSCIT.add("Microprocessor Architecture");
        FY_2BSCIT.add("Web Programming");
        FY_2BSCIT.add("Numerical and Statistical Methods");
        FY_2BSCIT.add("Green Computing");
        FY_2BSCIT.add("Object Oriented Programming Practical");
        FY_2BSCIT.add("Microprocessor Architecture Practical");
        FY_2BSCIT.add("Web Programming Practical");
        FY_2BSCIT.add("Numerical & Statistical Methods Practical");
        FY_2BSCIT.add("Green Computing Practical");

        SY_3BSCIT.add("Python Programming");
        SY_3BSCIT.add("Data Structures");
        SY_3BSCIT.add("Computer Networks");
        SY_3BSCIT.add("Database Management Systems");
        SY_3BSCIT.add("Applied Mathematics");
        SY_3BSCIT.add("Python Programming Practical");
        SY_3BSCIT.add("Data Structures Practical");
        SY_3BSCIT.add("Computer Networks Practical");
        SY_3BSCIT.add("Database Management Systems Practical");
        SY_3BSCIT.add("Mobile Programming Practical");

        SY_4BSCIT.add("Core Java");
        SY_4BSCIT.add("Introduction to Embedded Systems");
        SY_4BSCIT.add("Computer Oriented Statistical Techniques");
        SY_4BSCIT.add("Software Engineering");
        SY_4BSCIT.add("Computer Graphics and Animation");
        SY_4BSCIT.add("Core Java Practical");
        SY_4BSCIT.add("Introduction to ES Practical");
        SY_4BSCIT.add("COST Practical");
        SY_4BSCIT.add("Software Engineering Practical");
        SY_4BSCIT.add("Computer Graphics and Animation Practical");

        TY_5BSCIT.add("Software Project Management");
        TY_5BSCIT.add("Internet of Things");
        TY_5BSCIT.add("Advanced Web Programming");
        TY_5BSCIT.add("AI/Linux Admin.");
        TY_5BSCIT.add("E Java/NGT");
        TY_5BSCIT.add("Project Dissertation");
        TY_5BSCIT.add("Internet of Things Practical");
        TY_5BSCIT.add("Advanced Web Programming Practical");
        TY_5BSCIT.add("AI/Linux Admin. Practical");
        TY_5BSCIT.add("E Java/NGT Practical");

        TY_6BSCIT.add("Software Quality Assurance");
        TY_6BSCIT.add("Security in Computing");
        TY_6BSCIT.add("Business Intelligence");
        TY_6BSCIT.add("Principles of GIS/EN");
        TY_6BSCIT.add("IT Service Management/Cyber Laws");
        TY_6BSCIT.add("Project Implementation");
        TY_6BSCIT.add("Security in Computing Practical");
        TY_6BSCIT.add("Business Intelligence Practical");
        TY_6BSCIT.add("Principles of GIS/EN Practical");
        TY_6BSCIT.add("Advanced Mobile Programming");

        starttime.setInputType(InputType.TYPE_NULL);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                TimePickerDialog picker1;
                picker1 = new TimePickerDialog(getContext(),R.style.MyTimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        starttime.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
                picker1.getWindow().setBackgroundDrawableResource(R.color.grey);
                picker1.show();
            }
        });

        endtime.setInputType(InputType.TYPE_NULL);
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                TimePickerDialog picker1;
                picker1 = new TimePickerDialog(getContext(),R.style.MyTimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        endtime.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
                picker1.getWindow().setBackgroundDrawableResource(R.color.grey);
                picker1.show();
            }
        });


        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        attancourse.setAdapter(dataAdapter);
        attancourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });

        List<String> categories1 = new ArrayList<String>();
        categories1.add("FY_1");
        categories1.add("FY_2");
        categories1.add("SY_3");
        categories1.add("SY_4");
        categories1.add("TY_5");
        categories1.add("TY_6");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        attanyear.setAdapter(dataAdapter1);

        attanyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sub == 0 && position == 0){
                    ArrayAdapter<String> dataAdapter2=new ArrayAdapter<>(getContext(), R.layout.spinner_item, FY_1BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }else if(sub == 0 && position == 1){
                    ArrayAdapter<String> dataAdapter2=new ArrayAdapter<>(getContext(), R.layout.spinner_item, FY_2BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }else if(sub == 0 && position == 2) {
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item, SY_3BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }else if(sub == 0 && position == 3) {
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item, SY_4BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }else if(sub == 0 && position == 4) {
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item, TY_5BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }else if(sub == 0 && position == 5) {
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item, TY_6BSCIT);
                    dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_item);
                    attansubject.setAdapter(dataAdapter2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Error"+parent,Toast.LENGTH_LONG).show();
            }
        });
//        List<String> categories2=new ArrayList<>();
//        categories2.add("Communication skill");
//        categories2.add("Operating");
//        ArrayAdapter<String> dataAdapter2=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, categories2);
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        attansubject.setAdapter(dataAdapter2);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attancourses=attancourse.getSelectedItem().toString().trim();
                String attanyears=attanyear.getSelectedItem().toString().trim();
                String attansubjects=attansubject.getSelectedItem().toString().trim();
                String divisions=division.getText().toString().trim();
                String starttimes=starttime.getText().toString().trim();
                String endtimes=endtime.getText().toString().trim();
                if(divisions.isEmpty()){
                    division.setError("Please enter division");
                    division.requestFocus();
                }else if(starttimes.isEmpty()){
                    starttime.setError("Please enter division");
                    starttime.requestFocus();
                }else if(endtimes.isEmpty()){
                    endtime.setError("Please enter division");
                    endtime.requestFocus();
                }else if(starttimes.length()<4){
                    starttime.setError("PLease eter valid formate");
                    starttime.requestFocus();
                }else if(endtimes.length()<4){
                    starttime.setError("Please enter valid formate");
                    starttime.requestFocus();
                }else{
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                    Intent intent=new Intent(getContext(), CameraAttandance.class);
                    intent.putExtra("course",attancourses);
                    intent.putExtra("year",attanyears);
                    intent.putExtra("division",divisions);
                    intent.putExtra("subject",attansubjects);
                    intent.putExtra("starttime",starttimes);
                    intent.putExtra("endtime",endtimes);
                    startActivity(intent);
                }
            }
        });
        rollnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attancourses=attancourse.getSelectedItem().toString().trim();
                String attanyears=attanyear.getSelectedItem().toString().trim();
                String attansubjects=attansubject.getSelectedItem().toString().trim();
                String divisions=division.getText().toString().trim();
                String starttimes=starttime.getText().toString().trim();
                String endtimes=endtime.getText().toString().trim();
                if(divisions.isEmpty()){
                    division.setError("Please enter division");
                    division.requestFocus();
                }else if(starttimes.isEmpty()){
                    starttime.setError("Please enter division");
                    starttime.requestFocus();
                }else if(endtimes.isEmpty()){
                    endtime.setError("Please enter division");
                    endtime.requestFocus();
                }else if(starttimes.length()<4){
                    starttime.setError("PLease enter valid formate");
                    starttime.requestFocus();
                }else if(endtimes.length()<4){
                    starttime.setError("Please enter valid formate");
                    starttime.requestFocus();
                }else{
                    Intent intent=new Intent(getContext(), selectrollnumber.class);
                    intent.putExtra("course",attancourses);
                    intent.putExtra("year",attanyears);
                    intent.putExtra("division",divisions);
                    intent.putExtra("subject",attansubjects);
                    intent.putExtra("starttime",starttimes);
                    intent.putExtra("endtime",endtimes);
                    startActivity(intent);
                }
            }
        });
        return layout;
    }
}
