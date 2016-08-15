package tawseel.com.tajertawseel.activities;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tawseel.com.tajertawseel.R;

/**
 * Created by AbdulMoeed on 7/26/2016.
 */
public class DeligateRequest extends Service {
    RequestQueue requestQueue;
    private StringRequest request;
    String tmp = "";
    boolean keepRunning = true;

    public class ThreadThis implements Runnable {

        int service_id;

        ThreadThis(int service_id) {
            this.service_id = service_id;

        }

        @Override
        public void run() {

//            Toast.makeText(DeligateRequest.this,"Checking If Any Request", Toast.LENGTH_SHORT).show();

            while (keepRunning){
                try {
                    Thread.sleep(5000);
                    Log.d("ServiceCheck", keepRunning + "");

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, functions.add + "DeligateRequest.php?id=" + WaitingForAcceptanceActivity.GrpID,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        JSONArray jsonArr = response.getJSONArray("info");
                                        for (int i = 0; i < jsonArr.length(); i++) {
                                            final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                            if (!jsonObj.getString("status").isEmpty() && !jsonObj.getString("status").equals("") && !jsonObj.getString("status").equals("0")) {
                                                keepRunning = false;
                                                tmp = jsonObj.getString("status");


                                                keepRunning = false;
                                                Log.d("ServiceCheckAnder", keepRunning + "");


                                                // functions.RequestDeligateID=jsonObj.getString("status");

                                                Toast.makeText(DeligateRequest.this, "Deligate Accepted" + keepRunning, Toast.LENGTH_SHORT).show();
                                                Intent it = new Intent(DeligateRequest.this, ChooseDelegateActivity.class);

                                                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                it.putExtra("DeligateID", jsonObj.getString("status") + "");
                                                it.putExtra("Name", jsonObj.getString("Name") + "");
                                                it.putExtra("CarNo", jsonObj.getString("CarNo") + "");
                                                it.putExtra("CarModel", jsonObj.getString("CarModel") + "");
                                                it.putExtra("Latitude", jsonObj.getString("Latitude"));
                                                it.putExtra("Longitude", jsonObj.getString("Longitude"));
                                                it.putExtra("CarBrand", jsonObj.getString("CarBrand"));
                                                it.putExtra("Contact", jsonObj.getString("Contact"));
                                                getApplication().startActivity(it);
                                                stopSelf(service_id);
                                                WaitingForAcceptanceActivity.c.finish();
                                                break;


                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ;
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Volley", "onErrorResponse: " + error);
                                }
                            });

                    //dummy Adapter
                    // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));

                    requestQueue.add(jsonObjectRequest);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


        }
    }

}

 @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        keepRunning = true;
        Thread t = new Thread(new ThreadThis(startId));
        Log.d("Moeed", "finish2");
        t.start();
        Log.d("Moeed", "finish");
        return START_STICKY;
    }
    @Override
    public void onCreate() {
        requestQueue = Volley.newRequestQueue(DeligateRequest.this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
