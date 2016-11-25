package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.utils.PathRequest;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ComfirmationActivity extends BaseActivity implements OnMapReadyCallback {
    RequestQueue requestQueue;
    LatLng From, To;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Toast.makeText(ComfirmationActivity.this, "Getting Your Location. Please Wait", Toast.LENGTH_LONG).show();

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double TajerLatitude, TajerLongitude;
        TajerLatitude = Double.parseDouble(getIntent().getExtras().getString("Lat"));
        TajerLongitude = Double.parseDouble(getIntent().getExtras().getString("Lng"));
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        From = new LatLng(TajerLatitude, TajerLongitude);
        To = new LatLng(LocationManage.Lat, LocationManage.Long);

        Double distance = SphericalUtil.computeDistanceBetween(From, To);
        NumberFormat format = new DecimalFormat("##.##");
        Marker marker = mMap.addMarker(new MarkerOptions().position(From).title("Tajer : " + format.format(distance / 1000) + " KM away"));

        marker.showInfoWindow();

        mMap.addMarker(new MarkerOptions().position(To).title("You"));

        new PathRequest().makeUrl(From.latitude, From.longitude, To.latitude, To.longitude, ComfirmationActivity.this, mMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(From));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(From, 15));
    }


    @Override
    public void onBackPressed() {
       // startActivity(new Intent(ComfirmationActivity.this,DeligateHomeActivity.class));
        finish();
    }
}