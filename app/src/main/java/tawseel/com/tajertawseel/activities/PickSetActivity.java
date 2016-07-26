package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
    private RequestQueue requestQueue;
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
        requestQueue= Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainobj=new JSONObject(response);
                    JSONArray jsonArr=mainobj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        PickSet_data data=new PickSet_data();
                        data.setGid(jsonObj.getString("GroupID"));
                        data.setGname(jsonObj.getString("name"));
                        data.setGmembers(jsonObj.getString("members"));
                        list.add(data);
                    }
                    grp_count.setText(list.size());
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }


            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
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
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(PickSetActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }
        setUpToolbar();
        setUpComponents();
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
        requestQueue= Volley.newRequestQueue(this);
        request1 = new StringRequest(Request.Method.POST, URLupdate, new Response.Listener<String>() {
            public void onResponse(String resp) {
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    if (jsonObject.names().get(0).equals("success"))
                        result=true;
                    else {
                        Toast.makeText(PickSetActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                hashMap.put("orderID",orderID);
                hashMap.put("grpID",groupID);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };

        try{
            requestQueue.add(request1);
        }
        catch (Exception e)
        {
            Toast.makeText(PickSetActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }
    }


    public void setUpComponents (){
        mListView = (ListView)findViewById(R.id.pickSetListView);
        grp_count=(TextView)findViewById(R.id.grp_count);
        mListView.setAdapter(new PickSetAdapter(this,list));
        demandButton = (CustomBoldTextView)findViewById(R.id.add_demand_basket);
        demandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupID==null)
                {
                    Toast.makeText(PickSetActivity.this,"Select a group first.",Toast.LENGTH_SHORT).show();
                    return;
                }
                addGroup();
                if(result) {
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
                    dialog.show();
                    result=false;
                    groupID=null;
                    for (int i=0;i<mListView.getCount();i++) {
                        RelativeLayout container = (RelativeLayout) mListView.getChildAt(i).findViewById(R.id.container);
                        container.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
                else
                {
                    Toast.makeText(PickSetActivity.this,"Cannot add order to the group.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itert==null)
                {
         RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
         container.setBackgroundColor(Color.parseColor("#CCCCCC"));
         groupID = view.getId() + "";
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
                    groupID = view.getId() + "";
                    RelativeLayout container1 = (RelativeLayout)itert.findViewById(R.id.container);
                    container1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    itert=view;
                }
            }
        });
    }
}
