package tawseel.com.tajertawseel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveredNowActivity;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.LoginActivity;
import tawseel.com.tajertawseel.activities.PostGroupActivity;
import tawseel.com.tajertawseel.activities.PostNewGroupActivity;
import tawseel.com.tajertawseel.adapters.DeliveredListAdapter;

/**
 * Created by AbdulMoeed on 8/17/2016.
 */
public class delegateHomeFragment2 extends Fragment{
    private ListView listView;
    private View mRootView;private RequestQueue requestQueue;
    ArrayList<HomeFragment2Data> list = new ArrayList<>();;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home2,null,false);

        setupContentView();
        return mRootView;
    }


    public void setupContentView()
    {
        requestQueue = Volley.newRequestQueue(getActivity());
        listView = (ListView)mRootView.findViewById(R.id.listView);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/DeligateInfoGroup.php?id="+ LoginActivity.DeligateID
                ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");

                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                HomeFragment2Data item= new HomeFragment2Data();
                                item.setName(jsonObj.getString("name"));
                                item.setMembers(jsonObj.getString("members"));
                                item.setGroupID(jsonObj.getString("groupID"));
                                item.setDeligateContact(jsonObj.getString("TajerContact"));
                                list.add(item);
                            }
                            listView.setAdapter(new DeliveredListAdapter(getActivity(),list));

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
               Intent i = new Intent (getActivity(), PostGroupActivity.class);
                i.putExtra("id",list.get(position).getGroupID().toString());
                i.putExtra("flag",false);

                startActivity(i);
            }
        });
    }

}
