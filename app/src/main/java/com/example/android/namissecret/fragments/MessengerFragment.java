package com.example.android.namissecret.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.example.android.namissecret.R;
import com.example.android.namissecret.models.Messenger;


public class MessengerFragment extends Fragment {

    ArrayList<Messenger> msg = new ArrayList<Messenger>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messenger, container, false);


        return view;
    }
}
