package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DileveryGroupAdapter;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class DeliveryGroupActivity extends BaseActivity {


    private ListView groupListView;
    private ImageView postGroup;
    private TextView total_groups;
    private RequestQueue requestQueue;
    private StringRequest request;

    ArrayList<DeliveryGroupData> list= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_groups);
     total_groups = (TextView)findViewById(R.id.request_count);
        requestQueue = Volley.newRequestQueue(this);
        setUpToolbar();
        setupComponents();


    }

    public void setupComponents ()
    {
        groupListView = (ListView)findViewById(R.id.group_list_view);




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/info_grp.php?id=1",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
try {

                            JSONArray jsonArr=response.getJSONArray("info");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                DeliveryGroupData item= new DeliveryGroupData();
                                item.setName(jsonObj.getString("name"));
                                item.setNoOfOrders(jsonObj.getString("members"));
                                item.setGrpID(jsonObj.getString("groupID"));
                                list.add(item);
                            }
    groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
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
        requestQueue.add(jsonObjectRequest);
        total_groups.setText(list.size()+"");

        postGroup = (ImageView)findViewById(R.id.post_group_button);


        postGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryGroupActivity.this,PostGroupActivity.class);
                startActivity(i);
            }
        });


        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView tickView = (ImageView)view.findViewById(R.id.tick_view);

                LinearLayout layout = (LinearLayout) view.findViewById(R.id.container);
                if(tickView.getVisibility() == View.INVISIBLE)
                {

                    tickView.setVisibility(View.VISIBLE);
                  //  layout.setBackgroundColor(getResources().getColor(R.color.));
                }
                else
                {
                    tickView.setVisibility(View.INVISIBLE);
                   // layout.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    private void getData()
    {


    }
}
