package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.utils.PathRequest;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ComfirmationActivity extends BaseActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        setUpToolbar();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        // title.setText(getString(R.string.connecting_profile_delegates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        title.setText("تأكيد");
        // deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Abbasi = new LatLng(24.9200, 67.0297);
        LatLng goleMarket = new LatLng(24.9145, 67.0242);

        Double distance = SphericalUtil.computeDistanceBetween(Abbasi, goleMarket);
        NumberFormat format = new DecimalFormat("##.##");
       Marker marker =  mMap.addMarker(new MarkerOptions().position(Abbasi).title(""+format.format(distance/1000)+" KM away"));

        marker.showInfoWindow();



      //  LatLng to = new LatLng(24.92, 67.0297);/// destination
        //LatLng from = new LatLng(24.9231, 67.0204);//source point
        ;
        mMap.addMarker(new MarkerOptions().position(goleMarket).title(""+format.format(distance/1000)+"KM away"));
        new PathRequest().makeUrl(24.9200, 67.0297,24.9145, 67.0242,ComfirmationActivity.this,mMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Abbasi));

    }
}
