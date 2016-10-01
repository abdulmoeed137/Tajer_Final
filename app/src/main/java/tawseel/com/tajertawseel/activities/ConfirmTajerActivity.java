package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ConfirmTajerListAdapter;
import tawseel.com.tajertawseel.adapters.CustomerRequestAdapter;
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ConfirmTajerActivity extends BaseActivity {
    ArrayList<PostGroupData> list = new ArrayList<>();
    ListView ListView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tajer);
        requestQueue= Volley.newRequestQueue(this);
        setUpComponents();
        setUpToolbar();



    }


    public void setUpComponents()
    {
        ListView = (ListView)findViewById(R.id.listView);
        final  ProgressDialog progress = new ProgressDialog(ConfirmTajerActivity.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"GroupItem.php?id="+getIntent().getExtras().getString("GroupID"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");

                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PostGroupData item= new PostGroupData();
                                item.setCustomerEmail(jsonObj.getString("Email"));
                                item.setCustomerName(jsonObj.getString("UserName"));
                                item.setCustomerPhone(jsonObj.getString("Mobile"));
                                item.setOrderProductQuantity(jsonObj.getString("OrderMember"));
                                item.setOrderID(jsonObj.getString("OrderID"));
                                item.setID(jsonObj.getString("ID"));
                                item.setConfirmationCode(jsonObj.getString("ConfirmationCode"));
                                if (jsonObj.getString("IsConfirmed").equals("1"))
                                list.add(item);
                            }
                            progress.dismiss();
                            ListView.setAdapter(new ConfirmTajerListAdapter(ConfirmTajerActivity.this,list));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");

                        progress.dismiss();
                        if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                        Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                        startActivity(getIntent());
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
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNotificationDialogue(ConfirmTajerActivity.this,position );

            }
        });



    }
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        // title.setText(getString(R.string.connecting_profile_delegates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //title.setText("تأكيد");
        // deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    public void showNotificationDialogue(Context c, final int position) {
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.confirmation_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.findViewById(R.id.ButtonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText code = (EditText)dialog.findViewById(R.id.Code);
                if (code.getText().toString().equals(list.get(position).getConfirmationCode()))
                {
RunVolley("3",position);
                }

                else
                    Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.findViewById(R.id.ButtonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.cancel();
            }
        });
        // lp.copyFrom(c.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
//        ListView lv = (ListView) dialog.findViewById(R.id.ordersList);
//        lv.setAdapter(new OrdeDialogueAdapter(c));
        dialog.show();
    }
    void RunVolley (final String value, final int position)
    {

        //  Toast.makeText(ComfirmationActivity.this,formattedDate,Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request;
        final  ProgressDialog progress = new ProgressDialog(ConfirmTajerActivity.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.show();
        request = new StringRequest(Request.Method.POST, functions.add+"DeligateOrderConfirm.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                           finish();
                            startActivity(new Intent(ConfirmTajerActivity.this,DeligateHomeActivity.class));
                            progress.dismiss();
} else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("failed"),Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    } catch (JSONException e) {

                       // Toast.makeText(getApplicationContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progress.dismiss();
                   // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Log.d("Srvc",error.toString());
                //Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",list.get(position).getOrderID()+"");
                hashMap.put("hash", "CCB612R");
                hashMap.put("IsConfirmed",value);
                return hashMap;
            }
        };
        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfirmTajerActivity.this,DeligateHomeActivity.class));
        finish();
    }
}
