package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/12/2016.
 *
 *
 * mm
 */
public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    ProgressDialog progress;
    public static String LoginID=null,DeligateID=null;
    EditText email_ET, pass_ET;
    private RequestQueue requestQueue;
    private static final String URL = functions.add + "login.php";
    private StringRequest request;
    public static String uname;
    public static String email;
//    LocationManager locationManager;
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters
//
//    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show();
//
//
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
//                this);
//        try {
//            LocationManage.Lat = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLatitude();
//            LocationManage.Long = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLongitude();
//        }
//        catch (Exception e)
//        {
//            Log.d("Location",e.toString());
//        }
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

                    final  ProgressDialog progress = new ProgressDialog(LoginActivity.this, ProgressDialog.THEME_HOLO_DARK);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setMessage("Loading...");
                    progress.show();


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
                                    try {
                                        SharedPreferences settings;
                                        SharedPreferences.Editor editor;
                                        settings =LoginActivity.this.getSharedPreferences("deligate", Context.MODE_PRIVATE); //1
                                        editor = settings.edit(); //2

                                        editor.putString("id2",null); //3
                                        editor.commit(); //4
                                    }catch ( Exception e)
                                    {

                                    }
                                    SharedPreferences settings;
                                    SharedPreferences.Editor editor;
                                    settings = LoginActivity.this.getSharedPreferences("tajer", Context.MODE_PRIVATE); //1
                                    editor = settings.edit(); //2

                                    editor.putString("id",LoginID); //3
                                    editor.putString("email",email);
                                    editor.putString("uname",uname);
                                    editor.commit(); //4
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (jsonObject.names().get(0).equals("deligate")){
                                    uname=jsonObject.getString("uname");
                                    DeligateID= jsonObject.getString("deligate");
                                    //if success
                                    try {
                                        SharedPreferences settings2;
                                        SharedPreferences.Editor editor2;

                                        settings2 = LoginActivity.this.getSharedPreferences("tajer", Context.MODE_PRIVATE);
                                        editor2 = settings2.edit();
                                        editor2.putString("id", null); //3
                                        editor2.commit();
                                    }
                                    catch (Exception e)
                                    {

                                    }
                                    SharedPreferences settings;
                                    SharedPreferences.Editor editor;
                                    settings = LoginActivity.this.getSharedPreferences("deligate", Context.MODE_PRIVATE); //1
                                    editor = settings.edit(); //2

                                    editor.putString("id2",DeligateID); //3
                                    editor.putString("email",email);
                                    editor.putString("Did",DeligateID);;
                                    editor.commit(); //4
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
                           // Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
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
                    int socketTimeout = 3000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    requestQueue.add(request);

                }



            }
        });
    }


//    @Override
//    public void onLocationChanged(Location location) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationManage.Lat = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLatitude();
//        LocationManage.Long=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getLongitude();
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
////        Toast.makeText(this, "Location is Off!", Toast.LENGTH_SHORT).show();
////        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                // NO need to show the dialog;

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    //failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Location Enabled", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "Location is Disabled. Please Enable Location From Setting", Toast.LENGTH_LONG).show();
            }

        }}
}