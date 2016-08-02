package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.HomePagerAdapter;

/**
 * Created by Junaid-Invision on 7/2/2016.
 *
 * Edited by M Monis on 7/18/2016
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    String uname, email;
    public static String id;
    private ViewPager homePager;
    private TabLayout homeTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        setupContents();
    }

    private void setupContents() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        uname = LoginActivity.uname;
        email = LoginActivity.email;
        id = LoginActivity.LoginID;
        TextView uname_tv = (TextView) mDrawerLayout.findViewById(R.id.dname);
        TextView email_tv = (TextView) mDrawerLayout.findViewById(R.id.demail);
        uname_tv.setText(uname);
        email_tv.setText(email);


        homePager = (ViewPager)findViewById(R.id.homePager);
        homeTabLayout = (TabLayout)findViewById(R.id.home_tabLayout);

        homePager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        homeTabLayout.setupWithViewPager(homePager);

        LayoutInflater inflater = LayoutInflater.from(this);

      // TabLayout.Tab tab1= homeTabLayout.getTabAt(0);

        View view1  = inflater.inflate(R.layout.tab_text_layout,null,false);
        TextView text  = (TextView) view1.findViewById(R.id.tab_text);
        text.setText(getString(R.string.the_map));
        homeTabLayout.getTabAt(0).setCustomView(view1);


        /// second tab

       // TabLayout.Tab tab2= homeTabLayout.getTabAt(0);

        View view2  = inflater.inflate(R.layout.tab_text_layout,null,false);
        TextView text2  = (TextView) view2.findViewById(R.id.tab_text);
        text2.setText(getString(R.string.dilevered_now));
        homeTabLayout.getTabAt(1).setCustomView(view2);
        setupListeners();


    }

    public void setupListeners() {
        mDrawerLayout.findViewById(R.id.option1).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option2).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option3).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option4).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option5).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option6).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option7).setOnClickListener(this);
       // findViewById(R.id.new_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.option1) {
            Intent i = new Intent(HomeActivity.this, CustomerRequestActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.option2) {
            Toast.makeText(HomeActivity.this, "Option2", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(HomeActivity.this, DeliveryGroupActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.option3) {
            Intent intent = new Intent (HomeActivity.this,DateOfConnectionsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.option4) {
            Intent intent = new Intent (HomeActivity.this,DelegatesQuestionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.option5) {
            Toast.makeText(HomeActivity.this, "Use the navigation drawer to roam around the app", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.option6) {
            Toast.makeText(HomeActivity.this, "We are Tajer Tawseel", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.option7) {
            Toast.makeText(HomeActivity.this, "Send us feedback at Tajer Tawseel", Toast.LENGTH_SHORT).show();
        }


    }






}
