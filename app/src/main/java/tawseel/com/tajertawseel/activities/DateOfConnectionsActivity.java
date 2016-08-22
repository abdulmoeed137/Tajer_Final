package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import tawseel.com.tajertawseel.adapters.DelegatesQuestionAdapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class DateOfConnectionsActivity extends BaseActivity {
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



    public void setupComponents()
    {
        mLisView = (ListView)findViewById(R.id.connectionsListView);

        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"delivers.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        if(i==0)
                        {
                            date=jsonObj.getString("DeliveryDate");
                        DateOfConnectionsData tdata=new DateOfConnectionsData();
                        tdata.setGid(jsonObj.getString("GroupID"));
                        tdata.setDate(jsonObj.getString("DeliveryDate"));
                        tdata.setGname(jsonObj.getString("name"));
                        tdata.setTime(jsonObj.getString("Deliverytime"));
                        tdata.setTitle("");
                        tdata.setDname(jsonObj.getString("Name"));
                        tdata.setDelivers(jsonObj.getString("orders"));
                        tdata.setStars(jsonObj.getString("delivers"));
                        data.add(tdata);

                            DateOfConnectionsData tdata1=new DateOfConnectionsData();
                            String[] datearr=jsonObj.getString("DeliveryDate").split(" ");
                            tdata1.setTitle(datearr[1]+" "+datearr[2]);
                            data.add(tdata1);
                        }
                        else
                        {
                            if(date.compareTo(jsonObj.getString("DeliveryDate"))!=0)
                            {
                                date=jsonObj.getString("DeliveryDate");
                                DateOfConnectionsData tdata=new DateOfConnectionsData();
                                tdata.setGid(jsonObj.getString("GroupID"));
                                tdata.setDate(jsonObj.getString("DeliveryDate"));
                                tdata.setGname(jsonObj.getString("name"));
                                tdata.setTime(jsonObj.getString("Deliverytime"));
                                tdata.setTitle("");
                                tdata.setDname(jsonObj.getString("Name"));
                                tdata.setDelivers(jsonObj.getString("orders"));
                                tdata.setStars(jsonObj.getString("delivers"));
                                data.add(tdata);

                                DateOfConnectionsData tdata1=new DateOfConnectionsData();
                                String[] datearr=jsonObj.getString("DeliveryDate").split(" ");
                                tdata1.setTitle(datearr[1]+" "+datearr[2]);
                                data.add(tdata1);
                            }
                            else
                            {
                                DateOfConnectionsData tdata=new DateOfConnectionsData();
                                tdata.setGid(jsonObj.getString("GroupID"));
                                tdata.setDate(jsonObj.getString("DeliveryDate"));
                                tdata.setGname(jsonObj.getString("name"));
                                tdata.setTime(jsonObj.getString("Deliverytime"));
                                tdata.setTitle("");
                                tdata.setDname(jsonObj.getString("Name"));
                                tdata.setDelivers(jsonObj.getString("orders"));
                                tdata.setStars(jsonObj.getString("delivers"));
                                data.add(tdata);
                            }
                        }
                    }
                    mLisView.setAdapter(new DateOfConnectionsAdapter(DateOfConnectionsActivity.this,data));
                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
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
            requestQueue= Volley.newRequestQueue(DateOfConnectionsActivity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(DateOfConnectionsActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }

        mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // T.2.1
                Intent intent=new Intent(DateOfConnectionsActivity.this,PostGroupActivity.class);
                intent.putExtra("id",data.get(i).getGid().toString());
                intent.putExtra("flag","false");
                startActivity(intent);
            }
        });

        mLisView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
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

    }
}
