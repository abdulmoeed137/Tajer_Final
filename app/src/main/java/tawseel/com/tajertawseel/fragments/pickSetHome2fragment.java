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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveredNowActivity;
import tawseel.com.tajertawseel.activities.HASH;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.PickSet_data;
import tawseel.com.tajertawseel.activities.WaitingForAcceptanceActivity;
import tawseel.com.tajertawseel.adapters.DeliveredListAdapter;
import tawseel.com.tajertawseel.adapters.pick_dummy_adapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class pickSetHome2fragment extends Fragment {

    View mRootView;
    ListView listView;

    private StringRequest request;
    //private int SelectedItem = -1;

    TextView submit;
    private RequestQueue requestQueue;
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
        submit=(TextView)mRootView.findViewById(R.id.BtnAddGroupHome);
        submit.setVisibility(View.GONE);
        submit.setEnabled(false);
        listView = (ListView) mRootView.findViewById(R.id.listView);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  "http://192.168.0.100/ms/InfoGroup3.php?id="+ HomeActivity.id+"&hash="+ HASH.getHash(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PickSet_data data=new PickSet_data();
                                data.setGid(jsonObj.getString("groupID"));
                                data.setGname(jsonObj.getString("name"));
                                data.setGmembers(jsonObj.getString("members"));
                                data.setConfirmationCode(jsonObj.getString("ConfirmationCode"));
                                data.setStatusCode(jsonObj.getString("StatusCode"));
                                data.setDeligateName(jsonObj.getString("DeligateName"));
                                data.setItemPrice(jsonObj.getString("ItemsPrice"));
                                data.setPriceRange(jsonObj.getString("PriceRange"));
                                data.setDeligateNumber(jsonObj.getString("DeligateNumber"));
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
        // listView.setAdapter(new pick_dummy_adapter(getActivity(),list));
        requestQueue.add(jsonObjectRequest);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent (getActivity(), DeliveredNowActivity.class);
                i.putExtra("GroupID",list.get(position).getGid().toString());
                i.putExtra("ConfirmationCode",list.get(position).getConfirmationCode().toString());
                i.putExtra("StatusCode",list.get(position).getStatusCode()).toString();
                i.putExtra("GroupName",list.get(position).getGname().toString());
                i.putExtra("DeligateName",list.get(position).getDeligateName().toString());
                i.putExtra("ItemPrice",list.get(position).getItemPrice().toString());
                i.putExtra("PriceRange",list.get(position).getPriceRange()).toString();
                i.putExtra("DeligateNumber",list.get(position).getDeligateNumber()).toString();
                startActivity(i);

            }
        });


    }
}
