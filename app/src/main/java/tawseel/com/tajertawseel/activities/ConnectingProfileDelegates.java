package tawseel.com.tajertawseel.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DelegatesQuestionAdapter;

/**
 * Created by Junaid-Invision on 7/30/2016.
 */
public class ConnectingProfileDelegates extends BaseActivity {


    private RequestQueue requestQueue;
    String id="";
    TextView main_name;
    TextView name;
    TextView car;
    TextView num;
    TextView model;
    TextView contact;
    TextView delivers;
    TextView reviews;
    TextView date;
    TextView time;
    RatingBar rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting_profile_delegates);
        Bundle extras = getIntent().getExtras();
         main_name = (TextView) findViewById(R.id.mname);
         name = (TextView) findViewById(R.id.profile_name);
         car = (TextView) findViewById(R.id.profile_car);
         num = (TextView) findViewById(R.id.profile_carnum);
         model = (TextView) findViewById(R.id.profile_model);
         contact = (TextView) findViewById(R.id.profile_contact);
         delivers = (TextView) findViewById(R.id.profile_delivers);
        reviews = (TextView) findViewById(R.id.profile_reviews);
        rating=(RatingBar)findViewById(R.id.profile_ratingbar);
        date=(TextView)findViewById(R.id.pdate);
        time=(TextView) findViewById(R.id.ptime);

        if (extras != null) {
            String idt[]=extras.getString("DeligateID").split(" ");
            if(idt[0]!=null)
                id=idt[0];
            else
            id=extras.getString("DeligateID");
            if(idt[1]!=null)
                date.setText(idt[1]);
            if(idt[2]!=null)
                time.setText(idt[2]);
        }

        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"delegates.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        main_name.setText(jsonObj.getString("Name"));
                        name.setText(jsonObj.getString("Name"));
                        car.setText(jsonObj.getString("CarBrand"));
                        num.setText(jsonObj.getString("CarNo"));
                        model.setText(jsonObj.getString("CarModel"));
                        contact.setText(jsonObj.getString("Contact"));
                        delivers.setText(jsonObj.getString("delivers"));
                        reviews.setText(jsonObj.getString("delivers"));
                        Float idelivers= Float.parseFloat(jsonObj.getString("delivers"));
                        rating.setRating(idelivers);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",HomeActivity.id);
                hashMap.put("hash",HASH.getHash());
                hashMap.put("did",id);
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(ConnectingProfileDelegates.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(ConnectingProfileDelegates.this,"Internet Connection Error",Toast.LENGTH_SHORT).show();
        }
        setUpToolbar();
    }


    private void setUpToolbar() {
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
