package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import tawseel.com.tajertawseel.adapters.ConfirmationTabAdapter;

import static tawseel.com.tajertawseel.activities.AddNewOrderActivity.context;

/**
 * Created by AbdulMoeed on 11/16/2016.
 */

public class ConfirmationTab extends BaseActivity {
    ListView mListView;
    ArrayList<PostGroupData> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_tab);

        setupToolbar();
        setupComponents();

    }

    public void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.dilevered_now));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    private void setupComponents() {
       
        mListView = (ListView)findViewById(R.id.mListView);
ImageView im2 = (ImageView)findViewById(R.id.BtnTajerLoc);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent i = new Intent(ConfirmationTab.this,ComfirmationActivity.class);
                i.putExtra("Lat",getIntent().getExtras().getString("TajerLat"));
                i.putExtra("Lng",getIntent().getExtras().getString("TajerLng"));
                startActivity(i);
            }
        });
        ImageView im = (ImageView)findViewById(R.id.BtnTajerContact);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+getIntent().getExtras().getString("TajerContact")));
                if (ActivityCompat.checkSelfPermission(ConfirmationTab.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ConfirmationTab.this,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });
        int statuscode = Integer.parseInt(getIntent().getExtras().getString("StatusCode"));
        setstatus(statuscode);
        TextView tajername = (TextView)findViewById(R.id.TajerName);
        tajername.setText(getIntent().getStringExtra("TajerName"));
        TextView totalprice = (TextView)findViewById(R.id.TotalPrice);
        totalprice.setText("" +getIntent().getStringExtra("ItemsPrice"));
        TextView deliveyprice = (TextView)findViewById(R.id.DeliveryPrice);
        deliveyprice.setText("" +getIntent().getStringExtra("PriceRange"));
        TextView profit = (TextView)findViewById(R.id.Profit);
        profit.setText("" +((Integer.parseInt(getIntent().getStringExtra("ItemsPrice"))*10)/100));
        int x = Integer.parseInt(totalprice.getText().toString())+ Integer.parseInt(deliveyprice.getText().toString())+Integer.parseInt(profit.getText().toString());
      TextView total = (TextView)findViewById(R.id.TotalPrice2);
        total.setText(""+x);


showAllGroups();
    }

    void builderWithBtn() {
        final Dialog dialog = new Dialog(ConfirmationTab.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.confirmation_delivery_code);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.3f;
        dialog.show();
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final TextView verify = (TextView)dialog.findViewById(R.id.BtnVerify);
        final EditText currentcode = (EditText)dialog.findViewById(R.id.CurrentCode);
        currentcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
if (currentcode.getText().length() > 0)
{
    verify.setVisibility(View.VISIBLE);
}
                else verify.setVisibility(View.INVISIBLE);
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ConfirmationCode = getIntent().getExtras().getString("ConfirmationCode");

                String cc = currentcode.getText().toString();
                if (cc.isEmpty())
                {
                    Toast.makeText(ConfirmationTab.this,"Code Empty!",Toast.LENGTH_SHORT).show();
                }
                else if (!ConfirmationCode.equals(cc))
                {

                    verify.setText(getResources().getString(R.string.tryagain));
                    verify.setBackground(getResources().getDrawable(R.drawable.eclipse_red_check));
                }
                else if (ConfirmationCode.equals(cc))
                {
                    verify.setText(getResources().getString(R.string.correct));
                    verify.setBackground(getResources().getDrawable(R.drawable.eclipse_green_check));
                    Button ok = (Button)dialog.findViewById(R.id.ok);
                    ok.setBackgroundColor(getResources().getColor(R.color.mainColor));
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
RunVolley("2",dialog);
                        }
                    });
                }
            }
        });
    }
    void showAllGroups()
    {
      RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        final ProgressDialog progress = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
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
                                item.setPayMethod(jsonObj.getString("PayMethod"));
                                item.setPriceRange(jsonObj.getString("PriceRange"));
                                item.setCustomerPhone(jsonObj.getString("Mobile"));
                                item.setOrderProductQuantity(jsonObj.getString("OrderMember"));
                                item.setItemsPrice(jsonObj.getString("ItemsPrice"));
                                item.setOrderID(jsonObj.getString("OrderID"));
                                item.setID(jsonObj.getString("ID"));
                                item.setConfirmationCode(jsonObj.getString("ConfirmationCode"));
                                item.setLatitude(jsonObj.getString("Latitude"));
                                item.setLongitude(jsonObj.getString("Longitude"));
                         item.setIsConfirmed(jsonObj.getString("IsConfirmed"));
                                    list.add(item);
                            }
                            progress.dismiss();
                          mListView.setAdapter(new ConfirmationTabAdapter(ConfirmationTab.this,list));
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
    }


    void RunVolley (final String value, final Dialog d)
    {
        RequestQueue requestQueue;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        final String formattedDate = df.format(c.getTime());
        final String formattedTime = sdf.format(c.getTime());
        //  Toast.makeText(ComfirmationActivity.this,formattedDate,Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request;
//Toast.makeText(ComfirmationActivity.this,getIntent().getExtras().getString("GroupID")+"\n"+value+"\n"+formattedDate+"\n"+formattedTime,Toast.LENGTH_SHORT).show();
        final  ProgressDialog progress = new ProgressDialog(ConfirmationTab.this, ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.setMessage("Loading...");
        progress.show();

        request = new StringRequest(Request.Method.POST, functions.add+"DeligateTajerConfirm.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
d.dismiss();
                            setstatus(2);
                            progress.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("failed"),Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                        }
                    } catch (JSONException e) {

                        Toast.makeText(getApplicationContext(), "Internet Connection Error",  Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc",error.toString());
                Toast.makeText(getApplicationContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                progress.dismiss();

            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",getIntent().getExtras().getString("GroupID"));
                hashMap.put("hash", "CCB612R");
                hashMap.put("StatusCode",value);
                hashMap.put("Date",formattedDate);
                hashMap.put("Time",formattedTime);
                return hashMap;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }
 void   setstatus(int statuscode)
    {
        TextView tv = (TextView)this.findViewById(R.id.ConfirmationCode) ;
        TextView BtnStatus = (TextView)this.findViewById(R.id.BtnStatusGroup) ;

        if (statuscode == 1 ) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builderWithBtn();
                }
            });

        }else
        {tv.setEnabled(false);
            tv.setText("Confirmed");
            tv.setTextColor(Color.RED);
            BtnStatus.setText("Picked By Deligate");
            BtnStatus.setBackground( getResources().getDrawable(R.drawable.eclipse_green));
            BtnStatus.setTextColor(getResources().getColor(R.color.green2));
        }
    }
}
