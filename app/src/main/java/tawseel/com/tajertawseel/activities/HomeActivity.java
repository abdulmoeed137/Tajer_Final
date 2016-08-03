package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/2/2016.
 *
 * Edited by M Monis on 7/18/2016
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, LocationListener {

    DrawerLayout mDrawerLayout;
    String uname, email;
    public static String id;
    LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

        uname = LoginActivity.uname;
        email = LoginActivity.email;
        id = LoginActivity.LoginID;
        setupContents();
    }

    private void setupContents() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        TextView uname_tv = (TextView) mDrawerLayout.findViewById(R.id.dname);
        TextView email_tv = (TextView) mDrawerLayout.findViewById(R.id.demail);
        uname_tv.setText(uname);
        email_tv.setText(email);
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
        findViewById(R.id.new_button).setOnClickListener(this);

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
        else if(v.getId() == R.id.new_button)
        {
            showDialogue();
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
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


    public void showDialogue()
    {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.new_button_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.show();


        dialog.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,HomePickSetActivity.class);
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.BtnNewGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this ,PostNewGroupActivity.class));
            }
        });
        dialog.findViewById(R.id.BtnNewOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this ,AddNewOrderActivity.class));
            }
        });
    }
}
