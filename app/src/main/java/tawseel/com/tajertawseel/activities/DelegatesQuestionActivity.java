package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import tawseel.com.tajertawseel.adapters.DelegatesQuestionAdapter;

/**
 * Created by Junaid-Invision on 7/30/2016.
 */
public class DelegatesQuestionActivity extends BaseActivity {

    ListView listview;
    private CustomBoldTextView title;
    private ImageView deleteIcon;
    private boolean longClick  = false;
    private int ItemCount = 0;
private ArrayList<FavouriteDelegateItemData> data=new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deldgates_question);
        setUpToolbar();
        setUpComponents();
    }

    public void setUpComponents ()
    {
        listview = (ListView)findViewById(R.id.mListView);

        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"favdelegates.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FavouriteDelegateItemData tdata=new FavouriteDelegateItemData();
                        tdata.setId(jsonObj.getString("DeligateID"));
                        tdata.setName(jsonObj.getString("Name"));
                        tdata.setCar(jsonObj.getString("CarBrand"));
                        tdata.setCarnum(jsonObj.getString("CarNo"));
                        tdata.setModel(jsonObj.getString("CarModel"));
                        tdata.setContact(jsonObj.getString("Contact"));
                        tdata.setNdelivers(jsonObj.getString("delivers"));
                        Float idelivers= Float.parseFloat(jsonObj.getString("delivers"));
                        tdata.setStars(String.valueOf(idelivers));
                        data.add(tdata);
                    }
                    listview.setAdapter(new DelegatesQuestionAdapter(DelegatesQuestionActivity.this,data));
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
            requestQueue= Volley.newRequestQueue(DelegatesQuestionActivity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(DelegatesQuestionActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!longClick){
                Intent intent = new Intent(DelegatesQuestionActivity.this,ConnectingProfileDelegates.class);
                    intent.putExtra("DeligateID",data.get(position).getId());
                startActivity(intent);
                }
                else
                {
                    longClick = false;
                }
            }
        });



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option4));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);

    }
}
