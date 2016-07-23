package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
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
 */
public class PickSetActivity extends BaseActivity {


    ListView mListView;
    CustomBoldTextView demandButton;
    private RequestQueue requestQueue;
    private static final String URL = functions.add+"groups.php";
    private StringRequest request;
    ArrayList<PickSet_data> list=new ArrayList<PickSet_data>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_set);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("orderID");
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
                        data.setGid(jsonObj.getString("groupID"));
                        data.setGname(jsonObj.getString("name"));
                        data.setGmembers(jsonObj.getString("members"));
                        list.add(data);
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


    public void setUpComponents (){
        mListView = (ListView)findViewById(R.id.pickSetListView);
        mListView.setAdapter(new PickSetAdapter(this,list));


        demandButton = (CustomBoldTextView)findViewById(R.id.add_demand_basket);
        demandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);
                container.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });


    }
}
