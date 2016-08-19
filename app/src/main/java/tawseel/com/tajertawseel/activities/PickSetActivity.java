package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import tawseel.com.tajertawseel.adapters.PickSetAdapter;

/**
 * Created by Junaid-Invision on 7/3/2016.
 *
 *
 * Edited by M Monis on 7/23/2016
 */
public class PickSetActivity extends BaseActivity {


    ListView mListView;
    CustomBoldTextView demandButton;
    TextView grp_count;
    private RequestQueue requestQueue,requestQueue1;
    private static final String URL = functions.add+"groups.php";
    private static final String URLupdate = functions.add+"add_order_to_groups.php";
    private StringRequest request,request1;
    ArrayList<PickSet_data> list=new ArrayList<PickSet_data>();
    private String orderID,groupID;
    private boolean result=false;
    View itert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_set);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = extras.getString("orderID");
        }
        Toast.makeText(PickSetActivity.this , orderID,Toast.LENGTH_SHORT).show();
        requestQueue= Volley.newRequestQueue(this);
        setUpToolbar();
        setUpComponents();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/groups.php?id="+HomeActivity.id+"&hash="+HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                           grp_count.setText(jsonArr.length()+"");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PickSet_data data=new PickSet_data();
                                data.setGid(jsonObj.getString("GroupID"));
                                data.setGname(jsonObj.getString("name"));
                                data.setGmembers(jsonObj.getString("members"));
                                list.add(data);

                            }

                            mListView.setAdapter(new PickSetAdapter(PickSetActivity.this,list));
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


    public void setUpToolbar()
    { 
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView)findViewById(R.id.title_text);
        toolbarTitle.setText(getString(R.string.pick_set));
        setSupportActionBar(toolbar);
    }

    private void addGroup()
    {
        Toast.makeText(PickSetActivity.this,orderID+"--"+groupID+"",Toast.LENGTH_SHORT).show();
    }


    public void setUpComponents (){
        mListView = (ListView)findViewById(R.id.pickSetListView);
        grp_count=(TextView)findViewById(R.id.grp_count);

        demandButton = (CustomBoldTextView)findViewById(R.id.add_demand_basket);
        demandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupID==null)
                {
                    Toast.makeText(PickSetActivity.this,"Select a group first.",Toast.LENGTH_SHORT).show();
                    return;
                }
                requestQueue1= Volley.newRequestQueue(PickSetActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"add_order_to_groups.php?orderID="+orderID+"&grpID="+groupID+"&hash="+HASH.getHash(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArr=response.getJSONArray("info");
                                    for(int i=0;i<jsonArr.length();i++) {
                                        final JSONObject jsonObj = jsonArr.getJSONObject(i);

                                        if (jsonObj.getString("status").equals("success")){

                                        final Dialog dialog = new Dialog(PickSetActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            dialog.setContentView(R.layout.dialogue_layout);
                                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                            lp.copyFrom(dialog.getWindow().getAttributes());
                                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                            lp.gravity = Gravity.CENTER;
                                            lp.dimAmount = 0.3f;
                                            dialog.findViewById(R.id.confirm12).setOnClickListener(new
                                                                                                           View.OnClickListener() {
                                                                                                               @Override
                                                                                                               public void onClick(View v) {


                                                                                                                   finish();
                                                                                                               }
                                                                                                           });
                                            dialog.show();
                                            groupID=null;

                                         /*       for (int ij=0;ij<mListView.getCount();ij++) {
                                                RelativeLayout container = (RelativeLayout) mListView.getChildAt(ij).findViewById(R.id.container);
                                                container.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                            }
                                             */
                                        }
                                        else {
                                            Toast.makeText(PickSetActivity.this,"Cannot add order to the group.",Toast.LENGTH_SHORT).show();
                                        }
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
                requestQueue1.add(jsonObjectRequest);



            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itert==null)
                {
         RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
         container.setBackgroundColor(Color.parseColor("#CCCCCC"));
                    groupID =  list.get(position).getGid()+"";
                    Toast.makeText(PickSetActivity.this,position+"and ID : "+list.get(position).getGid(),Toast.LENGTH_SHORT).show();
                    itert=view;
                }
                else if(itert==view)
                {
                    RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
                    container.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    groupID = null;
                    itert=null;
                }
                else
                {
                    RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
                    container.setBackgroundColor(Color.parseColor("#CCCCCC"));
                    groupID =  list.get(position).getGid()+"";

                    RelativeLayout container1 = (RelativeLayout)itert.findViewById(R.id.container);
                    container1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    itert=view;
                }
            }
        });
    }
}
