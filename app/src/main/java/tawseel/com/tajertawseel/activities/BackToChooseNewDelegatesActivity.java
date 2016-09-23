package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.utils.PathRequest;

/**
 * Created by Junaid-Invision on 7/24/2016.
 */
public class BackToChooseNewDelegatesActivity extends BaseActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView continuee;
    private RequestQueue requestQueue;
    CustomBoldTextView distance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_to_choose_delegates);
        requestQueue= Volley.newRequestQueue(this);
        distance = (CustomBoldTextView)findViewById(R.id.Distance) ;
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
        continuee = (TextView)findViewById(R.id.ButtonAccept);
        continuee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, functions.add+"ChooseDeligateSetToken.php?grpID="+WaitingForAcceptanceActivity.GrpID+"&hash="+HASH.getHash(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArr=response.getJSONArray("info");
                                    for(int i=0;i<jsonArr.length();i++) {
                                        final JSONObject jsonObj = jsonArr.getJSONObject(i);

                                        if (jsonObj.getString("status").equals("success")){

                                            startActivity( new Intent(BackToChooseNewDelegatesActivity.this,HomeActivity.class));
                                            finish();

                                        }
                                        else {
                                            Toast.makeText(BackToChooseNewDelegatesActivity.this,"Service Busy..",Toast.LENGTH_SHORT).show();
                                        }
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
        });


      TextView searchAgain = (TextView)findViewById(R.id.BtnSearchAgain);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  ProgressDialog progress = new ProgressDialog(BackToChooseNewDelegatesActivity.this, ProgressDialog.THEME_HOLO_DARK);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Loading...");
                progress.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, functions.add+"DeligateSearchAgain.php?grpID="+WaitingForAcceptanceActivity.GrpID+"&hash="+HASH.getHash(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArr=response.getJSONArray("info");
                                    for(int i=0;i<jsonArr.length();i++) {
                                        final JSONObject jsonObj = jsonArr.getJSONObject(i);

                                        if (jsonObj.getString("status").equals("success")){
                                            Intent j = new Intent(BackToChooseNewDelegatesActivity.this,WaitingForAcceptanceActivity.class);
                                            j.putExtra("GroupID",WaitingForAcceptanceActivity.GrpID);
                                            startActivity(j);
                                            finish();
                                            progress.hide();
                                        }
                                        else {
                                            Toast.makeText(BackToChooseNewDelegatesActivity.this,"Service Busy..",Toast.LENGTH_SHORT).show();
                                            progress.hide();
                                        }
                                    }

                                } catch (JSONException error) {
                                    error.printStackTrace();
                                    progress.hide();
                                    if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                                        Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                                .setAction("Undo", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                })
                                                .setActionTextColor(Color.RED)

                                                .show();}
                                };
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                                progress.hide();
                                if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                            .setAction("Undo", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })
                                            .setActionTextColor(Color.RED)

                                            .show();}
                            }
                        });

                //dummy Adapter
                // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
                int socketTimeout = 3000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                requestQueue.add(jsonObjectRequest);

            }
        });


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


//// for knowing the distance between the two points
        LatLng to = new LatLng(dLat, dLng);/// destination
        LatLng from = new LatLng(lat, lng);//source point
        Double distance2 = SphericalUtil.computeDistanceBetween(from, to); //map utils function to compute distance in meters

        distance2=Double.parseDouble(String.format("%.2f", distance2));
        distance.setText(distance2+"");



        PathRequest request = new PathRequest();
        request.makeUrl(dLat,dLng,lat,lng,this,mMap);


    }
}
