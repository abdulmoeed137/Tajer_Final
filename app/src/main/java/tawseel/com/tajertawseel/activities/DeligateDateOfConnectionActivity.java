package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import tawseel.com.tajertawseel.adapters.DateOfConnectionsAdapter;
import tawseel.com.tajertawseel.adapters.DelegatesDateOfConnectionAdapter;

/**
 * Created by Junaid-Invision on 8/18/2016.
 */
public class DeligateDateOfConnectionActivity extends BaseActivity {

    ListView mLisView;
    private ImageView deleteIcon;
    private CustomBoldTextView title;
    private int selectedCount = 0;
    private ArrayList<DateOfConnectionsData> data=new ArrayList<>();
    private RequestQueue requestQueue;
    String date="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_connections);

        setUpToolbar();
        setupComponents();
    }

    private void setupComponents() {
        mLisView = (ListView)findViewById(R.id.connectionsListView);
        //mLisView.setAdapter(new DelegatesDateOfConnectionAdapter(this));
        final  ProgressDialog progress = new ProgressDialog(DeligateDateOfConnectionActivity.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"sdelivers.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        if(i==0)
                        {
                            DateOfConnectionsData tdata1=new DateOfConnectionsData();
                            tdata1.setTitle(jsonObj.getString("DeliveryDate"));
                            data.add(tdata1);

                            date=jsonObj.getString("DeliveryDate");
                            DateOfConnectionsData tdata=new DateOfConnectionsData();
                            tdata.setGid(jsonObj.getString("GroupID"));
                            tdata.setDate(jsonObj.getString("DeliveryDate"));
                            tdata.setGname(jsonObj.getString("name"));
                            tdata.setTime(jsonObj.getString("Deliverytime"));
                            tdata.setTitle("");
                            tdata.setDname(jsonObj.getString("UserName"));
                            tdata.setDelivers(jsonObj.getString("orders"));
                            tdata.setStars(jsonObj.getString("delivers"));
                            data.add(tdata);

                        }
                        else
                        {
                            if(date.compareTo(jsonObj.getString("DeliveryDate"))!=0)
                            {
                                DateOfConnectionsData tdata1=new DateOfConnectionsData();
                                String[] datearr=jsonObj.getString("DeliveryDate").split(" ");
                                tdata1.setTitle(datearr[1]+" "+datearr[2]);
                                data.add(tdata1);

                                date=jsonObj.getString("DeliveryDate");
                                DateOfConnectionsData tdata=new DateOfConnectionsData();
                                tdata.setGid(jsonObj.getString("GroupID"));
                                tdata.setDate(jsonObj.getString("DeliveryDate"));
                                tdata.setGname(jsonObj.getString("name"));
                                tdata.setTime(jsonObj.getString("Deliverytime"));
                                tdata.setTitle("");
                                tdata.setDname(jsonObj.getString("UserName"));
                                tdata.setDelivers(jsonObj.getString("orders"));
                                tdata.setStars(jsonObj.getString("delivers"));
                                data.add(tdata);

                            }
                            else
                            {
                                DateOfConnectionsData tdata=new DateOfConnectionsData();
                                tdata.setGid(jsonObj.getString("GroupID"));
                                tdata.setDate(jsonObj.getString("DeliveryDate"));
                                tdata.setGname(jsonObj.getString("name"));
                                tdata.setTime(jsonObj.getString("Deliverytime"));
                                tdata.setTitle("");
                                tdata.setDname(jsonObj.getString("UserName"));
                                tdata.setDelivers(jsonObj.getString("orders"));
                                tdata.setStars(jsonObj.getString("delivers"));
                                data.add(tdata);
                            }
                        }
                    }
                    progress.dismiss();
                    mLisView.setAdapter(new DelegatesDateOfConnectionAdapter(DeligateDateOfConnectionActivity.this,data));
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

                                .show();
                };}

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
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
                hashMap.put("id",DeligateHomeActivity.DeligateID);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(DeligateDateOfConnectionActivity.this);
            int socketTimeout = 3000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
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
        }

mLisView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.container);

        if(layout!=null)
        {
            ImageView TickView = (ImageView)view.findViewById(R.id.tick_view);

            if(TickView.getVisibility() == View.INVISIBLE)
            {
                TickView.setVisibility(View.VISIBLE);
                layout.setBackgroundColor(getResources().getColor(R.color.grey));
                selectedCount = selectedCount+1;
                title.setText(""+selectedCount);
                if(deleteIcon.getVisibility() == View.INVISIBLE)
                {
                    deleteIcon.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                TickView.setVisibility(View.INVISIBLE);
                layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                selectedCount = selectedCount-1;

                title.setText(""+selectedCount);
                if(selectedCount == 0)
                {
                    title.setText(getString(R.string.drawer_option3));
                    deleteIcon.setVisibility(View.INVISIBLE);
                }

            }

        }
        return false;
    }
});

        mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

;
            }
        });
    }

    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option3));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
}
