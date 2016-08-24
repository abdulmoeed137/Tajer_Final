package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.ArrayList;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ConfirmTajerListAdapter;
import tawseel.com.tajertawseel.adapters.PickSetAdapter;

/**
 * Created by AbdulMoeed on 8/23/2016.
 */
public class DeligateConfirmationActivity extends BaseActivity {
    ListView mListView;TextView grp_count; private RequestQueue requestQueue, requestQueue1;ArrayList<PickSet_data> list = new ArrayList<PickSet_data>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_set);
        setUpToolbar();
        setUpComponents();
    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView) findViewById(R.id.title_text);
        toolbarTitle.setText(getString(R.string.pick_set));
        setSupportActionBar(toolbar);
    }
    public void setUpComponents() {
  findViewById(R.id.add_demand_basket).setVisibility(View.GONE);
        findViewById(R.id.ll).setVisibility(View.GONE);
        requestQueue = Volley.newRequestQueue(this);
        mListView = (ListView) findViewById(R.id.pickSetListView);
        grp_count = (TextView) findViewById(R.id.grp_count);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.0.100/ms/DeligateInfoGroup2.php?id=" + LoginActivity.DeligateID + "&hash=" + HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr = response.getJSONArray("info");
                            grp_count.setText(jsonArr.length() + "");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PickSet_data data = new PickSet_data();
                                data.setGid(jsonObj.getString("groupID"));
                                data.setGname(jsonObj.getString("name"));
                                data.setGmembers(jsonObj.getString("members"));
                                data.setStatusCode(jsonObj.getString("StatusCode"));
                                data.setConfirmationCode(jsonObj.getString("ConfirmationCode"));
                                data.setTajerLatitude(jsonObj.getString("TajerLatitude"));
                                data.setTajerLongitude(jsonObj.getString("TajerLongitude"));
                                list.add(data);

                            }

                            mListView.setAdapter(new PickSetAdapter(DeligateConfirmationActivity.this, list));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ;
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( list.get(position).getStatusCode().equals("1"))
                {
                    Intent i = new Intent(DeligateConfirmationActivity.this, ComfirmationActivity.class);
                    i.putExtra("ConfirmationCode",list.get(position).getConfirmationCode());
                    i.putExtra("GroupID",list.get(position).getGid());
                    i.putExtra("TajerLatitude",list.get(position).getTajerLatitude());
                    i.putExtra("TajerLongitude",list.get(position).getTajerLongitude());

                    startActivity(i);
                    finish();

                }else if (list.get(position).getStatusCode().equals("2"))
                {
                    Intent i = new Intent(DeligateConfirmationActivity.this,ConfirmTajerActivity.class);
                    i.putExtra("GroupID",list.get(position).getGid());
                    startActivity(i);
                    finish();
                }
            }
        });

    }



}