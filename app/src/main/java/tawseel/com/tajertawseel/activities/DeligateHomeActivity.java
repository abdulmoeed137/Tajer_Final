package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DelegatesHomeAdapter;
import tawseel.com.tajertawseel.firebase.FirebaseInstanceIDService;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class DeligateHomeActivity extends BaseActivity implements View.OnClickListener {
    static String DeligateID;
    private ViewPager homePager;
    private TabLayout homeTabLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_screen);
        DeligateID=LoginActivity.DeligateID;
        setUpContents();
        setupListeners();
    }


    private void setUpContents() {
       boolean CheckLogin=false;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        TextView DeligateID2= (TextView)mDrawerLayout.findViewById(R.id.DeligateID);
                DeligateID2.setText(LoginActivity.DeligateID);
        TextView DeligateName= (TextView)mDrawerLayout.findViewById(R.id.DeligateName);
        DeligateName.setText(LoginActivity.email);

        try {
          CheckLogin = getIntent().getExtras().getBoolean("flag");
        }
        catch (Exception e)
        {}
        if (CheckLogin) {
            CheckLogin=false;
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            FirebaseInstanceIDService dd = new FirebaseInstanceIDService();
            String token = FirebaseInstanceId.getInstance().getToken();
            DeligateID = getIntent().getExtras().getString("DeligateID");
           // Toast.makeText(getApplicationContext(),DeligateID,Toast.LENGTH_SHORT).show();
            dd.registerToken(token, DeligateID, Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID) + "");
//            Intent i = new Intent(DeligateHomeActivity.this,UpdateLocation.class);
//            startService(i);
        }




        homePager = (ViewPager) findViewById(R.id.homePager);
        homeTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);

        homePager.setAdapter(new DelegatesHomeAdapter(getSupportFragmentManager()));
        homeTabLayout.setupWithViewPager(homePager);

        LayoutInflater inflater = LayoutInflater.from(this);

        // TabLayout.Tab tab1= homeTabLayout.getTabAt(0);

        View view1 = inflater.inflate(R.layout.tab_text_layout, null, false);
        TextView text = (TextView) view1.findViewById(R.id.tab_text);
        text.setText(getString(R.string.the_map));
        homeTabLayout.getTabAt(0).setCustomView(view1);


        /// second tab

        // TabLayout.Tab tab2= homeTabLayout.getTabAt(0);

        View view2 = inflater.inflate(R.layout.tab_text_layout, null, false);
        TextView text2 = (TextView) view2.findViewById(R.id.tab_text);
        text2.setText(getString(R.string.dilevered_now));
        homeTabLayout.getTabAt(1).setCustomView(view2);
       // setupListeners();
    }

    public void setupListeners() {
       // mDrawerLayout.findViewById(R.id.option1).setOnClickListener(this);
        //mDrawerLayout.findViewById(R.id.option2).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option3).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option4).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option5).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option6).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option7).setOnClickListener(this);

        mDrawerLayout.findViewById(R.id.confirmation).setOnClickListener(this);
        // findViewById(R.id.new_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.option3) {
            Intent intent = new Intent(DeligateHomeActivity.this, DeligateDateOfConnectionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.confirmation){

            //Intent i  = new Intent(DeligateHomeActivity.this,ComfirmationActivity.class);
            Intent i  = new Intent(DeligateHomeActivity.this,DeligateConfirmationActivity.class);
            startActivity(i);
            finish();
        }
        else if (v.getId() == R.id.option4){

            Intent intent = new Intent(DeligateHomeActivity.this,TradersFavouriteActivity.class);
            startActivity(intent);
        }

    }
}
