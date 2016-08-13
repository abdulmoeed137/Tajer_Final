package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DeliveredNowAdapter;

/**
 * Created by Junaid-Invision on 7/30/2016.
 */
public class ConnectingProfileDelegates extends BaseActivity {
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting_profile_delegates);
        requestQueue= Volley.newRequestQueue(this);
        setUpComponents();
        setUpToolbar();
    }

    private void setUpComponents() {
        final TextView NameHeader = (TextView)findViewById(R.id.DeligateName);
       final  TextView NameDeligate = (TextView)findViewById(R.id.DeligateName2);
        final  TextView Car = (TextView)findViewById(R.id.Car);
        final  TextView Model = (TextView)findViewById(R.id.Model);
        final  TextView CarNum = (TextView)findViewById(R.id.CarNumber);
        final  TextView Contact = (TextView)findViewById(R.id.Contact);
        ImageView BtnCall = (ImageView)findViewById(R.id.BtnCall) ;
        BtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+Contact.getText()));
                if (ActivityCompat.checkSelfPermission(ConnectingProfileDelegates.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ConnectingProfileDelegates.this,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},100);
                    }

                    return;
                }
                startActivity(callIntent);

            }
        });

        /////

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/DeligateInfo.php?id="+getIntent().getExtras().getString("DeligateID"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");

                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                 NameHeader.setText(jsonObj.getString("Name"));
                                NameDeligate.setText(jsonObj.getString("Name"));
                                Car.setText(jsonObj.getString("CarBrand"));
                                Model.setText(jsonObj.getString("CarModel"));
                                CarNum.setText(jsonObj.getString("CarNo"));
                                Contact.setText(jsonObj.getString("Contact"));



                            }

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


    private void setUpToolbar () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        // title.setText(getString(R.string.connecting_profile_delegates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);

    }
}
