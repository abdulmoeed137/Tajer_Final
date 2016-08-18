package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;

    /**
     * Created by Junaid-Invision on 7/12/2016.
     */
    public class NotificationOrderDetails extends BaseActivity implements OnMapReadyCallback, android.location.LocationListener{

        ListView productList;
        private RequestQueue requestQueue;
        ArrayList<PostGroupData> list = new ArrayList<>();;
        private TextView total_orders;
        private GoogleMap mMap=null;
        LatLng origin ;
        LocationManager locationManager;
        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_notification_order_details);
            TextView ButtonSave= (TextView)findViewById(R.id.ButtonSave);
            ButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView ButtonContinue= (TextView)findViewById(R.id.ButtonAccept);
            TextView ButtonCancel= (TextView)findViewById(R.id.ButtonCancel);
            // origin= new LatLng(21.470285, 39.238547);
            LinearLayout ll = (LinearLayout)findViewById(R.id.LayoutAdd) ;
           ll.setVisibility(View.GONE);
            ButtonSave.setVisibility(View.GONE);
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
            try
            {
                origin= new LatLng(LocationManage.Lat,LocationManage.Long);
            }
            catch (Exception e)
            {

                Toast.makeText(this,"No Old Location Saved",Toast.LENGTH_SHORT).show();
            }

            requestQueue = Volley.newRequestQueue(this);
            total_orders = (TextView)findViewById(R.id.request_count);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            setUpToolbar();
            setUpComponents();

        }
        public void setUpComponents ()
        {
            productList = (ListView)findViewById(R.id.product_list);
            Toast.makeText(NotificationOrderDetails.this,getIntent().getExtras().getString("id"),Toast.LENGTH_SHORT).show();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/GroupItem.php?id="+getIntent().getExtras().getString("id"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArr=response.getJSONArray("info");
                                total_orders.setText(jsonArr.length()+"");
                                for(int i=0;i<jsonArr.length();i++) {
                                    final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    PostGroupData item= new PostGroupData();
                                    item.setPriceRange(jsonObj.getString("PriceRange"));
                                    item.setCustomerEmail(jsonObj.getString("Email"));
                                    item.setCustomerName(jsonObj.getString("UserName"));
                                    item.setCustomerPhone(jsonObj.getString("Mobile"));
                                    item.setItemsPrice(jsonObj.getString("ItemsPrice"));
                                    item.setPayMethod(jsonObj.getString("PayMethod"));
                                    item.setOrderProductQuantity(jsonObj.getString("OrderMember"));
                                    item.setOrderID(jsonObj.getString("OrderID"));
                                    item.setLatitude(jsonObj.getString("Latitude"));
                                    item.setLongitude(jsonObj.getString("Longitude"));
                                    item.setID(jsonObj.getString("ID"));

                                    list.add(item);
                                }
                                for (int i=0;i<list.size();i++)
                                {
                                    addMarker(Double.parseDouble(list.get(i).getLatitude()),Double.parseDouble(list.get(i).getLongitude()),"Customer", R.drawable.person_marker);
                                }
                                productList.setAdapter(new PostGroupListAdapter(NotificationOrderDetails.this,list,"false"));

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
            productList.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

        }


        public void setUpToolbar()
        {
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
            title.setText(getString(R.string.create_new_group));
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng positionUpdate = new LatLng(origin.latitude,origin.longitude);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate,13);
            mMap.animateCamera(update);


            addMarker(origin.latitude,origin.longitude,"Seller", R.drawable.destination_marker);



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
            LocationManage.Lat = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLatitude();
            LocationManage.Long=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


