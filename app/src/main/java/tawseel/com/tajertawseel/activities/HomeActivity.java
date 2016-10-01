package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.HomePagerAdapter;

/**
 * Created by Junaid-Invision on 7/2/2016.
 *
 * Edited by M Monis on 7/18/2016
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, LocationListener {
    LocationManager locationManager;
    DrawerLayout mDrawerLayout;
    String uname, email;
    public static String id;
    private ViewPager homePager;
    private TabLayout homeTabLayout;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
System.gc();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Location Permission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
        try {
            LocationManage.Lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            LocationManage.Long = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

        } catch (Exception e) {

            Toast.makeText(this, "No Old Location Saved", Toast.LENGTH_SHORT).show();
        }
//// for knowing the distance between the two points
//        LatLng to = new LatLng(24.92, 67.0297);/// destination
//        LatLng from = new LatLng(24.9231, 67.0204);//source point
//        Double distance = SphericalUtil.computeDistanceBetween(from, to); //map utils function to compute distance in meters


       // Toast.makeText(this, "" + distance / 1000 + "KM", Toast.LENGTH_SHORT).show();
        setupContents();
    }

    private void setupContents() {

        Intent i = new Intent(HomeActivity.this, UpdateLocationSeller.class);
        startService(i);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
      //  mDrawerLayout.openDrawer(Gravity.RIGHT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.drawerIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                else
                    mDrawerLayout.openDrawer(Gravity.RIGHT);

            }
        });
        uname = LoginActivity.uname;
        email = LoginActivity.email;
        id = LoginActivity.LoginID;
        TextView uname_tv = (TextView) mDrawerLayout.findViewById(R.id.DeligateName);
        TextView email_tv = (TextView) mDrawerLayout.findViewById(R.id.demail);
        uname_tv.setText(uname);
        email_tv.setText(email);

        homePager = (ViewPager) findViewById(R.id.homePager);
        homeTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);

        homePager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
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
        mDrawerLayout.findViewById(R.id.option8).setOnClickListener(this);
        // findViewById(R.id.new_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.option1) {
            Intent i = new Intent(HomeActivity.this, CustomerRequestActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.option2) {
            Intent i = new Intent(HomeActivity.this, DeliveryGroupActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.option3) {
            Intent intent = new Intent(HomeActivity.this, DateOfConnectionsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.option4) {
            Intent intent = new Intent(HomeActivity.this, DelegatesQuestionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.option5) {
            Toast.makeText(HomeActivity.this, "Use the navigation drawer to roam around the app", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.option6) {
            Toast.makeText(HomeActivity.this, "We are Tajer Tawseel", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.option7) {
            Toast.makeText(HomeActivity.this, "Send us feedback at Tajer Tawseel", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.option8) {
            LoginActivity.LoginID= null;
            finish();startActivity(new Intent(this,LoginActivity.class));
            stopService(new Intent(this,UpdateLocationSeller.class));
            SharedPreferences settings;
            SharedPreferences.Editor editor;

            settings = this.getSharedPreferences("tajer", Context.MODE_PRIVATE);
            editor = settings.edit();
            editor.putString("id",null); //3
            editor.commit();

        }


    }

    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManage.Lat = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLatitude();
        LocationManage.Long=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Location is Off!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
