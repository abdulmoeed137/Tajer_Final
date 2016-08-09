package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ListPopupAdapter;
import tawseel.com.tajertawseel.adapters.NewOrderProductAdapter;
import tawseel.com.tajertawseel.utils.PathRequest;

/**
 * Created by Junaid-Invision on 7/10/2016.
 */
public class AddNewOrderActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, DirectionCallback, android.location.LocationListener {
//okw

    private ProgressDialog progress;
    private String serverKey = "AIzaSyCBGMz8LNPmst35x_GK50FU-tj_E8q0EDw";
    private LatLng camera = null;
    String pid="";
    public static int oldid=-1;
    private LatLng destination = null;
    private GoogleMap mMap;
    public static ListView productsList;
    public  static Context context;
    LinearLayout postnewLayout;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    LatLng origin;
   public static EditText name,email,mobile;
    RadioGroup payment_method;
    public static TextView total,item_total,delivery;
    TextView cancel,save,continue_b;
    private RequestQueue requestQueue;
      public static int pmi=0;
    public static RadioButton bank,cash;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        productsList = (ListView) findViewById(R.id.product_list);
        bank=(RadioButton)findViewById(R.id.by_bank);
        cash=(RadioButton)findViewById(R.id.cash);

        name=(EditText)findViewById(R.id.c_name);
        email=(EditText)findViewById(R.id.c_email);
        mobile=(EditText)findViewById(R.id.c_number);

        payment_method=(RadioGroup)findViewById(R.id.pay_method_radiogrp);

        total=(TextView)findViewById(R.id.c_total);
        item_total=(TextView)findViewById(R.id.c_itemTotal);
        delivery=(TextView)findViewById(R.id.c_delivery_charges);

        cancel=(TextView)findViewById(R.id.cancel_button);
        save=(TextView)findViewById(R.id.protection_button);
        continue_b=(TextView)findViewById(R.id.continue_button);

