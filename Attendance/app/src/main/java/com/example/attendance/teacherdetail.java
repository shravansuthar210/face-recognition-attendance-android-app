package com.example.attendance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.attendance.beans.addTeacherdatbase;

import java.util.List;

public class teacherdetail extends ArrayAdapter<addTeacherdatbase> {
    private TextView teachername,teachercource,teacherbirthdate,teacheremail,teacheraddress;
    private Activity context;
    private List<addTeacherdatbase> teacherinfo;
    public teacherdetail(Context context, List<addTeacherdatbase> teacherinfo) {
        super(context,R.layout.teacherlist,teacherinfo);
        this.context= (Activity) context;
        this.teacherinfo=teacherinfo;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.teacherlist,null,true);
        teachername=listViewItem.findViewById(R.id.teachername);
        teachercource=listViewItem.findViewById(R.id.teachercource);
        teacherbirthdate=listViewItem.findViewById(R.id.degree);
        teacheremail=listViewItem.findViewById(R.id.teacheremail);
        teacheraddress=listViewItem.findViewById(R.id.teacheraddress);
        addTeacherdatbase addTeacherdatbase=teacherinfo.get(position);
        teachername.setText(addTeacherdatbase.getFullname());
        teachercource.setText(addTeacherdatbase.getCourese());
        teacherbirthdate.setText(addTeacherdatbase.getDegree());
        teacheremail.setText(addTeacherdatbase.getEmail());
        teacheraddress.setText(addTeacherdatbase.getAddresss());
        return listViewItem;
    }
}
