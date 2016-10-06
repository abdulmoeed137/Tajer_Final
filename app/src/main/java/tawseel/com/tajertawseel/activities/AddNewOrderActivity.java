package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ListPopupAdapter;

/**
 * Created by Junaid-Invision on 7/10/2016.
 */
public class AddNewOrderActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, DirectionCallback {
//okw


    private String serverKey = "AIzaSyCBGMz8LNPmst35x_GK50FU-tj_E8q0EDw";
    private LatLng camera = null;
    String pid = "";
    public static int oldid = -1;
    private LatLng destination = null;
    private GoogleMap mMap;
    public static ListView productsList;
    public static Context context;
    LinearLayout postnewLayout;
    boolean isGPSEnabled = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    LatLng origin;
    public static EditText name, email, mobile;
    RadioGroup payment_method;
    public static TextView total, item_total, delivery;
    TextView cancel, save, continue_b;
    private RequestQueue requestQueue;
    public static int pmi = 0;
    public static RadioButton bank, cash;
    private String[] Ryals;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        productsList = (ListView) findViewById(R.id.product_list);
        bank = (RadioButton) findViewById(R.id.by_bank);
        cash = (RadioButton) findViewById(R.id.cash);
        Ryals = AddNewOrderActivity.this.getResources().getStringArray(R.array.ryal_array);
        name = (EditText) findViewById(R.id.c_name);
        email = (EditText) findViewById(R.id.c_email);
        mobile = (EditText) findViewById(R.id.c_number);

        payment_method = (RadioGroup) findViewById(R.id.pay_method_radiogrp);

        total = (TextView) findViewById(R.id.c_total);
        item_total = (TextView) findViewById(R.id.c_itemTotal);
        delivery = (TextView) findViewById(R.id.c_delivery_charges);

        cancel = (TextView) findViewById(R.id.cancel_button);
        save = (TextView) findViewById(R.id.protection_button);
        continue_b = (TextView) findViewById(R.id.ButtonAccept);

        context = this;

