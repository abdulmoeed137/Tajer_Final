package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/12/2016.
 *
 *
 * mm
 */
public class LoginActivity extends BaseActivity implements LocationListener{

    ProgressDialog progress;
    public static String LoginID,DeligateID;
    EditText email_ET, pass_ET;
    private RequestQueue requestQueue;
    private static final String URL = functions.add + "login.php";
    private StringRequest request;
    public static String uname;
    public static String email;
    LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE}, 100);
            }

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
        try {
            LocationManage.Lat = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLatitude();
            LocationManage.Long = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLongitude();
        }
        catch (Exception e)
        {
            Log.d("Location",e.toString());
        }
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.splash_logo3).into(logo);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        email_ET = (EditText) findViewById(R.id.email);
        pass_ET = (EditText) findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(this);

        assert loginButton != null;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              email = email_ET.getText().toString();
                final String pass = pass_ET.getText().toString();
                if (functions.isEmailTrue(email, getApplicationContext()) || functions.isPasswordTrue(pass,getApplicationContext())) {
                    progress = ProgressDialog.show(LoginActivity.this, "Loading",
                            "Please Wait..", true);
                   request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        //if response
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jsonObject.names().get(0).equals("tajer")) {
                                    LoginID=jsonObject.getString("tajer");
                                    uname=jsonObject.getString("uname");
                                    Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                                    //if success
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (jsonObject.names().get(0).equals("deligate")){
                                    uname=jsonObject.getString("uname");
                                    DeligateID= jsonObject.getString("deligate");
                                    //if success
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, DeligateHomeActivity.class);
                                    i.putExtra("DeligateID",DeligateID);
                                    i.putExtra("flag",true);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                progress.dismiss();
                                e.printStackTrace();
                            }


                        }// in case error
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        //send data to server using POST
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();

                            hashMap.put("email", email);
                            hashMap.put("pass", pass);
                            hashMap.put("hash", HASH.getHash());
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);}



            }
        });
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
        Toast.makeText(this, "Location is Off!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}