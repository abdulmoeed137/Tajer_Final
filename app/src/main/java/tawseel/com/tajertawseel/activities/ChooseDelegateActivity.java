package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.utils.PathRequest;

/**
 * Created by Junaid-Invision on 7/20/2016.
 */
public class ChooseDelegateActivity extends BaseActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delegate);
        setUpToolbar();
        setupMap();


        LinearLayout moreButton_layout = (LinearLayout)findViewById(R.id.moreLayout);
        moreButton_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent (ChooseDelegateActivity.this,BackToChooseNewDelegatesActivity.class);
                startActivity(i);
            }
        });

    }

    public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.choose_deligate));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }



    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }



    private void addMarker(double lat, double lng, String title,int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));

        mMap.addMarker(markerOptions);
}

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng positionUpdate = new LatLng(24.9033f,67.0346f);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate,16);
        mMap.animateCamera(update);


        float dLat = getIntent().getExtras().getFloat("destLat");
        float dLng = getIntent().getExtras().getFloat("destLng");


        float lat = getIntent().getExtras().getFloat("lat");
        float lng = getIntent().getExtras().getFloat("lng");


        addMarker(dLat,dLng,"destination", R.drawable.destination_marker);
        addMarker(lat,lng,"Car", R.drawable.car_marker);


        PathRequest request = new PathRequest();
        request.makeUrl(dLat,dLng,lat,lng,this,mMap);

    }


}
