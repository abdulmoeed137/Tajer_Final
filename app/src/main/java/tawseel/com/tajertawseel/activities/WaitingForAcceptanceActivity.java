package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;

/**
 * Created by Junaid-Invision on 7/16/2016.
 */
public class WaitingForAcceptanceActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    LatLng origin;
    private GoogleMap mMap;
    static PendingIntent pendingIntent;
    static AlarmManager manager;
    static    int i=0;
    static String GrpID;
    static Activity c;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_waiting);
        c=this;
        GrpID=getIntent().getExtras().getString("GroupID");
        Intent alarmIntent = new Intent(WaitingForAcceptanceActivity.this, DeligateRequest.class);
        pendingIntent = PendingIntent.getBroadcast(WaitingForAcceptanceActivity.this, 0, alarmIntent, 0);
        start();
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
            Toast.makeText(WaitingForAcceptanceActivity.this, "Location Permission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
        try {
            origin = new LatLng(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());

        } catch (Exception e) {

            Toast.makeText(WaitingForAcceptanceActivity.this, "No Old Location Saved", Toast.LENGTH_SHORT).show();
        }
       setUpToolbar();
        setupMap();
    }


    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.waiting_for_acceptance));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng positionUpdate = origin;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate, 12);
        mMap.animateCamera(update);


        addMarker(origin.latitude,origin.longitude, "Seller", R.drawable.destination_marker);
        GetDeligates(this.origin);


    }

    private void addMarker(double lat, double lng, String title, int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));


        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                if (marker.getTitle().equals("Car")) {
                    double latitude = marker.getPosition().latitude;
                    double longitude = marker.getPosition().longitude;

                    Log.d("Latitide , Longitide", "" + latitude + " " + longitude);

                    Intent i = new Intent(WaitingForAcceptanceActivity.this, ChooseDelegateActivity.class);
                    i.putExtra("destLat", 24.9033f);
                    i.putExtra("destLng", 67.0346f);
                    i.putExtra("lat", (double) latitude);
                    i.putExtra("lng", (double) longitude);
                    startActivity(i);
                }
                return false;
            }
        });
//        LatLng latLng = new LatLng(lat,lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, Application.DEFAULT_ZOOM);
//        mMap.animateCamera(update);

    }

    @Override
    public void onLocationChanged(Location location) {
        origin = new LatLng(location.getLatitude(), location.getLongitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(WaitingForAcceptanceActivity.this, "Location is Off!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
    public void GetDeligates (LatLng origin)
    {
         RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(WaitingForAcceptanceActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, functions.add+"EligibleDeligates.php?latitude="+this.origin.latitude+"&longitude="+this.origin.longitude,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                            functions.AvailableDeligates=jsonArr.length()+"";
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                addMarker(Double.parseDouble(jsonObj.getString("Latitude")),Double.parseDouble(jsonObj.getString("Longitude")), jsonObj.getString("Name"), R.drawable.car_marker);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                });

        //dummy Adapter
        // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
        requestQueue.add(jsonObjectRequest);
    }
    public void start() {
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 5000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);


    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        manager.cancel(pendingIntent);
    }
}