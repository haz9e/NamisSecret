package com.example.android.namissecret.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.namissecret.R;

import java.util.ArrayList;

public class MessengerAdapter extends BaseAdapter {

    public Activity context;
    public ArrayList<Messenger> msg;

    public MessengerAdapter(Activity context, ArrayList<Messenger> msg) {
        this.context = context;
        this.msg = msg;
    }

    @Override
    public int getCount() {
        return this.msg.size();
    }

    @Override
    public Object getItem(int position) {
        return this.msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.msg.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.fragment_contacts, null);

      TextView textView = (TextView)rowView.findViewById(R.id.tvUsername);
      textView.setText(this.msg.get(position).getUsername());

      TextView textView2 =(TextView)rowView.findViewById(R.id.tvAge);
      textView2.setText(this.msg.get(position).getAge());

      ImageView imageView =(ImageView)rowView.findViewById(R.id.imageContact);
        Glide.with(context).load(this.msg.get(position).getImageContact()).into(imageView);

        return rowView;
    }
}
