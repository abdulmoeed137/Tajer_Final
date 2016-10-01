package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
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
import tawseel.com.tajertawseel.adapters.FinancialRequestAdapter;

/**
 * Created by Junaid-Invision on 8/13/2016.
 */
public class FinancialRequestActivity extends BaseActivity {

    ListView mListView;
    String Groupid,tprice,tdelivery;
    TextView gid,ncustomers,date,itemprice,deliveryprice,dname;
    RatingBar drating;
    private RequestQueue requestQueue;
    ArrayList<FinancialHistoryData> data=new ArrayList<FinancialHistoryData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_request_history);
        tprice=getIntent().getExtras().getString("totalitem");
        tdelivery=getIntent().getExtras().getString("totald");
        Groupid=getIntent().getExtras().getString("gid");
setUpToolbar();
        setUpComponents();

    }

    private void setUpComponents() {


        mListView = (ListView)findViewById(R.id.financial_history_list_view);
        gid=(TextView)findViewById(R.id.fh_GroupID);
        ncustomers=(TextView)findViewById(R.id.fh_NoOfCustomers);
        date=(TextView)findViewById(R.id.fh_time);
        itemprice=(TextView)findViewById(R.id.fh_ItemPrice);
        deliveryprice=(TextView)findViewById(R.id.fh_PriceRange);
        dname=(TextView)findViewById(R.id.fh_DeligateName);
        drating=(RatingBar)findViewById(R.id.fh_ratingbar);
        final ProgressDialog progress = new ProgressDialog(FinancialRequestActivity.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.setMessage("Loading...");
        progress.show();

        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"financialhistory.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    date.setText(jsonArr.getJSONObject(0).getString("DeliveryDate"));
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FinancialHistoryData temp=new FinancialHistoryData();
                        temp.setDname(jsonObj.getString("UserName"));
                        temp.setDeliveryprice(jsonObj.getString("PriceRange"));
                        temp.setItemprice(jsonObj.getString("ItemsPrice"));
                        temp.setNitems(jsonObj.getString("Items"));
                        data.add(temp);
                    }
                    gid.setText(Groupid);
                    ncustomers.setText(data.size()+"");
                    itemprice.setText(tprice);
                    deliveryprice.setText(tdelivery);
                    mListView.setAdapter(new FinancialRequestAdapter(FinancialRequestActivity.this,data));
                    progress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.dismiss();
                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                        Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setActionTextColor(Color.RED)

                                .show();}
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),"Internet Connection Error", Toast.LENGTH_SHORT).show();
                progress.dismiss();
                if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                            .setAction("Reload", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                    startActivity(getIntent());
                                }
                            })
                            .setActionTextColor(Color.RED)

                            .show();}
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("gid",Groupid);
                hashMap.put("id",HomeActivity.id);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(FinancialRequestActivity.this);
            int socketTimeout = 3000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            requestQueue.add(request);
        }
        catch (Exception error)
        {
           // Toast.makeText(FinancialRequestActivity.this,"Internet Connection Error",Toast.LENGTH_SHORT).show();
            if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setActionTextColor(Color.RED)

                        .show();}
        }
        setUpToolbar();
    }



    public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.financial_history_of_Request));
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

}
