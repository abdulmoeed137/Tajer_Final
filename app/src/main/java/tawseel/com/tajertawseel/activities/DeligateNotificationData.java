package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DileveryGroupAdapter;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class DeligateNotificationData extends BaseActivity {
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deligate_notification_data);
        setUpContents();
    }

    private void setUpContents() {

        final TextView GroupInfoText = (TextView) findViewById(R.id.GroupInfoText) ;
        final String GroupID = getIntent().getExtras().getString("GroupID");
        requestQueue = Volley.newRequestQueue(this);
        findViewById(R.id.Reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //noinspection ConstantConditions
        final ProgressDialog progress = new ProgressDialog(DeligateNotificationData.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.setMessage("Loading...");
        progress.show();

        findViewById(R.id.Accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"DeligateAcceptRequest.php?id="+GroupID+"&hash=CCB612R&DeligateID="+DeligateHomeActivity.DeligateID,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArr=response.getJSONArray("info");

                                    for(int i=0;i<jsonArr.length();i++) {
                                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                        if (jsonObj.names().get(0).equals("success"))
                                        {
                                            if (jsonObj.getString("success").toString().equals("Taken"))
                                            {
                                                Toast.makeText(DeligateNotificationData.this,"SomeOne Already Toaken",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                                Toast.makeText(DeligateNotificationData.this,"Success. Check your Groups if its Confirmed with you ",Toast.LENGTH_SHORT).show();
                                        }else
                                        Toast.makeText(DeligateNotificationData.this,"Failed",Toast.LENGTH_SHORT).show();

                                    }
                                     finish();
                                    progress.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progress.dismiss();
                                };
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                                progress.dismiss();
                            }
                        });

                //dummy Adapter
                // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
                requestQueue.add(jsonObjectRequest);
            }
        });

        GroupInfoText.setText("********Group Details ************");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"GroupItem.php?id="+GroupID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                           int itemprice=0,pricerange=0;
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);

                                GroupInfoText.append("\nOrder No: "+(i+1));
                              GroupInfoText.append("\nName : "+jsonObj.getString("UserName"));
                                GroupInfoText.append("\nOrder Amount : "+jsonObj.getString("ItemsPrice"));
                                GroupInfoText.append("\nDelivery Amount : "+jsonObj.getString("PriceRange"));
                                GroupInfoText.append("\nProducts Quantity : "+jsonObj.getString("OrderMember"));
                                itemprice+=Integer.parseInt(jsonObj.getString("ItemsPrice"));
                                pricerange+=Integer.parseInt(jsonObj.getString("PriceRange"));
                                int x=Integer.parseInt(jsonObj.getString("ItemsPrice"))+Integer.parseInt(jsonObj.getString("PriceRange"));
                                GroupInfoText.append("\nOrder Amount : "+x+"\n**************************************\n");
                            }
                            GroupInfoText.append("\nTotal Group Amount : "+(itemprice+pricerange));

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
    }
}
