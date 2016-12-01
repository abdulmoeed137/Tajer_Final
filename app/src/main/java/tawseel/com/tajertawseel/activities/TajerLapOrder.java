package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.CustomerRequestAdapter;
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;
import tawseel.com.tajertawseel.adapters.TajerLapOrderAdapter;
import tawseel.com.tajertawseel.adapters.ViewHolder;
import tawseel.com.tajertawseel.adapters.pick_dummy_adapter;
import android.widget.AdapterView.OnItemClickListener;

public class TajerLapOrder extends BaseActivity {

    ListView mListView;
    private RequestQueue requestQueue;
    String GroupID=null;
    boolean flag=false;
    private StringRequest request;
    ArrayList<Customer_request_item_data> list=new ArrayList<Customer_request_item_data>();
    RequestQueue requestQueue2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tajer_lap_order);
        setUpToolbar();
        setUpComponents();
        requestQueue= Volley.newRequestQueue(this);

        requestQueue2= Volley.newRequestQueue(this);


//        final ProgressDialog progress = ProgressDialog.show(new ContextThemeWrapper(CustomerRequestActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar ), "Loading",
//                "Please Wait..");
        final  ProgressDialog progress = new ProgressDialog(TajerLapOrder.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"order_tajerlap.php?id="+HomeActivity.id+"&hash="+HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");

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
                            mListView.setAdapter(new TajerLapOrderAdapter(TajerLapOrder.this,list));
                            ExecutorService mThreadPool = Executors.newSingleThreadScheduledExecutor();
                            mThreadPool.execute(new Runnable() {
                                @Override
                                public void run() {

                                    progress.dismiss();

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                            if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                        .setAction("Reload", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(getIntent());finish();
                                            }
                                        })
                                        .setActionTextColor(Color.RED)

                                        .show();}
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        progress.dismiss();
                        if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(getIntent());finish();
                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();}
                    }
                });

        //dummy Adapter
        // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
mListView.setOnItemClickListener(new OnItemClickListener() {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        //Toast.makeText(TajerLapOrder.this,getIntent().getStringExtra("GroupID")+"Order id : "+list.get(i).getOrderID(),Toast.LENGTH_SHORT ).show();
        final  ProgressDialog progress = new ProgressDialog(TajerLapOrder.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        request = new StringRequest(Request.Method.POST, functions.add+"TajerLapAddOrderGroup.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Order Added to Group", Toast.LENGTH_SHORT).show();
                            ((TajerLapOrderAdapter)mListView.getAdapter()).updateList(i);


                        } else {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        progress.dismiss();
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                }


            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                hashMap.put("GroupID",getIntent().getStringExtra("GroupID"));
                hashMap.put("OrderID", list.get(i).getOrderID()+"");
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }
});

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    public void setUpComponents()
    {
        mListView = (ListView)this.findViewById(R.id.customer_request_listView);
    findViewById(R.id.BtnNext).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            startActivity(new Intent(TajerLapOrder.this,PostOrderAddGroupActivity.class));
        }
    });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListView= null;
        requestQueue=null;
        request=null;
        list=null;
        System.gc();
    }
}