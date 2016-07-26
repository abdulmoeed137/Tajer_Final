package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.CustomerRequestAdapter;

/**
 * Created by Junaid-Invision on 7/3/2016.
 *
 * Edited by M Monis on 7/21/2016
 */
public class CustomerRequestActivity extends BaseActivity {

    ListView mListView;
    ImageView postRequestButton;
    TextView oCount;
    private RequestQueue requestQueue;
    private static final String URL = functions.add+"orders.php";
    private StringRequest request;
    ArrayList<Customer_request_item_data> list=new ArrayList<Customer_request_item_data>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);
        oCount=(TextView)findViewById(R.id.req_countText);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainobj=new JSONObject(response);
                    JSONArray jsonArr=mainobj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                            Customer_request_item_data data = new Customer_request_item_data();
                            data.setOrderID(jsonObj.getString("OrderID"));
                            data.setName(jsonObj.getString("UserName"));
                            data.setEmail(jsonObj.getString("Email"));
                            data.setNumber(jsonObj.getString("Mobile"));
                            data.setNo_of_items(jsonObj.getString("Items"));
                            list.add(data);
                    }
                    oCount.setText(list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }// in case error
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
                return hashMap;
            }
        };

        try{
            requestQueue= Volley.newRequestQueue(CustomerRequestActivity.this);
            requestQueue.add(request);}
        catch (Exception e)
        {
            Toast.makeText(CustomerRequestActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }

        setUpToolbar();
        setUpComponents();
    }

    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option1));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setUpComponents()
    {
        mListView = (ListView)findViewById(R.id.customer_request_listView);
        mListView.setAdapter(new CustomerRequestAdapter(this,list));
        postRequestButton = (ImageView)findViewById(R.id.post_your_request_button);
        setUpListeners();
    }

    public void setUpListeners ()
    {
        postRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i  = new Intent (CustomerRequestActivity.this, AddNewOrderActivity.class);

                startActivity(i);

            }
        });
    }
}