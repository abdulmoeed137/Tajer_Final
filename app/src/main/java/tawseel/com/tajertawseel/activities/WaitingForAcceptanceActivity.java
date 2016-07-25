package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/16/2016.
 */
public class WaitingForAcceptanceActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_waiting);

        Toast.makeText(WaitingForAcceptanceActivity.this,kmDistanceBetweenPoints(24.915460, 67.034923,24.964350, 67.066641)+"",Toast.LENGTH_SHORT).show();
        setUpToolbar();
        setupMap();
    }



    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.waiting_for_acceptance));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng positionUpdate = new LatLng(24.9033f,67.0346f);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate,16);
        mMap.animateCamera(update);


        addMarker(24.9033f,67.0346f,"destination", R.drawable.destination_marker);
        addMarker(24.9575,67.0639,"Car", R.drawable.car_marker);
        addMarker(24.9333,67.0333,"Car", R.drawable.car_marker);


    }

    private void addMarker(double lat, double lng, String title,int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));




        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                if(marker.getTitle().equals("Car"))
                {
                    double latitude  = marker.getPosition().latitude;
                    double longitude = marker.getPosition().longitude;

                    Log.d("Latitide , Longitide",""+latitude+" "+longitude);

                    Intent i = new Intent (WaitingForAcceptanceActivity.this,ChooseDelegateActivity.class);
                    i.putExtra("destLat",24.9033f);
                    i.putExtra("destLng", 67.0346f);
                    i.putExtra("lat",(double)latitude);
                    i.putExtra("lng",(double)longitude);
                    startActivity(i);
                }
                return false;
            }
        });
//        LatLng latLng = new LatLng(lat,lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, Application.DEFAULT_ZOOM);
//        mMap.animateCamera(update);

    }
    private double kmDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = (double) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return (6366000*tt)/1000;
    }
}
