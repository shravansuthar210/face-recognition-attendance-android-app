package com.example.attendance.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class attandacereport extends Fragment{
    private Spinner searchcource,seachyear;
    private EditText searchroll,searchdiv;
    private Button search;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private List<String> FY_1BSCIT=new ArrayList<String>(10);
    private List<String> FY_2BSCIT=new ArrayList<String>(10);
    private List<String> SY_3BSCIT=new ArrayList<String>(10);
    private List<String> SY_4BSCIT=new ArrayList<String>(10);
    private List<String> TY_5BSCIT=new ArrayList<String>(10);
    private List<String> TY_6BSCIT=new ArrayList<String>(10);

    private List<Integer> minmum=new ArrayList<>();
    private List<Integer> manmum=new ArrayList<>();
    private int sub,main;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private int num=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout=inflater.inflate(R.layout.attandancereport,container,false);
        getActivity().setTitle("Attandance Report");
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


        searchcource=layout.findViewById(R.id.searchspinner);
        seachyear=layout.findViewById(R.id.searchspinner2);
        searchroll=layout.findViewById(R.id.searchroll);
        searchdiv=layout.findViewById(R.id.searchdivision);
        search=layout.findViewById(R.id.search);


        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        searchcource.setAdapter(dataAdapter);

        final List<String> categories1 = new ArrayList<String>();
        categories1.add("FY_1");
        categories1.add("FY_2");
        categories1.add("SY_3");
        categories1.add("SY_4");
        categories1.add("TY_5");
        categories1.add("TY_6");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        seachyear.setAdapter(dataAdapter1);

        searchcource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                main = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        seachyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Error--"+parent,Toast.LENGTH_LONG).show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                final String searchroll1=searchroll.getText().toString().trim();
                final String searchdiv1=searchdiv.getText().toString().trim();
                final String searchcource1=searchcource.getSelectedItem().toString().trim();
                final String searchyear1=seachyear.getSelectedItem().toString().trim();
                if(searchroll1.isEmpty()){
                    searchroll.setError("please enter Roll number");
                    searchroll.requestFocus();
                }else if(searchdiv1.isEmpty()){
                    searchdiv.setError("please enter Roll number");
                    searchdiv.requestFocus();
                }else{
                    tableLayout.removeAllViews();
                    if(main==0&&sub==0){
                        for(int i=0;i<=FY_1BSCIT.size()-1;i++){
                            Log.d("report","Report"+FY_1BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,FY_1BSCIT.get(i));
                        }
                    }else if(main==0&&sub==1){
                        for(int i=0;i<=FY_2BSCIT.size()-1;i++){
                            Log.d("report","Report"+FY_2BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,FY_2BSCIT.get(i));
                        }
                    }else if(main==0&&sub==2){
                        for(int i=0;i<=SY_3BSCIT.size()-1;i++){
                            Log.d("report","Report"+SY_3BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,SY_3BSCIT.get(i));
                        }
                    }else if(main==0&&sub==3){
                        for(int i=0;i<=SY_4BSCIT.size()-1;i++){
                            Log.d("report","Report"+SY_4BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,SY_4BSCIT.get(i));
                        }
                    }else if(main==0&&sub==4){
                        for(int i=0;i<=TY_5BSCIT.size()-1;i++){
                            Log.d("report","Report"+TY_5BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,TY_5BSCIT.get(i));
                        }
                    }else if(main==0&&sub==5){
                        for(int i=0;i<=TY_6BSCIT.size()-1;i++){
                            Log.d("report","Report"+TY_6BSCIT.get(i));
                            attandance(searchcource1,searchyear1,searchdiv1,searchroll1,TY_6BSCIT.get(i));
                        }
                    }
                }
            }
        });
        tableLayout=layout.findViewById(R.id.list);
        return layout;
    }
    private void attandance(final String searchcource1, final String searchyear1, final String searchdiv1, String searchroll1, final String dataclass){
        notice = FirebaseDatabase.getInstance().getReference("Attandance/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+searchroll1+"/"+dataclass);
        notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    final long child=dataSnapshot.getChildrenCount();
                    Log.d("report","mainchild----"+child);
                    final int minchild=(int)child;
                    notice1 = FirebaseDatabase.getInstance().getReference("Attandancedetail/"+searchcource1+"/"+searchyear1+"/"+searchdiv1+"/"+dataclass);
                    notice1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                num=num+1;
                                long tchild=dataSnapshot.getChildrenCount();
                                Log.d("report","subchild----"+tchild);
                                Log.d("att","num"+String.valueOf(num));
                                int maxchild=(int)tchild;
                                progress(dataclass,minchild,maxchild);
                                report(num);
                            }  else {
                                num=num+1;
                                Log.d("att","num"+String.valueOf(num));
                                Log.d("report","Teacher Not child");
                                progress(dataclass,minchild,minchild);
                                report(num);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("report","Error--"+error);
                            Toast.makeText(getContext(),"Error--"+error,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    num=num+1;
                    Log.d("att","num"+String.valueOf(num));
                    int i=0,j=0;
                    progress(dataclass,i,j);
                    Log.d("report","student Not child");
                    report(num);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("report","Error--"+error);
                Toast.makeText(getContext(),"Error--"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void progress(String i, int min, int max){
        minmum.add(min);
        manmum.add(max);
        tableRow = new TableRow(getContext());
        TextView tv=new TextView(getContext());
        tv.setText(i);
        tv.setTextSize(20);
        tv.setPadding(5,0,0,0);
        tv.setWidth(900);
        tv.setTextColor(Color.BLACK);
        TextView tv1=new TextView(getContext());
        int percentage;
        if(max==0){
            percentage=0;
        }else {
            percentage =(min*100)/max;
        }
        tv1.setTextColor(Color.GRAY);
        if(percentage>100){
            percentage=100;
        }
        tv1.setText(min+"/"+max+"\t\t"+percentage+"%");
        tv1.setTextSize(9);
        tv1.setPadding(0,0,0,0);
        tableRow.addView(tv);
        tableRow.addView(tv1);
        ProgressBar pb=new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
        pb.setMin(0);
        pb.setMax(max);
        pb.setProgress(min);
        pb.setPadding(15,0,15,5);
        TextView qw=new TextView(getContext());
        qw.setBackgroundColor(Color.BLACK);
        qw.setHeight(5);
        tableLayout.addView(tableRow);
        tableLayout.addView(pb);
        tableLayout.addView(qw);
    }
    @SuppressLint("SetTextI18n")
    private void report(int qw){
        if(qw==10){
            int newmin=0;
            int newmax=0;
            num=0;
            for(int v=0;v<minmum.size();v++){
                newmax=newmax+manmum.get(v);
                newmin=newmin+minmum.get(v);
            }
            int newpercentage;
            if(newmax==0){
                newpercentage=0;
            }else {
                newpercentage =(newmin*100)/newmax;
            }
            Log.d("atttt","per"+newpercentage);
            TableRow tableRow1 = new TableRow(getContext());
            tableRow1.setBackgroundColor(Color.parseColor("#1134af"));
            TextView tv3=new TextView(getContext());
            tv3.setText("Total Attandance");
            tv3.setTextSize(20);
            tv3.setPadding(35,10,0,15);
            tv3.setWidth(900);
            tv3.setTextColor(Color.WHITE);
            TextView tv2=new TextView(getContext());
            tv2.setTextColor(Color.WHITE);
            if(newpercentage>100){
                newpercentage=100;
            }
            tv2.setText(newmin+"/"+newmax+"\t"+newpercentage+"%");
            tv2.setTextSize(12);
            tv2.setPadding(0,10,0,15);
            tableRow1.addView(tv3);
            tableRow1.addView(tv2);
            tableLayout.addView(tableRow1);
            newmax=0;
            newmin=0;
            newpercentage=0;
            manmum.clear();
            minmum.clear();
        }
    }
}
