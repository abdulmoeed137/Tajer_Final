package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import tawseel.com.tajertawseel.adapters.DelegatesQuestionAdapter;
import tawseel.com.tajertawseel.adapters.TradersFavouriteAdapter;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class TradersFavouriteActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView listView;
    private CustomBoldTextView title;
    private ImageView deleteIcon;
    private boolean longClick  = false;
    private int ItemCount = 0;
    private ArrayList<FavouriteSellerItemData> data=new ArrayList<FavouriteSellerItemData>();
    private RequestQueue requestQueue;
    TextView tcount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_favourite);
        setUpToolbar();
        setUpComponent();
    }

    private void setUpComponent() {

        tcount=(TextView)findViewById(R.id.fs_grp_count);
        listView = (ListView) findViewById(R.id.listView);
        //listView.setAdapter(new TradersFavouriteAdapter(this));
        final ProgressDialog progress = ProgressDialog.show(TradersFavouriteActivity
                .this, "Loading",
                "Please Wait..");
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(functions.bg)));
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"favsellers.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FavouriteSellerItemData tdata=new FavouriteSellerItemData();
                        tdata.setId(jsonObj.getString("TajerID"));
                        tdata.setName(jsonObj.getString("UserName"));
                        tdata.setNdelivers(jsonObj.getString("delivers"));
                        Float idelivers= Float.parseFloat(jsonObj.getString("delivers"));
                        tdata.setStars(String.valueOf(idelivers));
                        tdata.setDate(jsonObj.getString("DeliveryDate"));
                        tdata.setTime(jsonObj.getString("Deliverytime"));
                        data.add(tdata);
                    }
                    tcount.setText(data.size()+"");
                    progress.hide();
                    listView.setAdapter(new TradersFavouriteAdapter(TradersFavouriteActivity.this,data));
                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.hide();
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
                progress.hide();
                Log.d("Volley",error.toString());
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
            requestQueue= Volley.newRequestQueue(TradersFavouriteActivity.this);
            int socketTimeout = 3000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(TradersFavouriteActivity.this,"Internet Connection Error",Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!longClick) {
                    Intent intent = new Intent(TradersFavouriteActivity.this, DealerProfileActivity.class);
                    intent.putExtra("TajerID",data.get(position).getId()+" "+data.get(position).getDate()+" "+data.get(position).getTime());
                    startActivity(intent);
                }
                else
                {
                    longClick = false;
                }
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.container);
                ImageView iView = (ImageView)view.findViewById(R.id.tick_view);
                longClick = true;


                if(iView.getVisibility()==View.INVISIBLE)
                {
                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
                    iView.setVisibility(View.VISIBLE);
                    ItemCount = ItemCount+1;
                    title.setText(ItemCount+"");
                    if(deleteIcon.getVisibility() == View.INVISIBLE)
                    {
                        deleteIcon.setVisibility(View.VISIBLE);
                    }
                }

                else
                {
                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    iView.setVisibility(View.INVISIBLE);
                    //ItemCount = ItemCount-1;

                    if(ItemCount >0)
                    {
                        ItemCount = ItemCount-1;
                        title.setText(""+ItemCount);
                    }


                    if(ItemCount==0)
                    {
                        title.setText(getString(R.string.drawer_option4));
                        deleteIcon.setVisibility(View.INVISIBLE);
                    }
                }


                return false;
            }
        });

    }


    public void setUpToolbar() {
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title = (CustomBoldTextView) findViewById(R.id.title_text);
        title.setText(getString(R.string.trader_favourite));
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
