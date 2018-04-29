package com.example.android.namissecret;
import com.example.android.namissecret.R;
import com.example.android.namissecret.fragments.MessengerItemFragment;
import com.example.android.namissecret.fragments.MessengerListFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;


public class BoardActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        TabHost tabHost;

        MessengerListFragment listFragment;
        MessengerItemFragment itemFragment;
            listFragment = new MessengerListFragment();

            itemFragment = new MessengerItemFragment();

//        getFragmentManager().beginTransaction()
//                .add(R.id.frameLayout, listFragment)
//                .commit();


            TabHost host = (TabHost)findViewById(R.id.tabHost);
            host.setup();

            //Tab 1
            TabHost.TabSpec spec = host.newTabSpec("DRAGUER");
            spec.setContent(R.id.tab1);
            spec.setIndicator("",getResources().getDrawable(R.drawable.heart_icon));
            host.addTab(spec);

            //Tab 2
            spec = host.newTabSpec("CHATTER");
            spec.setContent(R.id.tab2);
            spec.setIndicator("",getResources().getDrawable(R.drawable.messenger_icon));
            host.addTab(spec);


            //Tab 3
            spec = host.newTabSpec("COMPTE");
            spec.setContent(R.id.tab3);
            spec.setIndicator("",getResources().getDrawable(R.drawable.account_icon));
            host.addTab(spec);

        }


        public void goBack(View view)
        {
            finish();
        }
    }

