package tawseel.com.tajertawseel.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import tawseel.com.tajertawseel.activities.functions;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
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
        submit=(TextView)mRootView.findViewById(R.id.ButtonConfirmationTajer);
        submit.setVisibility(View.GONE);
        submit.setEnabled(false);
        listView = (ListView) mRootView.findViewById(R.id.listView);

        final  ProgressDialog progress = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"InfoGroup3.php?id="+ HomeActivity.id+"&hash="+ HASH.getHash(),
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
                                data.setDeligateID(jsonObj.getString("DeligateID"));
                                list.add(data);

                            }
                            listView.setAdapter(new pick_dummy_adapter(getActivity(),list));
                            progress.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                            if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                        .setAction("Reload", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(getActivity().getIntent());getActivity().finish();
                                            }
                                        })
                                        .setActionTextColor(Color.RED)

                                        .show();}
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        progress.dismiss();
                        if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(getActivity().getIntent());getActivity().finish();
                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();}
                    }
                });

        //dummy Adapter
        // listView.setAdapter(new pick_dummy_adapter(getActivity(),list));
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
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
                i.putExtra("DeligateID",list.get(position).getDeligateID().toString());
                startActivity(i);
                getActivity().finish();

            }
        });


    }
}
