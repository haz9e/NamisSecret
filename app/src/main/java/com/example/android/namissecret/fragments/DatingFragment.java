package com.example.android.namissecret.fragments;

import android.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.android.namissecret.MatchActivity;
import com.example.android.namissecret.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DatingFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private ImageButton like,pass;
    private DatabaseReference usersDb;
    private ImageView userSearch;
    private TextView setUser,userSexx,userAge;
    private String userSex;
    private String oppositeUserSex,uidOpposite;
    private FirebaseUser user;
    private DatabaseReference oppisiteUserDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dating, container, true );
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        setUpUi(rootView);
        checkUserSex();

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = user.getUid().toString();// Take the current user
                oppisiteUserDb.child(uidOpposite).child("connections").child("dislike").child(user.getUid()).setValue(true);
                getOppositeSexUsers();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = user.getUid().toString();// Take the current user
                oppisiteUserDb.child(uidOpposite).child("connections").child("like").child(user.getUid()).setValue(true);
                //check if match after like
                checkMatch(uidOpposite);
                getOppositeSexUsers();
            }
        });

        return rootView;
    }

    public void checkUserSex(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersDb = this.usersDb.child(user.getUid());
        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("sex").getValue() != null){
                        userSex = dataSnapshot.child("sex").getValue().toString();
                        switch (userSex){
                            case "Male":
                                oppositeUserSex = "Female";
                                break;
                            case "Female":
                                oppositeUserSex = "Male";
                                break;
                        }
                        getOppositeSexUsers();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getOppositeSexUsers(){
        oppisiteUserDb = FirebaseDatabase.getInstance().getReference().child("Users");
        oppisiteUserDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("sex").getValue() != null) {
                    if (dataSnapshot.exists()  && dataSnapshot.child("sex").getValue().toString().equals(oppositeUserSex) && !dataSnapshot.child("connections").child("dislike").hasChild(user.getUid())&& !dataSnapshot.child("connections").child("like").hasChild(user.getUid())) {
                        //check the other sex, if we already have pass or like
                        String profileImageUrl = "default";
                        if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        setUser.setText(dataSnapshot.child("name").getValue().toString());
                        userSexx.setText(dataSnapshot.child("sex").getValue().toString());
                        userAge.setText(dataSnapshot.child("age").getValue().toString());
                        uidOpposite=dataSnapshot.getKey().toString();
                        Glide.with(getActivity()).load(profileImageUrl).into(userSearch);
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void setUpUi(View view){
        userSearch = (ImageView)view.findViewById(R.id.image_user);
        userAge =(TextView)view.findViewById(R.id.tvUserAge);
        userSexx=(TextView)view.findViewById(R.id.tvUserSex);
        firebaseAuth = FirebaseAuth.getInstance();
        setUser = (TextView)view.findViewById(R.id.tvSetUser);
        like = (ImageButton) view.findViewById(R.id.btnLike);
        pass = (ImageButton) view.findViewById(R.id.btnDislike);
    }

    private void checkMatch(String userId) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference checkMatchDb = usersDb.child(user.getUid()).child("connections").child("like").child(userId);
        checkMatchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(user.getUid()).child("ChatId").setValue(key);
                    usersDb.child(user.getUid()).child("connections").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);
                    goToMatches();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void goToMatches() {
        Context context = getActivity();
        CharSequence text = "Vous avez un match!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        startActivity(new Intent(getActivity(), MatchActivity.class));
        return;
    }
}