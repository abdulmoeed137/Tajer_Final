package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import tawseel.com.tajertawseel.adapters.CustomerRequestAdapter;

/**
 * Created by Junaid-Invision on 7/3/2016.
 *
 * Edited by M Monis on 7/21/2016
 */
public class CustomerRequestActivity extends BaseActivity {

    ListView mListView;
    ImageView postRequestButton;
    TextView oCount;
    private RequestQueue requestQueue;
    private static final String URL = functions.add+"orders.php";
    private StringRequest request;
    ArrayList<Customer_request_item_data> list=new ArrayList<Customer_request_item_data>();
    ArrayList<ProductLayoutData> itemList=new ArrayList<ProductLayoutData>();
    String temp="-1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);
        oCount=(TextView)findViewById(R.id.req_countText);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainobj=new JSONObject(response);
                    JSONArray jsonArr=mainobj.getJSONArray("info");
                    int item=1;
                    ArrayList<PostGroupListData> pdlist=new ArrayList<PostGroupListData>();
                    for (int i = 0; i < jsonArr.length(); i++) {

                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        if(jsonObj.get("OrderID").toString().compareTo(temp)!=0)
                        {
                            temp=jsonObj.get("OrderID").toString();
                            Customer_request_item_data data=new Customer_request_item_data();
                            data.setOrderID(temp);
                            data.setName(jsonObj.getString("UserName"));
                            data.setEmail(jsonObj.getString("Email"));
                            data.setNumber(jsonObj.getString("Mobile"));
                            list.add(data);
                            if(i!=0)
                            {
                                list.get(list.size()-1).setNo_of_items(String.valueOf(item));
                                item=0;
                                ProductLayoutData pld=new ProductLayoutData();
                                pld.setItems(pdlist);
                                pld.setPay_method(jsonObj.getString("PayMethod"));
                                pld.setDelivery_charges(Long.parseLong(jsonObj.getString("PriceRange")));
                                itemList.add(pld);
                                pdlist.clear();
                            }
                        }
                        else{
                            item++;
                            PostGroupListData sitem=new PostGroupListData();
                            sitem.setProductID(jsonObj.getString("ProductID"));
                            sitem.setQuantity(jsonObj.getString("Quantity"));
                            sitem.setProductName(jsonObj.getString("Title"));
                            sitem.setDescription(jsonObj.getString("Description"));
                            sitem.setPrice((jsonObj.getString("Price")));
                            pdlist.add(sitem);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }// in case error
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
            requestQueue= Volley.newRequestQueue(CustomerRequestActivity.this);
            requestQueue.add(request);}
        catch (Exception e)
        {
            Toast.makeText(CustomerRequestActivity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }

        setUpToolbar();
        setUpComponents();
    }

    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option1));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setUpComponents()
    {
        mListView = (ListView)findViewById(R.id.customer_request_listView);
        Customer_request_item_data item=new Customer_request_item_data();
        item.setOrderID("7");
        item.setName("Monis");
        item.setEmail("moni.com");
        item.setNumber("34604445457");
        Customer_request_item_data item1=new Customer_request_item_data();
        item1.setOrderID("3");
        item1.setName("Moid");
        item1.setEmail("abc.com");
        item1.setNumber("7600837657");
        ProductLayoutData list1[]=new ProductLayoutData[2];
        list1[0]=new ProductLayoutData();
        list1[1]=new ProductLayoutData();
        ArrayList<PostGroupListData> pdlist=new ArrayList<PostGroupListData>();
        PostGroupListData pd=new PostGroupListData();
        pd.setProductID("0");
        pd.setProductName("HandsFree");
        pd.setDescription("Best HandsFree");
        pd.setPrice("500");
        pd.setQuantity("1");
        pdlist.add(pd);
        list1[0].setItems(pdlist);
        list1[0].setDelivery_charges(20);
        list1[0].setPay_method("1");
        ArrayList<PostGroupListData> pdlist1=new ArrayList<PostGroupListData>();
        PostGroupListData pd1=new PostGroupListData();
        pd1.setProductID("1");
        pd1.setProductName("Mobile");
        pd1.setQuantity("1");
        pd1.setDescription("BEST Mobile");
        pd1.setPrice("1000");
        pdlist1.add(pd1);
        PostGroupListData pd2=new PostGroupListData();
        pd2.setProductID("1008");
        pd2.setProductName("Charger");
        pd2.setDescription("Best Charger");
        pd2.setPrice("100");
        pd2.setQuantity("4");
        pdlist1.add(pd2);
        list1[1].setItems(pdlist1);
        list1[1].setDelivery_charges(40);
        list1[1].setPay_method("2");
        item1.setNo_of_items(String.valueOf(list1[0].getItems().size()));
        item.setNo_of_items(String.valueOf(list1[1].getItems().size()));
        list.add(item);
        list.add(item1);
        mListView.setAdapter(new CustomerRequestAdapter(this,list,list1));
        postRequestButton = (ImageView)findViewById(R.id.post_your_request_button);
        setUpListeners();
    }

    public void setUpListeners ()
    {
        postRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i  = new Intent (CustomerRequestActivity.this, AddNewOrderActivity.class);

                startActivity(i);

            }
        });
    }
}