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
import tawseel.com.tajertawseel.activities.functions;

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
import tawseel.com.tajertawseel.activities.HASH;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.PickSet_data;
import tawseel.com.tajertawseel.activities.WaitingForAcceptanceActivity;
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
    //private int SelectedItem = -1;
   String GroupID=null;
    boolean flag=false;
            TextView submit;
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
        listView = (ListView) mRootView.findViewById(R.id.listView);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"groups.php?id="+ HomeActivity.id+"&hash="+ HASH.getHash(),
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
        // listView.setAdapter(new pick_dummy_adapter(getActivity(),list));
        requestQueue.add(jsonObjectRequest);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

flag=true;
           GroupID=list.get(position).getGid();

                ((pick_dummy_adapter)listView.getAdapter()).setSelectedItem(position);

//                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
//                ImageView tickView = (ImageView) layout.findViewById(R.id.tick_view);
//
//                try{
//                if(SelectedItem != -1)
//                {
//
//                    View previousView = listView.getChildAt(SelectedItem);
//                    RelativeLayout previousLayout = (RelativeLayout) previousView.findViewById(R.id.container);
//                    ImageView previousTickView = (ImageView) previousView.findViewById(R.id.tick_view);
//
//                    previousTickView.setVisibility( View.INVISIBLE);
//                    previousLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                }
//
//
//               // SelectedItem =position;
//                    }
//
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }




//                if(tickView.getVisibility() == View.INVISIBLE){
//                    tickView.setVisibility(View.VISIBLE);
//                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
//                }
//                else
//                {
//                    tickView.setVisibility(View.INVISIBLE);
//                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                }e


            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                Intent i = new Intent(getActivity(), WaitingForAcceptanceActivity.class);
                i.putExtra("GroupID",GroupID);
                startActivity(i);
                  flag=false;
                    getActivity().finish();
                }
                else
                    Toast.makeText(getActivity(),"Select Group! ",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
