package tawseel.com.tajertawseel.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.ConfirmTajerActivity;
import tawseel.com.tajertawseel.activities.LoginActivity;
import tawseel.com.tajertawseel.activities.UpdateLocation;
import tawseel.com.tajertawseel.activities.functions;

/**
 * Created by Junaid-Invision on 8/16/2016.
 */
public class delegateHomeFragment1 extends Fragment {

    View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_delegates_home1, null, false);
        setupComponents();
        return mRootView;

    }

    public void setupComponents() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        mRootView.findViewById(R.id.ButtonConfirmationTajer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
            }
        });

    }


//
//    else if(v.getId() == R.id.new_button)
//    {
//        showDialogue();
//    }


    public void showDialogue()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.available_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getActivity().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.findViewById(R.id.Online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
RunVolley("1");
                dialog.dismiss();
                Intent i = new Intent(getActivity(),UpdateLocation.class);
                getActivity().startService(i);
            }
        });
        dialog.findViewById(R.id.Offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
RunVolley("0");
                dialog.dismiss();
                Intent i = new Intent(getActivity(),UpdateLocation.class);
                getActivity().stopService(i);
            }
        });
        dialog.show();

//        dialog.findViewById(R.id.BtnNewGroup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // startActivity(new Intent(getActivity(), PostNewGroupActivity.class));
//            }
//        });
    }
    void RunVolley(final String value){


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request;
        final  ProgressDialog progress = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading");
        progress.show();
        request = new StringRequest(Request.Method.POST, functions.add+"DeligateOnOffLine.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            Snackbar.make(getActivity().findViewById(android.R.id.content), jsonObject.getString("success"), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                            progress.dismiss();


                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), jsonObject.getString("failed"), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                            progress.dismiss();
                        }
                    } catch (JSONException e) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setActionTextColor(Color.RED)

                                .show();
                        progress.dismiss();

                    }

                } catch (JSONException e) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(Color.RED)

                            .show();
                    progress.dismiss();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc",error.toString());
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)

                        .show();
                progress.dismiss();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", LoginActivity.DeligateID);
                hashMap.put("hash", "CCB612R");
                hashMap.put("Status",value);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
