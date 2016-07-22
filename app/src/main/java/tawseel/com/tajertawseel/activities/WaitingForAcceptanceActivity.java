package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
                    i.putExtra("lat",(float)latitude);
                    i.putExtra("lng",(float)longitude);
                    startActivity(i);
                }
                return false;
            }
        });
//        LatLng latLng = new LatLng(lat,lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, Application.DEFAULT_ZOOM);
//        mMap.animateCamera(update);

    }
}
