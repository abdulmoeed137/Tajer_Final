package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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
 * Created by Junaid-Invision on 7/24/2016.
 */
public class BackToChooseNewDelegatesActivity extends BaseActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_to_choose_delegates);
        TextView TextDeligateCarBrand= (TextView)findViewById(R.id.CarBrand);
        TextView TextDeligateCarModel= (TextView)findViewById(R.id.CarMode);
        TextView TextDeligateCarNumber= (TextView)findViewById(R.id.CarNo);
        TextView TextDeligateContact= (TextView)findViewById(R.id.DeligateContact);
        TextView TextDeligateName= (TextView)findViewById(R.id.DeligateName);
        TextDeligateCarBrand.setText(getIntent().getExtras().getString("CarBrand"));
        TextDeligateCarModel.setText(getIntent().getExtras().getString("CarModel"));
        TextDeligateCarNumber.setText(getIntent().getExtras().getString("CarNo"));
        TextDeligateContact.setText(getIntent().getExtras().getString("DeligateContact"));
        TextDeligateName.setText(getIntent().getExtras().getString("DeligateName"));
        setUpToolbar();
        setupMap();

    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
       CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.back_to_choose_delegates));
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
        LatLng positionUpdate = new LatLng(getIntent().getExtras().getDouble("dLat"),getIntent().getExtras().getDouble("dLng"));

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate,16);
        mMap.animateCamera(update);


       double  dLat =getIntent().getExtras().getDouble("dLat");
        double dLng = getIntent().getExtras().getDouble("dLng");


       double  lat =getIntent().getExtras().getDouble("lat");
       double  lng = getIntent().getExtras().getDouble("lng");


        addMarker(dLat,dLng,"Seller", R.drawable.destination_marker);
        addMarker(lat,lng,"Deligate", R.drawable.car_marker);


        PathRequest request = new PathRequest();
        request.makeUrl(dLat,dLng,lat,lng,this,mMap);


    }
}
