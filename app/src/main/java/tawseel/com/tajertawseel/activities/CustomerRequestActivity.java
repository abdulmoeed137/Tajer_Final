package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;

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
        setUpToolbar();
        setUpComponents();
        requestQueue= Volley.newRequestQueue(this);
        oCount=(TextView)findViewById(R.id.request_count);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/orders.php?id="+HomeActivity.id+"&hash="+HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
oCount.setText(jsonArr.length()+"");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);

                                Customer_request_item_data item = new Customer_request_item_data();
                                item.setPriceRange(jsonObj.getString("PriceRange"));
                                item.setCustomerEmail(jsonObj.getString("Email"));
                                item.setCustomerName(jsonObj.getString("UserName"));
                                item.setCustomerPhone(jsonObj.getString("Mobile"));
                                item.setItemsPrice(jsonObj.getString("ItemsPrice"));
                                item.setPayMethod(jsonObj.getString("PayMethod"));
                                item.setOrderProductQuantity(jsonObj.getString("OrderMember"));
                                item.setOrderID(jsonObj.getString("OrderID"));
                                item.setLatitude(jsonObj.getString("Latitude"));
                                item.setLongitude(jsonObj.getString("Longitude"));
                                item.setID(jsonObj.getString("ID"));
                                list.add(item);

                            }

                            mListView.setAdapter(new CustomerRequestAdapter(CustomerRequestActivity.this,list));
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