package tawseel.com.tajertawseel.activities;

import android.Manifest;
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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    LatLng origin;
    private GoogleMap mMap;
    double dLat ;
    double  dLng ;


    double lat ;
    double lng ;
static boolean keepRunning2=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delegate);
    origin= new LatLng(LocationManage.Lat,LocationManage.Long);
        TextView TextAvailableDeligates= (TextView)findViewById(R.id.TextAvailableDelegates);
        final TextView TextDeligateCarBrand= (TextView)findViewById(R.id.TextDeligateCarBrand);
        final TextView TextDeligateCarModel= (TextView)findViewById(R.id.TextDeligateCarModel);
        final TextView TextDeligateCarNumber= (TextView)findViewById(R.id.TextDeligateCarNumber);
        final TextView TextDeligateContact= (TextView)findViewById(R.id.TextDeligateContact);
        final TextView TextDeligateName= (TextView)findViewById(R.id.TextDeligateName);
        TextAvailableDeligates.setText(functions.AvailableDeligates+"");
        TextDeligateCarBrand.setText(getIntent().getExtras().getString("CarBrand")+"");
        TextDeligateCarModel.setText(getIntent().getExtras().getString("CarModel")+"");
        TextDeligateContact.setText(getIntent().getExtras().getString("Contact"));
        TextDeligateName.setText(getIntent().getExtras().getString("Name"));
        TextDeligateCarNumber.setText(getIntent().getExtras().getString("CarNo"));
        setUpToolbar();
        setupMap();
        Toast.makeText(ChooseDelegateActivity.this,getIntent().getExtras().getString("DeligateID"),Toast.LENGTH_SHORT).show();

        LinearLayout moreButton_layout = (LinearLayout)findViewById(R.id.moreLayout);
        moreButton_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent (ChooseDelegateActivity.this,BackToChooseNewDelegatesActivity.class);
                i.putExtra("dLat",dLat);i.putExtra("dLng",dLng);i.putExtra("lat",lat);i.putExtra("lng",lng);
                i.putExtra("CarBrand",TextDeligateCarBrand.getText().toString());i.putExtra("CarModel",TextDeligateCarModel.getText().toString());i.putExtra("CarNo",TextDeligateCarNumber.getText().toString());i.putExtra("DeligateContact",TextDeligateContact.getText().toString());
               i.putExtra("DeligateName",TextDeligateName.getText().toString());
                startActivity(i);
                finish();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
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
        LatLng positionUpdate =origin;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate,16);
        mMap.animateCamera(update);


    dLat = origin.latitude;
       dLng = origin.longitude;


        lat = Double.parseDouble(getIntent().getExtras().getString("Latitude"));
        lng = Double.parseDouble(getIntent().getExtras().getString("Longitude"));


        addMarker(dLat,dLng,"Seller", R.drawable.destination_marker);
        addMarker(lat,lng,"Deligate", R.drawable.car_marker);


        PathRequest request = new PathRequest();
        request.makeUrl(dLat,dLng,lat,lng,this,mMap);

    }
}
