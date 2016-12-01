package tawseel.com.tajertawseel.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.AddNewOrderActivity;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.HomePickSetActivity;
import tawseel.com.tajertawseel.activities.PostGroupActivity;
import tawseel.com.tajertawseel.activities.PostNewGroupActivity;
import tawseel.com.tajertawseel.activities.PostNewGroupTajerLapActivity;
import tawseel.com.tajertawseel.activities.functions;
import tawseel.com.tajertawseel.adapters.DeliveryGroupAdapter2Home;


/**
 * Created by Junaid-Invision on 8/2/2016.
 */
public class HomeFragment1 extends Fragment {



    private ListView listView;
    private View mRootView;private RequestQueue requestQueue;
    ArrayList<HomeFragment3Data> list = new ArrayList<>();;
    RelativeLayout ll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home1, null, false);
        setupComponents();

        return mRootView;

    }

    public void setupComponents() {
ll = (RelativeLayout)mRootView.findViewById(R.id.ll);
        list.clear();
        requestQueue = Volley.newRequestQueue(getActivity());
        listView = (ListView)mRootView.findViewById(R.id.listView);
        mRootView.findViewById(R.id.BtnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
                // showNotificationDialogue();
            }
        });
        mRootView.findViewById(R.id.MyGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogugFloatingButton();
            }
        });
        listView.setAdapter(new DeliveryGroupAdapter2Home(getActivity(),list));
        final ProgressDialog progress = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"TajerHomeFragment1.php?id="+HomeActivity.id
                ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");

                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                HomeFragment3Data item= new HomeFragment3Data();
                                item.setName(jsonObj.getString("name"));
                                item.setNoOfOrders(jsonObj.getString("members"));
                                item.setGroupID(jsonObj.getString("groupID"));
                                item.setItemPrice(jsonObj.getString("ItemsPrice"));
                                item.setPriceRange(jsonObj.getString("PriceRange"));


                                list.add(item);

                            }
                            listView.setAdapter(new DeliveryGroupAdapter2Home(getActivity(),list));

                            progress.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                            if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                Snackbar.make(ll, "Internet Connection Error", Snackbar.LENGTH_LONG)
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
                            Snackbar.make(ll, "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getActivity().finish(); startActivity(getActivity().getIntent());
                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();}
                    }
                });

        //dummy Adapter
        // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),PostGroupActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                i.putExtra("id",list.get(position).getGroupID()+"");
                i.putExtra("flag","true");
                startActivity(i);
            }
        });







    }


//
//    else if(v.getId() == R.id.new_button)
//    {
//        showDialogue();
//    }


    public void showDialogue() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.new_button_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getActivity().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.show();


        dialog.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePickSetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });
        dialog.findViewById(R.id.BtnNewOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewOrderActivity.class));
            }
        });
        dialog.findViewById(R.id.BtnNewGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostNewGroupActivity.class));
            }
        });
    }
    public void showDialogugFloatingButton() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.new_group_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getActivity().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.show();


        dialog.findViewById(R.id.tl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostNewGroupTajerLapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });
        dialog.findViewById(R.id.tt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewOrderActivity.class));
            }
        });

    }
@Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        setupComponents();
    }
}
