package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.NewOrderProductAdapter;
import tawseel.com.tajertawseel.adapters.ProductItemAdapter;

/**
 * Created by Monis on 7/27/2016.
 */

public class Old_Orders_Activity extends  BaseActivity {

    ScrollView orderList;
    Button donebutton;
    RadioGroup radioGroup1;
    private RequestQueue requestQueue;
    private static String URL = functions.add+"unconfirmed_orders.php";
    public static RadioButton rbs[];
    int index=-1;
    ArrayList<Customer_request_item_data> clist=new ArrayList<Customer_request_item_data>();
    ProductLayoutData pld=new ProductLayoutData();
    ArrayList<PostGroupListData> items=new ArrayList<PostGroupListData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_orders_lab);
        orderList=(ScrollView)findViewById(R.id.scrollView);
        donebutton=(Button)findViewById(R.id.Add_to_form_button);
        radioGroup1=(RadioGroup)findViewById(R.id.choresRadioGroup);
        addRadioGroupListener();
        final RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        final RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    rbs=new RadioButton[jsonArr.length()];
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        Customer_request_item_data data=new Customer_request_item_data();
                        data.setOrderID(jsonObj.getString("OrderID"));
                        data.setCustomerName(jsonObj.getString("UserName"));
                        data.setCustomerEmail(jsonObj.getString("Email"));
                        data.setCustomerPhone(jsonObj.getString("Mobile"));
                        clist.add(data);
                        rbs[i]=new RadioButton(Old_Orders_Activity.this);
                        rbs[i].setText(jsonObj.getString("UserName")+"\nNumber of items="+jsonObj.getString("Items"));
                        radioGroup1.addView(rbs[i],lp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",HomeActivity.id);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(Old_Orders_Activity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(Old_Orders_Activity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }
    }

    void addRadioGroupListener()
    {
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int j=0;j<rbs.length;j++){
                    if(rbs[j].isChecked())
                    {
                        index=j;
                        break;
                    }
                }
                            }
        });
    }


    public void addschClick(View view) {
        if(index==-1)
        {
            Toast.makeText(this,"Select a order first.",Toast.LENGTH_SHORT).show();
        return;
        }
        RequestQueue requestQueue1;
        StringRequest request = new StringRequest(Request.Method.POST, functions.add + "orderitems.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainobj = new JSONObject(response);
                    JSONArray jsonArr = mainobj.getJSONArray("info");
                    pld.setDelivery_charges(Long.parseLong(jsonArr.getJSONObject(0).getString("PriceRange")));
                    pld.setPay_method(jsonArr.getJSONObject(0).getString("PayMethod"));
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        PostGroupListData pgld = new PostGroupListData();
                        pgld.setProductID(jsonObj.getString("ProductID"));
                        pgld.setProductName(jsonObj.getString("Title"));
                        pgld.setQuantity(jsonObj.getString("Quantity"));
                        pgld.setDescription(jsonObj.getString("Description"));
                        pgld.setPrice(jsonObj.getString("Price"));
                        items.add(pgld);
                    }
                    New_Orders_Activity.pList.clear();
                    for  (int c=0;c<items.size();c++)
                        New_Orders_Activity.pList.add(items.get(c));
                    ProductItemAdapter adapter=new ProductItemAdapter(AddNewOrderActivity.context,New_Orders_Activity.pList);
                    AddNewOrderActivity.productsList.setAdapter(adapter);
                    pld.setItems(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Old_Orders_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("orderid",clist.get(index).getOrderID());
                hashMap.put("id", HomeActivity.id);
                hashMap.put("hash", HASH.getHash());
                return hashMap;
            }
        };
        try {
            requestQueue1 = Volley.newRequestQueue(Old_Orders_Activity.this);
            requestQueue1.add(request);
        } catch (Exception e) {
            Toast.makeText(Old_Orders_Activity.this, "Request Issue", Toast.LENGTH_SHORT).show();
        }

            AddNewOrderActivity.oldid=Integer.parseInt(clist.get(index).getOrderID());
            AddNewOrderActivity.name.setText(clist.get(index).getCustomerName());
            AddNewOrderActivity.email.setText(clist.get(index).getCustomerEmail());
            AddNewOrderActivity.mobile.setText(clist.get(index).getCustomerPhone());
            AddNewOrderActivity.delivery.setText(String.valueOf(pld.getDelivery_charges()).toString());





            AddNewOrderActivity.item_total.setText(pld.getTotal()+"");
            AddNewOrderActivity.total.setText((pld.getTotal() + pld.getDelivery_charges())+"");
            finish();
    }
}
