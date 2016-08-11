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
import android.widget.Toast;

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
    private int count= 0 ;
    CustomBoldTextView title;
    ImageView deleteIcon;
    boolean longClick = false;

    ArrayList<DeliveryGroupData> list = new ArrayList<>();;


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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/info_grp.php?id="+LoginActivity.LoginID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
try {

                            JSONArray jsonArr=response.getJSONArray("info");
                         total_groups.setText(jsonArr.length()+"");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                DeliveryGroupData item= new DeliveryGroupData();
                                item.setName(jsonObj.getString("name"));
                                item.setNoOfOrders(jsonObj.getString("members"));
                                item.setGrpID(jsonObj.getString("groupID"));
                                item.setItemPrice(jsonObj.getString("ItemsPrice"));
                                item.setPriceRange(jsonObj.getString("PriceRange"));

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

        //dummy Adapter
       // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
        requestQueue.add(jsonObjectRequest);

        postGroup = (ImageView)findViewById(R.id.post_group_button);


        postGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryGroupActivity.this,PostNewGroupActivity.class);
                i.putExtra("status","new");
                startActivity(i);
            }
        });


//        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        });

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if(!longClick){
               Intent i = new Intent(DeliveryGroupActivity.this,PostGroupActivity.class);

                i.putExtra("id",list.get(position).getGrpID()+"");
                 startActivity(i);

                   }
                else
               {
                   longClick = false;
               }
            }
        });



        groupListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView tickView = (ImageView)view.findViewById(R.id.tick_view);

                longClick = true;
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.container);
                if(tickView.getVisibility() == View.INVISIBLE)
                {

                    tickView.setVisibility(View.VISIBLE);
                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
                    count = count+1;

                    if(count>0)
                    {
                        title.setText(""+count);
                        if(deleteIcon.getVisibility() == View.INVISIBLE)
                        {
                            deleteIcon.setVisibility(View.VISIBLE);
                        }
                    }
                  //  layout.setBackgroundColor(getResources().getColor(R.color.));
                }
                else
                {
                    tickView.setVisibility(View.INVISIBLE);
                    count = count-1;
                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    if(count >= 1)
                    {
                        title.setText(""+count);
                    }
                    else
                    {
                        title.setText(getString(R.string.drawer_option2));
                        deleteIcon.setVisibility(View.INVISIBLE);
                    }

                }



                return false;
            }
        });
    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);

    }
    private void getData()
    {


    }
}