        context=this;


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(AddNewOrderActivity.this, "Location Permission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
        try
        {
            origin=new LatLng(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        }
        catch (Exception e)
        {

            Toast.makeText(AddNewOrderActivity.this,"No Old Location Saved",Toast.LENGTH_SHORT).show();
        }
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpToolbar();
        setUpComponents();

    }

    public void allProduct() {

        Toast.makeText(AddNewOrderActivity.this, "OK", Toast.LENGTH_SHORT).show();
    }


    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.add_new_order));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }



    public void setUpComponents() {
       // productsList.setAdapter(new NewOrderProductAdapter(this,New_Orders_Activity.pList));
        postnewLayout = (LinearLayout) findViewById(R.id.post_your_new_product_container);
        productsList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Double itotal=0.0;
        for(int t=0;t<New_Orders_Activity.pList.size();t++)
            itotal+=Double.parseDouble(New_Orders_Activity.pList.get(t).getPrice());
        item_total.setText(String.valueOf(itotal));
        total.setText(String.valueOf(itotal+Double.parseDouble(delivery.getText().toString())));

        payment_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(bank.isChecked())
                {
                    item_total.setText("0");
                    total.setText(String.valueOf(Double.parseDouble(item_total.getText().toString())+Double.parseDouble(delivery.getText().toString())));
                pmi=1;
                }
                else
                {
                    //compute total
                    double itotal=0.0;
                    try {
                        for (int t = 0; t < New_Orders_Activity.pList.size(); t++)
                        {
                            itotal+=Double.parseDouble(New_Orders_Activity.pList.get(t).getPrice());
                        }

                        item_total.setText(String.valueOf(itotal));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(AddNewOrderActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                    total.setText(String.valueOf(itotal+Double.parseDouble(delivery.getText().toString())));
                    pmi=2;
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                New_Orders_Activity.pList.clear();
                Intent i=new Intent(AddNewOrderActivity.this,CustomerRequestActivity.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pmi==0||name.getText().toString().compareTo("")==0||email.getText().toString().compareTo("")==0||mobile.getText().toString().compareTo("")==0)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Complete all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(New_Orders_Activity.pList.size()==0)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Add at least one item to the order",Toast.LENGTH_SHORT).show();
                    return;
                }
                String item_ids="";
                for(int t=0;t<New_Orders_Activity.pList.size();t++)
                    item_ids+=(New_Orders_Activity.pList.get(t).getProductID()+"-"+New_Orders_Activity.pList.get(t).getQuantity()+" ");
               StringRequest request = new StringRequest(Request.Method.POST,functions.add+"addorder.php?itemlist="+item_ids, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObj=new JSONObject(response);
                            JSONArray arr=mainObj.getJSONArray("info");
                            Toast.makeText(AddNewOrderActivity.this,arr.getJSONObject(0).getString("OrderID"),Toast.LENGTH_SHORT).show();
                            if(arr.getJSONObject(0).getString("OrderID").compareTo("-1")!=0)
                            {
                              New_Orders_Activity.pList.clear();
                              Toast.makeText(AddNewOrderActivity.this,"Order saved "+arr.getJSONObject(0).getString("OrderID"),Toast.LENGTH_SHORT).show();
                              Intent i=new Intent(AddNewOrderActivity.this,AddNewOrderActivity.class);
                              startActivity(i);
                          }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }// in case error
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //send data to server using POST
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id",HomeActivity.id);
                        hashMap.put("hash",HASH.getHash());
                        hashMap.put("delivery","20");
                        hashMap.put("pay",String.valueOf(pmi));
                        hashMap.put("price",item_total.getText().toString());
                        hashMap.put("confirm","0");
                        hashMap.put("name",name.getText().toString());
                        hashMap.put("email",email.getText().toString());
                        hashMap.put("number",mobile.getText().toString());
                        return hashMap;
                    }
                };
                try{
                    requestQueue= Volley.newRequestQueue(AddNewOrderActivity.this);
                    requestQueue.add(request);
                }
                catch (Exception e)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
                }

            }
        });

        continue_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pmi==0||name.getText().toString().compareTo("")==0||email.getText().toString().compareTo("")==0||mobile.getText().toString().compareTo("")==0)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Complete all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(New_Orders_Activity.pList.size()==0)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Add at least one item to the order",Toast.LENGTH_SHORT).show();
                    return;
                }
                String item_ids="";
                for(int t=0;t<New_Orders_Activity.pList.size();t++)
                    item_ids+=(New_Orders_Activity.pList.get(t).getProductID()+"-"+New_Orders_Activity.pList.get(t).getQuantity()+" ");
                String curl="";
                if(oldid!=-1)
                    curl+=functions.add+"changes_order_status.php";
                else
                curl+=functions.add+"addorder.php?itemlist="+item_ids;

                StringRequest request = new StringRequest(Request.Method.POST,curl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObj=new JSONObject(response);
                            JSONArray arr=mainObj.getJSONArray("info");
                            Toast.makeText(AddNewOrderActivity.this,arr.getJSONObject(0).getString("OrderID"),Toast.LENGTH_SHORT).show();
                            if(arr.getJSONObject(0).getString("OrderID").compareTo("-1")!=0)
                            {
                                if(oldid!=-1)
                                    oldid=-1;
                                New_Orders_Activity.pList.clear();
                                Toast.makeText(AddNewOrderActivity.this,"Order Confirmed "+arr.getJSONObject(0).getString("OrderID"),Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(AddNewOrderActivity.this,PickSetActivity.class);
                                i.putExtra("orderID",arr.getJSONObject(0).getString("OrderID"));
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }// in case error
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //send data to server using POST
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        if(oldid==-1){
                        hashMap.put("id",HomeActivity.id);
                        hashMap.put("hash",HASH.getHash());
                        hashMap.put("delivery","20");
                        hashMap.put("pay",String.valueOf(pmi));
                        hashMap.put("price",item_total.getText().toString());
                        hashMap.put("confirm","1");
                        hashMap.put("name",name.getText().toString());
                        hashMap.put("email",email.getText().toString());
                        hashMap.put("number",mobile.getText().toString());
                       }
                        else
                        {
                            hashMap.put("id",HomeActivity.id);
                            hashMap.put("hash",HASH.getHash());
                            hashMap.put("oid",oldid+"");
                        }
                        return hashMap;
                    }
                };
                try{
                    requestQueue= Volley.newRequestQueue(AddNewOrderActivity.this);
                    requestQueue.add(request);
                }
                catch (Exception e)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Spinner layout = (Spinner) findViewById(R.id.popup_view);
        layout.setAdapter(new ListPopupAdapter(this));

       View tajer_lap=(View)findViewById(R.id.tajer_lap_view);
        tajer_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent (AddNewOrderActivity.this, Tajer_Lap_Activity.class);
                startActivity(i);
            }
        });


        postnewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddNewOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.add_new_order_dialogue);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                lp.dimAmount = 0.3f;
                final EditText newitem_name=(EditText)dialog.findViewById(R.id.new_item_name_et);
                final EditText newitem_price=(EditText)dialog.findViewById(R.id.new_item_price_et);
                Button add_new_item=(Button)dialog.findViewById(R.id.add_item_to_list);
                add_new_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newitem_name.getText().toString().compareTo("")==0)
                        {
                            Toast.makeText(AddNewOrderActivity.this,"Item must have a name",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(newitem_price.getText().toString().compareTo("")==0)
                        {
                            Toast.makeText(AddNewOrderActivity.this,"Item must have a price",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"addproduct.php", new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    if(response!="-1")
                                    {
                                        pid=response;
                                        Toast.makeText(AddNewOrderActivity.this,"Product saved",Toast.LENGTH_SHORT).show();finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }// in case error
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            //send data to server using POST
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("id",HomeActivity.id);
                                hashMap.put("hash",HASH.getHash());
                                hashMap.put("delivery","20");
                                hashMap.put("pay",String.valueOf(pmi));
                                hashMap.put("price",item_total.getText().toString());
                                hashMap.put("confirm","1");
                                hashMap.put("name",name.getText().toString());
                                hashMap.put("email",email.getText().toString());
                                hashMap.put("number",mobile.getText().toString());
                                return hashMap;
                            }
                        };
                        try{
                           RequestQueue requestQueue1= Volley.newRequestQueue(AddNewOrderActivity.this);
                            requestQueue1.add(request);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(AddNewOrderActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAllProd) {

            startActivity(new Intent(getApplicationContext(), ProductList.class));

        }
        if (v.getId() == R.id.whatsappId) {
            builderWithBtn();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Log.d("Map Ready","Map is ready");
    }

    public void requestDirection() {

        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {

            mMap.addMarker(new MarkerOptions().position(origin).title("Seller") );
            mMap.addMarker(new MarkerOptions().position(destination).title("Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 3, Color.RED));

        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }


    @Override
    public void onLocationChanged(Location location) {
        origin= new LatLng(location.getLatitude(), location.getLongitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(AddNewOrderActivity.this, "Location is Off!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

    }

    void builderWithBtn() {
        final Dialog dialog = new Dialog(AddNewOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.alert_with_2button_1text);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.3f;
        final EditText input=(EditText)dialog.findViewById(R.id.new_item_name_et);

       dialog.findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getLocationFromAddress(input.getText().toString());
                    Toast.makeText(AddNewOrderActivity.this, input.getText().toString(), Toast.LENGTH_SHORT).show();
                    requestDirection();
                    dialog.cancel();
                }
                catch (Exception e)
                {
                    Toast.makeText(AddNewOrderActivity.this,"Invalid URL or Connection Error",Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }

            }
        });
        dialog.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        dialog.show();


     /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter Location URL");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                try {
                    progress = ProgressDialog.show(AddNewOrderActivity.this, "Loading",
                            "Please Wait..", true);
                    getLocationFromAddress(input.getText().toString());
                    Toast.makeText(AddNewOrderActivity.this,input.getText().toString(),Toast.LENGTH_SHORT).show();
                    requestDirection();
                    progress.dismiss();
                }
                catch (Exception e)
                {
                    Toast.makeText(AddNewOrderActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    Log.e("Masla",e.toString());
                    progress.dismiss();
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show(); */
    }


    public void getLocationFromAddress(String add) {

        URL url = null;
        try {
            url = new URL(add);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection ucon = null;


        try {

            ucon = (HttpURLConnection) url.openConnection();
            ucon.setInstanceFollowRedirects(true);
            URL secondURL = new URL(ucon.getHeaderField("Location"));
         String  dest=secondURL+"";
            Log.d("neche",dest);
            try {
                String[] parts = dest.split("q=");

                String[] parts2 = parts[1].split("&hl");
                String part2 = parts2[0]; // 034556

                String[] latlong = part2.split(",");
                double sourceLat = Double.valueOf(latlong[0]);
                double sourceLong = Double.valueOf(latlong[1]);
                destination=new LatLng(sourceLat,sourceLong);
            }
            catch (Exception e)
            {
                String[] parts = dest.split("search/");

                String[] parts2 = parts[1].split("/data");
                String part2 = parts2[0]; // 034556

                String[] latlong = part2.split(",");
                double sourceLat = Double.valueOf(latlong[0]);
                double sourceLong = Double.valueOf(latlong[1]);
                destination=new LatLng(sourceLat,sourceLong);
            }



        } catch (IOException e) {
          }


    }



    private void addMarker(double lat, double lng, String title,int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));

        mMap.addMarker(markerOptions);
    }



}
