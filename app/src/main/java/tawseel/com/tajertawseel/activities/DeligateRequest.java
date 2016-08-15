package tawseel.com.tajertawseel.activities;

import android.app.Service;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AbdulMoeed on 7/26/2016.
 */
public class DeligateRequest extends Service {
    RequestQueue requestQueue;
    private StringRequest request;
    String tmp = "";
    boolean keepRunning = true;
    Response.Listener<JSONObject> response;
    JsonObjectRequest jsonRequest;
    int service_id;

    public class ThreadThis implements Runnable {

        int service_id;

//        ThreadThis(int service_id) {
//            this.service_id = service_id;
//
//        }

        @Override
        public void run() {

//            Toast.makeText(DeligateRequest.this,"Checking If Any Request", Toast.LENGTH_SHORT).show();

//            while (keepRunning){
//                try {
//                    Thread.sleep(5000);
//                    Log.d("ServiceCheck", keepRunning + "");
//
//
//
//                    //dummy Adapter
//                    // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
//                  // jsonObjectRequest.setTag("request");
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


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
        requestQueue = Volley.newRequestQueue(DeligateRequest.this);
        response =  new Response.Listener<JSONObject>() {
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

                            //commenting for testing
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
                    if(keepRunning != false)

                    {
                        requestQueue.add(jsonRequest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ;
            }
        };
        service_id = startId;
        //Thread t = new Thread(new ThreadThis());
        //Log.d("Moeed", "finish2");
        //t.start();

        jsonRequest = new JsonObjectRequest(Request.Method.GET, functions.add + "DeligateRequest.php?id=" + WaitingForAcceptanceActivity.GrpID,response
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley", "onErrorResponse: " + error);
                    }
                });
        requestQueue.add(jsonRequest);
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
