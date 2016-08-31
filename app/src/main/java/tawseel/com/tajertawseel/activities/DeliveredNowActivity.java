package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DeliveredNowAdapter;
import tawseel.com.tajertawseel.adapters.PostGroupListAdapter;

/**
 * Created by Junaid-Invision on 8/9/2016.
 */
public class DeliveredNowActivity extends BaseActivity {

    private RequestQueue requestQueue;
    ListView mListView ;
    String GroupID,ConfirmationCode,StatusCode,GroupName,DeligateName,ItemPrice2,PriceRange2,DeligateNumber;
    TextView NoOfCustomer;
ArrayList< PostGroupData> list = new ArrayList<>();
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_delivered_now);
            setupToolbar();
            setupComponents();
        }

    private void setupComponents() {
        requestQueue= Volley.newRequestQueue(this);
        mListView = (ListView)findViewById(R.id.mListView);

        GroupID=getIntent().getExtras().getString("GroupID");
        ConfirmationCode=getIntent().getExtras().getString("ConfirmationCode");
        StatusCode=getIntent().getExtras().getString("StatusCode");
        GroupName=getIntent().getExtras().getString("GroupName");
        DeligateName=getIntent().getExtras().getString("DeligateName");
        PriceRange2=getIntent().getExtras().getString("ItemPrice");
        ItemPrice2=getIntent().getExtras().getString("PriceRange");
        DeligateNumber=getIntent().getExtras().getString("DeligateNumber");
        NoOfCustomer= (TextView)findViewById(R.id.NoOfCustomers);

  findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent=new Intent(DeliveredNowActivity.this,FinancialRequestActivity.class);
                intent.putExtra("totalitem",ItemPrice2);
                intent.putExtra("totald",PriceRange2);
                intent.putExtra("gid",GroupID);
                startActivity(intent);
            }
        });

        final String DeligateID= getIntent().getExtras().getString("DeligateID");
        ImageView btnImageDeligate = (ImageView)findViewById(R.id.BtnImage) ;
        btnImageDeligate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveredNowActivity.this,ConnectingProfileDelegates.class);
                i.putExtra("DeligateID",DeligateID);
                startActivity(i);
            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"GroupItem.php?id="+GroupID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArr=response.getJSONArray("info");
                            NoOfCustomer.setText(jsonArr.length()+"");
                            for(int i=0;i<jsonArr.length();i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PostGroupData item= new PostGroupData();
                                item.setPriceRange(jsonObj.getString("PriceRange"));
                                item.setCustomerEmail(jsonObj.getString("Email"));
                                item.setCustomerName(jsonObj.getString("UserName"));
                                item.setCustomerPhone(jsonObj.getString("Mobile"));
                                item.setItemsPrice(jsonObj.getString("ItemsPrice"));
                                item.setPayMethod(jsonObj.getString("PayMethod"));
                                item.setOrderProductQuantity(jsonObj.getString("OrderMember"));
                                item.setOrderID(jsonObj.getString("OrderID"));
                                item.setLatitude(jsonObj.getString("Latitude"));
                                item.setLongitude(jsonObj.getString("Longitude"));
                                item.setID(jsonObj.getString("ID"));
                                item.setIsConfirmed(jsonObj.getString("IsConfirmed"));

                                list.add(item);
                            }
                            mListView.setAdapter(new DeliveredNowAdapter(DeliveredNowActivity.this,list,StatusCode));
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

        TextView header = (TextView)findViewById(R.id.header);
        TextView groupid = (TextView)findViewById(R.id.GroupID);
        TextView StatusBox = (TextView)findViewById(R.id.BtnStatus) ;
        TextView DeligateNameTextField= (TextView)findViewById(R.id.DeligateName) ;
        TextView ConfirmCode = (TextView)findViewById(R.id.StatusText);
        TextView PriceRange = (TextView)findViewById(R.id.PriceRange) ;
        TextView ItemPrice = (TextView)findViewById(R.id.ItemPrice) ;
        ImageView CallDeligate= (ImageView) findViewById(R.id.BtnDeligateCall) ;

        PriceRange.setText(PriceRange2+"");
        ItemPrice.setText(ItemPrice2+"");
       ConfirmCode.setText("Confirmation Code: "+ConfirmationCode);
       DeligateNameTextField.setText(DeligateName+"");
        groupid.setText(GroupID+"");
        header.setText(GroupName+"");
        if (StatusCode.toString().equals("1")) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                StatusBox.setBackgroundResource(R.drawable.red_rectangle);

            } else {
                StatusBox.setBackground(getResources().getDrawable(R.drawable.red_rectangle));
            }
            StatusBox.setTextColor(Color.RED);
            StatusBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestQueue = Volley.newRequestQueue(DeliveredNowActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveredNowActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder.setTitle("Are You Sure?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final ProgressDialog progress;
                            StringRequest request;
                            progress = ProgressDialog.show(DeliveredNowActivity.this, "Performing",
                                    "Please Wait..", true);
                            request = new StringRequest(Request.Method.POST, functions.add+"DeleteDeligateFromGroup.php", new Response.Listener<String>() {
                                //if response
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        try {
                                            if (jsonObject.names().get(0).equals("success")) {

                                                Toast.makeText(DeliveredNowActivity.this, "Deligate Cancelled. Please Again Search Deligate for this Group", Toast.LENGTH_SHORT).show();
                                                progress.dismiss();
                                                finish();
                                            } else {
                                                progress.dismiss();
                                                Toast.makeText(DeliveredNowActivity.this, jsonObject.getString("Error while Deleting"), Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        } catch (JSONException e) {
                                            progress.dismiss();
                                            e.printStackTrace();
                                        }

                                    } catch (JSONException e) {
                                        progress.dismiss();
                                        e.printStackTrace();
                                    }


                                }// in case error
                            }, new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    progress.dismiss();
                                    Toast.makeText(DeliveredNowActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                //send data to server using POST
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("id",GroupID);
                                    hashMap.put("hash", HASH.getHash());
                                    return hashMap;
                                }
                            };
                            requestQueue.add(request);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
        }

        assert CallDeligate != null;
        CallDeligate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+DeligateNumber));
                if (ActivityCompat.checkSelfPermission(DeliveredNowActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DeliveredNowActivity.this,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });


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
    }
}
