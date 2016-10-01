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
LatLng From,To;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
Toast.makeText(ComfirmationActivity.this,"Getting Your Location. Please Wait",Toast.LENGTH_LONG).show();

        setUpToolbar();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        findViewById(R.id.ButtonConfirmationTajer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDialogue(ComfirmationActivity.this);
            }
        });
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

        double TajerLatitude,TajerLongitude;
        TajerLatitude = Double.parseDouble(getIntent().getExtras().getString("TajerLatitude"));
        TajerLongitude=Double.parseDouble(getIntent().getExtras().getString("TajerLongitude"));
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
         From = new LatLng(TajerLatitude,TajerLongitude);
       To = new LatLng(LocationManage.Lat,LocationManage.Long);

        Double distance = SphericalUtil.computeDistanceBetween(From, To);
        NumberFormat format = new DecimalFormat("##.##");
        Marker marker =  mMap.addMarker(new MarkerOptions().position(From).title("Tajer : "+format.format(distance/1000)+" KM away"));

        marker.showInfoWindow();

        mMap.addMarker(new MarkerOptions().position(To).title("You"));

        new PathRequest().makeUrl(From.latitude,From.longitude,To.latitude,To.longitude,ComfirmationActivity.this,mMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(From));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(From,15));




    }
    public void showNotificationDialogue(Context c) {
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.confirmation_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.findViewById(R.id.ButtonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText code = (EditText)dialog.findViewById(R.id.Code);
                if (code.getText().toString().equals(getIntent().getExtras().getString("ConfirmationCode")))
                    RunVolley("2");
                else
                    Toast.makeText(getApplicationContext(),"Wrong Code",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.findViewById(R.id.ButtonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunVolley("0");
            }
        });
        // lp.copyFrom(c.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
//        ListView lv = (ListView) dialog.findViewById(R.id.ordersList);
//        lv.setAdapter(new OrdeDialogueAdapter(c));
        dialog.show();
    }
    void RunVolley (final String value)
    {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        final String formattedDate = df.format(c.getTime());
        final String formattedTime = sdf.format(c.getTime());
        //  Toast.makeText(ComfirmationActivity.this,formattedDate,Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request;
//Toast.makeText(ComfirmationActivity.this,getIntent().getExtras().getString("GroupID")+"\n"+value+"\n"+formattedDate+"\n"+formattedTime,Toast.LENGTH_SHORT).show();
        final  ProgressDialog progress = new ProgressDialog(ComfirmationActivity.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.setMessage("Loading...");
        progress.show();

        request = new StringRequest(Request.Method.POST, functions.add+"DeligateTajerConfirm.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                            if (value.equals("0"))
                                finish();
                            else
                            {
                                Intent i = new Intent(ComfirmationActivity.this,ConfirmTajerActivity.class);
                                i.putExtra("GroupID",getIntent().getExtras().getString("GroupID"));
                                startActivity(i);
                                finish();
                            }
                            progress.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("failed"),Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                        }
                    } catch (JSONException e) {

                        Toast.makeText(getApplicationContext(), "Internet Connection Error",  Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc",error.toString());
                Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                progress.dismiss();

            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",getIntent().getExtras().getString("GroupID"));
                hashMap.put("hash", "CCB612R");
                hashMap.put("StatusCode",value);
                hashMap.put("Date",formattedDate);
                hashMap.put("Time",formattedTime);
                return hashMap;
            }
        };
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ComfirmationActivity.this,DeligateHomeActivity.class));
        finish();
    }
}