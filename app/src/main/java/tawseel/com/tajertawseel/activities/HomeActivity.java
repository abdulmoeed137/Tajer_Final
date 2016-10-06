package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.HomePagerAdapter;

/**
 * Created by Junaid-Invision on 7/2/2016.
 *
 * Edited by M Monis on 7/18/2016
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;

    DrawerLayout mDrawerLayout;
    String uname, email;
    public static String id;
    private ViewPager homePager;
    private TabLayout homeTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
System.gc();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);


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
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);

        } else if (v.getId() == R.id.option2) {
            Intent i = new Intent(HomeActivity.this, DeliveryGroupActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

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
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                // NO need to show the dialog;

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    //failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Location Enabled", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "Location is Disabled. Please Enable Location From Setting", Toast.LENGTH_LONG).show();
            }

        }}
}
