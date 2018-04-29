package com.example.android.namissecret.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.namissecret.R;
import com.example.android.namissecret.models.Messenger;
import com.example.android.namissecret.models.MessengerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengerListFragment extends Fragment implements AdapterView.OnItemClickListener {


    private FirebaseAuth firebaseAuth;
    private ImageButton logout,like,pass,settings;
    private DatabaseReference usersDb;
    private ImageView userSearch;
    private TextView setUser,userSexx,userAge;
    private String userSex;
    private String oppositeUserSex,uidOpposite;
    private DatabaseReference oppisiteUserDb;
    private String currentUser;
    ArrayList<Messenger> msgs = new ArrayList<Messenger>();
    MessengerAdapter adapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messenger, null);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new MessengerAdapter(getActivity(), msgs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        getUserMatch();


        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), MessengerChat.class);
        startActivity(intent);

    }
   private void getUserMatch(){

       DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("connections").child("matches");
       usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for(DataSnapshot match: dataSnapshot.getChildren()){// will take the value inside matches
                        FetchInformation(match.getKey()); // work with current matches id
                   }

               }
           }

           private void FetchInformation(String matchKey) {
               DatabaseReference matchUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(matchKey);
               matchUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.exists()){
                           String matchKey=dataSnapshot.getKey();
                           String nameMatch="";
                           String ageMatch="";
                           String imageUrlMatch="";
                           if(dataSnapshot.child("name").getValue()!=null){
                               nameMatch= dataSnapshot.child("name").getValue().toString();
                           }
                           if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                               imageUrlMatch= dataSnapshot.child("profileImageUrl").getValue().toString();
                           }
                           if(dataSnapshot.child("age").getValue()!=null){
                               ageMatch= dataSnapshot.child("age").getValue().toString();
                           }
                           msgs.add(new Messenger(imageUrlMatch, nameMatch, ageMatch));
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }

}
