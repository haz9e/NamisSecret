package com.example.android.namissecret.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.example.android.namissecret.R;

public class MessengerItemFragment extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contacts);
    }


    @Override
    public void onClick(View v) {

        startActivity(new Intent(MessengerItemFragment.this, MessengerChat.class));

    }

}
