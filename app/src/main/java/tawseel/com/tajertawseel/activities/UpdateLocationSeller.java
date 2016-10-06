package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by AbdulMoeed on 8/15/2016.
 */
public class UpdateLocationSeller extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    RequestQueue requestQueue;
    String id;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Location mLastLocation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences settings;

        settings = this.getSharedPreferences("tajer", Context.MODE_PRIVATE); //1
        id = settings.getString("id", null);

        buildGoogleApiClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // Thread t = new Thread(new ThreadThis(startId));
        //       t.start();
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    ExecutorService mThreadPool = Executors.newSingleThreadScheduledExecutor();
    boolean flag = true;

    @Override
    public void onLocationChanged(Location location) {
        LocationManage.Lat = location.getLatitude();
        LocationManage.Long = location.getLongitude();
        if (flag) {
            flag = false;
            final Location finalLocation = location;
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {

                    }

                    UpdateDeligateLocation(finalLocation);

                }
            });
            flag = true;
        }
    }

    public void UpdateDeligateLocation(final Location location) {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest request;

        request = new StringRequest(Request.Method.POST, functions.add + "UpdateSellerLocation.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            // Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("failed"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc", error.toString());
                Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", id);
                hashMap.put("hash", "CCB612R");
                hashMap.put("lat", location.getLatitude() + "");
                hashMap.put("lon", location.getLongitude() + "");
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval((60000*10)); // Update location every second
        mLocationRequest.setFastestInterval((60000*10));
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
           LocationManage.Lat = mLastLocation.getLatitude();
           LocationManage.Long= mLastLocation.getLongitude();
          UpdateDeligateLocation(mLastLocation );

        }




    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
