package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DelegatesHomeAdapter;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class DeligateHomeActivity extends BaseActivity {
    static String DeligateID;
    private ViewPager homePager;
    private TabLayout homeTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_screen);
        setUpContents();
    }


    private void setUpContents() {
//        boolean CheckLogin=false;
//        try {
//          CheckLogin = getIntent().getExtras().getBoolean("flag");
//        }
//        catch (Exception e)
//        {}
//        if (CheckLogin) {
//            CheckLogin=false;
//            FirebaseMessaging.getInstance().subscribeToTopic("test");
//            FirebaseInstanceIDService dd = new FirebaseInstanceIDService();
//            String token = FirebaseInstanceId.getInstance().getToken();
//            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wInfo = wifiManager.getConnectionInfo();
//            DeligateID = getIntent().getExtras().getString("DeligateID");
//
//            dd.registerToken(token, DeligateID, wInfo.getMacAddress() + "");
//            Intent i = new Intent(DeligateHomeActivity.this,UpdateLocation.class);
//            startService(i);
//        }




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

}