        try {
            origin = new LatLng(LocationManage.Lat, LocationManage.Long);
        } catch (Exception e) {

            Toast.makeText(AddNewOrderActivity.this, "No Old Location Saved", Toast.LENGTH_SHORT).show();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

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

        Spinner layout = (Spinner) findViewById(R.id.popup_view);
        layout.setAdapter(new ListPopupAdapter(this));

        layout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView ryalTextView = (TextView) view.findViewById(R.id.ryal_textView);
                int p;
                for (p = 0; p < Ryals.length; p++) {
                    if (ryalTextView.getText().toString().compareTo(Ryals[p]) == 0)
                        break;
                }
                if (p == 0)
                    delivery.setText("20");
                else if (p == 1)
                    delivery.setText("30");
                else if (p == 2)
                    delivery.setText("40");
                else if (p == 3) {
                    delivery.setText("50");
                }
                total.setText(String.valueOf((Double.parseDouble(item_total.getText().toString())) + (Double.parseDouble(delivery.getText().toString()))));
                total.setText(String.valueOf((Double.parseDouble(item_total.getText().toString())) + (Double.parseDouble(delivery.getText().toString()))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Double itotal = 0.0;
        for (int t = 0; t < New_Orders_Activity.pList.size(); t++)
            itotal += Double.parseDouble(New_Orders_Activity.pList.get(t).getPrice());
        item_total.setText(String.valueOf(itotal));
        total.setText(String.valueOf(itotal + Double.parseDouble(delivery.getText().toString())));

        payment_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bank.isChecked()) {
                    item_total.setText("0");
                    total.setText(String.valueOf(Double.parseDouble(item_total.getText().toString()) + Double.parseDouble(delivery.getText().toString())));
                    pmi = 1;
                } else {
                    //compute total
                    double itotal = 0.0;
                    try {
                        for (int t = 0; t < New_Orders_Activity.pList.size(); t++) {
                            itotal += Double.parseDouble(New_Orders_Activity.pList.get(t).getPrice())* Double.parseDouble(New_Orders_Activity.pList.get(t).getQuantity());
                        }

                        item_total.setText(String.valueOf(itotal));
                    } catch (Exception e) {
                        Toast.makeText(AddNewOrderActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    total.setText(String.valueOf(itotal + Double.parseDouble(delivery.getText().toString())));
                    pmi = 2;
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                New_Orders_Activity.pList.clear();
                Intent i = new Intent(AddNewOrderActivity.this, CustomerRequestActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pmi == 0 || name.getText().toString().compareTo("") == 0 || email.getText().toString().compareTo("") == 0 || mobile.getText().toString().compareTo("") == 0) {
                    Toast.makeText(AddNewOrderActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (New_Orders_Activity.pList.size() == 0) {
                    Toast.makeText(AddNewOrderActivity.this, "Add at least one item to the order", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String ItemDetails;
                String item_ids = "";
                for (int t = 0; t < New_Orders_Activity.pList.size(); t++)
                    item_ids += (New_Orders_Activity.pList.get(t).getProductID() + "-" + New_Orders_Activity.pList.get(t).getQuantity() + " ");
                ItemDetails = item_ids;
                final  ProgressDialog progress = new ProgressDialog(AddNewOrderActivity.this, ProgressDialog.THEME_HOLO_DARK);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Loading...");
                progress.show();
                StringRequest request = new StringRequest(Request.Method.POST, functions.add + "addorder.php", new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            if (response.toString().compareTo("-1") != 0) {
                                New_Orders_Activity.pList.clear();
                                Toast.makeText(AddNewOrderActivity.this, "Order saved ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddNewOrderActivity.this, AddNewOrderActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(i);
                                finish();
progress.dismiss();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progress.dismiss();
                            if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })
                                        .setActionTextColor(Color.RED)

                                        .show();
                            }
                        }
                    }// in case error
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                        }
                    }
                }) {
                    //send data to server using POST
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id", HomeActivity.id);
                        hashMap.put("hash", HASH.getHash());
                        hashMap.put("delivery", delivery.getText().toString());
                        hashMap.put("pay", String.valueOf(pmi));
                        hashMap.put("price", item_total.getText().toString());
                        hashMap.put("confirm", "1");
                        hashMap.put("name", name.getText().toString());
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("number", mobile.getText().toString());
                        hashMap.put("lat", destination.latitude + "");
                        hashMap.put("lon", destination.longitude + "");
                        hashMap.put("itemlist", ItemDetails);
                        return hashMap;
                    }
                };
                try {
                    requestQueue = Volley.newRequestQueue(AddNewOrderActivity.this);
                    int socketTimeout = 3000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    requestQueue.add(request);
                } catch (Exception e) {
                    progress.dismiss();
                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                        Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .setActionTextColor(Color.RED)

                                .show();
                    }
                }

            }
        });

        continue_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(AddNewOrderActivity.this,"Activity Behind this is On Development. Please Save Order and Select it in OrderList",Toast.LENGTH_LONG).show();
                if (pmi == 0 || name.getText().toString().compareTo("") == 0 || email.getText().toString().compareTo("") == 0 || mobile.getText().toString().compareTo("") == 0) {
                    Toast.makeText(AddNewOrderActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (New_Orders_Activity.pList.size() == 0) {
                    Toast.makeText(AddNewOrderActivity.this, "Add at least one item to the order", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String ItemDetails;
                String item_ids = "";
                for (int t = 0; t < New_Orders_Activity.pList.size(); t++)
                    item_ids += (New_Orders_Activity.pList.get(t).getProductID() + "-" + New_Orders_Activity.pList.get(t).getQuantity() + " ");
                String curl = "";
                ItemDetails = item_ids;
                if (oldid != -1)
                    curl += functions.add + "changes_order_status";
                else
                    curl += functions.add + "addorder.php";
                final  ProgressDialog progress = new ProgressDialog(AddNewOrderActivity.this, ProgressDialog.THEME_HOLO_DARK);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);progress.setMessage("Loading...");
                progress.show();
                StringRequest request = new StringRequest(Request.Method.POST, curl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            if (response.toString().compareTo("-1") != 0) {
                                if (oldid != -1)
                                    oldid = -1;
                                New_Orders_Activity.pList.clear();
                                Intent i = new Intent(AddNewOrderActivity.this, PickSetActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                String resp = response.toString().replaceAll("[\n\r]", "");
                                i.putExtra("orderID", resp);
                                startActivity(i);
                                finish();
                                progress.dismiss();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progress.dismiss();
                            if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })
                                        .setActionTextColor(Color.RED)

                                        .show();}
                        }
                    }// in case error
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
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
                }) {
                    //send data to server using POST
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        if (oldid == -1) {
                            hashMap.put("id", HomeActivity.id);
                            hashMap.put("hash", HASH.getHash());
                            hashMap.put("delivery", delivery.getText().toString());
                            hashMap.put("pay", String.valueOf(pmi));
                            hashMap.put("price", item_total.getText().toString());
                            hashMap.put("confirm", "1");
                            hashMap.put("name", name.getText().toString());
                            hashMap.put("email", email.getText().toString());
                            hashMap.put("number", mobile.getText().toString());
                            hashMap.put("lat", destination.latitude + "");
                            hashMap.put("lon", destination.longitude + "");
                            hashMap.put("itemlist", ItemDetails);
                        } else {
                            hashMap.put("id", HomeActivity.id);
                            hashMap.put("hash", HASH.getHash());
                            hashMap.put("oid", oldid + "");
                        }
                        return hashMap;
                    }
                };
                try {
                    requestQueue = Volley.newRequestQueue(AddNewOrderActivity.this);
                    int socketTimeout = 3000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                   request.setRetryPolicy(policy);
                    requestQueue.add(request);
                } catch (Exception e) {
                    progress.dismiss();
                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                        Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setActionTextColor(Color.RED)

                                .show();}
                }

            }
        });


        View tajer_lap = (View) findViewById(R.id.tajer_lap_view);
        tajer_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewOrderActivity.this, Tajer_Lap_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

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
                final EditText newitem_name = (EditText) dialog.findViewById(R.id.new_item_name_et);
                final EditText newitem_price = (EditText) dialog.findViewById(R.id.new_item_price_et);
                Button add_new_item = (Button) dialog.findViewById(R.id.add_item_to_list);
                add_new_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (newitem_name.getText().toString().compareTo("") == 0) {
                            Toast.makeText(AddNewOrderActivity.this, "Item must have a name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newitem_price.getText().toString().compareTo("") == 0) {
                            Toast.makeText(AddNewOrderActivity.this, "Item must have a price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringRequest request = new StringRequest(Request.Method.POST, functions.add + "addproduct.php", new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    if (response != "-1") {
                                        pid = response;
                                        Toast.makeText(AddNewOrderActivity.this, "Product saved", Toast.LENGTH_SHORT).show();
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
                                hashMap.put("id", HomeActivity.id);
                                hashMap.put("hash", HASH.getHash());
                                hashMap.put("delivery", delivery.getText().toString());
                                hashMap.put("pay", String.valueOf(pmi));
                                hashMap.put("price", newitem_price.getText().toString());
                                hashMap.put("confirm", "1");
                                hashMap.put("name", newitem_name.getText().toString());
                                hashMap.put("email", email.getText().toString());
                                hashMap.put("number", mobile.getText().toString());
                                return hashMap;
                            }
                        };
                        try {
                            RequestQueue requestQueue1 = Volley.newRequestQueue(AddNewOrderActivity.this);
                            requestQueue1.add(request);
                        } catch (Exception e) {
                            Toast.makeText(AddNewOrderActivity.this, "Request Issue", Toast.LENGTH_SHORT).show();
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
        Log.d("Map Ready", "Map is ready");
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

            mMap.addMarker(new MarkerOptions().position(origin).title("Seller"));
            mMap.addMarker(new MarkerOptions().position(destination).title("Customer").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 3, Color.RED));

        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {

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
        final EditText input = (EditText) dialog.findViewById(R.id.new_item_name_et);
        final ExecutorService mThreadPool = Executors.newSingleThreadScheduledExecutor();
        dialog.findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            getLocationFromAddress(input.getText().toString());
                            requestDirection();
                        }
                    });

                    Toast.makeText(AddNewOrderActivity.this, input.getText().toString(), Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(AddNewOrderActivity.this, "Invalid URL or Connection Error", Toast.LENGTH_SHORT).show();
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
            String dest = secondURL + "";
            Log.d("neche", dest);
            try {
                String[] parts = dest.split("q=");

                String[] parts2 = parts[1].split("&hl");
                String part2 = parts2[0]; // 034556

                String[] latlong = part2.split(",");
                double sourceLat = Double.valueOf(latlong[0]);
                double sourceLong = Double.valueOf(latlong[1]);
                destination = new LatLng(sourceLat, sourceLong);
            } catch (Exception e) {
                String[] parts = dest.split("search/");

                String[] parts2 = parts[1].split("/data");
                String part2 = parts2[0]; // 034556

                String[] latlong = part2.split(",");
                double sourceLat = Double.valueOf(latlong[0]);
                double sourceLong = Double.valueOf(latlong[1]);
                destination = new LatLng(sourceLat, sourceLong);
            }


        } catch (IOException e) {
        }


    }


    private void addMarker(double lat, double lng, String title, int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));

        mMap.addMarker(markerOptions);
    }


}
