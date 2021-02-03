package com.example.attendance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.attendance.beans.notificationSendAndReceive;

import java.util.List;

public class messagelist extends ArrayAdapter<notificationSendAndReceive> {
    private Activity context;
    private List<notificationSendAndReceive> messagelist;

    public messagelist(Activity context, List<notificationSendAndReceive> messagelist){
        super(context,R.layout.recevermessagelist,messagelist);
        this.context=context;
        this.messagelist=messagelist;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View listviewitem=inflater.inflate(R.layout.recevermessagelist,null,true);
        TextView sender=listviewitem.findViewById(R.id.by);
        TextView time=listviewitem.findViewById(R.id.time);
        TextView subject=listviewitem.findViewById(R.id.subject);
        TextView message=listviewitem.findViewById(R.id.messae);
        notificationSendAndReceive notificationSendAndReceive = messagelist.get(position);

        sender.setText(notificationSendAndReceive.getSender());
        time.setText(notificationSendAndReceive.getTime());
        subject.setText(notificationSendAndReceive.getSubject());
        message.setText(notificationSendAndReceive.getMessage());
        return listviewitem;
    }
}
