package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tawseel.com.tajertawseel.R;

/**
 * Created by AbdulMoeed on 8/15/2016.
 */
public class UpdateLocation extends Service implements LocationListener {
    LocationManager locationManager;
    boolean flag2 = true;
    RequestQueue requestQueue;
String id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences settings;

        settings = this.getSharedPreferences("deligate", Context.MODE_PRIVATE); //1
        id = settings.getString("id2", null);}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 0;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1,
                this);
        Toast.makeText(UpdateLocation.this, "Start Serice", Toast.LENGTH_SHORT).show();
        // Thread t = new Thread(new ThreadThis(startId));
        //       t.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
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
        locationManager.removeUpdates(this);
        super.onDestroy();
    }

    ExecutorService mThreadPool = Executors.newSingleThreadScheduledExecutor();
    boolean flag = true;

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
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(flag){
            flag=false;
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
            flag=true;
        }
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
    public void UpdateDeligateLocation(final Location location)
    {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest request;

        request = new StringRequest(Request.Method.POST, functions.add+"UpdateDeligateLocation.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                          //  Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                       /*    if (flag2){
                            if (jsonObject.names().get(1).equals("Alert"))
                            {
                                flag2=false;
                                Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UpdateLocation.this,DeligateHomeActivity.class);
                                Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                PendingIntent pendingIntent = PendingIntent.getActivity(UpdateLocation.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(UpdateLocation.this)
                                        .setAutoCancel(true)
                                        .setContentTitle("Tawseel")
                                        .setContentText("Agya")
                                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                                        .setSound(defaultSoundUri)
                                        .setContentIntent(pendingIntent);

                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                manager.notify(0,builder.build());
                            }

                            }*/

                        } else {
                           // Toast.makeText(getApplicationContext(),jsonObject.getString("failed"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();

                }

            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc",error.toString());
                Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",id);
                hashMap.put("hash", "CCB612R");
                hashMap.put("lat",location.getLatitude()+"");
                hashMap.put("lon",location.getLongitude()+"");
                return hashMap;
            }
        };
        requestQueue.add(request);
}

}
