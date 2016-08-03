package tawseel.com.tajertawseel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveryGroupData;
import tawseel.com.tajertawseel.activities.HASH;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.PickSet_data;
import tawseel.com.tajertawseel.adapters.DileveryGroupAdapter;
import tawseel.com.tajertawseel.adapters.PickSetAdapter;
import tawseel.com.tajertawseel.adapters.pick_dummy_adapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class pickSetHome1fragment extends Fragment {

    View mRootView;
    ListView listView;
    private RequestQueue requestQueue;
    private StringRequest request;
    ArrayList<PickSet_data> list=new ArrayList<PickSet_data>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_picksethome1, null,false);

        setupComponents();

        return mRootView;
    }

    public void setupComponents()
    {
        requestQueue = Volley.newRequestQueue(getActivity());
        listView = (ListView) mRootView.findViewById(R.id.listView);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/groups.php?id="+ HomeActivity.id+"&hash="+ HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PickSet_data data=new PickSet_data();
                                data.setGid(jsonObj.getString("GroupID"));
                                data.setGname(jsonObj.getString("name"));
                                data.setGmembers(jsonObj.getString("members"));
                                list.add(data);

                            }
                            listView.setAdapter(new pick_dummy_adapter(getActivity(),list));

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



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
                ImageView tickView = (ImageView) layout.findViewById(R.id.tick_view);


                if(tickView.getVisibility() == View.INVISIBLE){
                    tickView.setVisibility(View.VISIBLE);
                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
                }
                else
                {
                    tickView.setVisibility(View.INVISIBLE);
                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }


            }
        });

    }
}
