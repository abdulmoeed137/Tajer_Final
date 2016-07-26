package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.CustomerRequestActivity;
import tawseel.com.tajertawseel.activities.Customer_request_item_data;
import tawseel.com.tajertawseel.activities.HASH;
import tawseel.com.tajertawseel.activities.HomeActivity;
import tawseel.com.tajertawseel.activities.PickSetActivity;
import tawseel.com.tajertawseel.activities.PostGroupListData;
import tawseel.com.tajertawseel.activities.ProductLayoutData;
import tawseel.com.tajertawseel.activities.functions;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 7/3/2016.
 * Edit: M Monis
 */
public class CustomerRequestAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Customer_request_item_data> List;

    public CustomerRequestAdapter (Context c, ArrayList<Customer_request_item_data> list)
    {
        List=list;
        context = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v  = inflater.inflate(R.layout.customer_request_item,null,false);
        TextView name=(TextView)v.findViewById(R.id.co_name);
        TextView number=(TextView)v.findViewById(R.id.co_number);
        TextView email=(TextView)v.findViewById(R.id.co_email);
        TextView items=(TextView)v.findViewById(R.id.co_nitems);
        final TextView moreView = (TextView) v.findViewById(R.id.moreButton);
        final Customer_request_item_data data=(Customer_request_item_data)getItem(position);
        name.setText(data.getName());
        number.setText(data.getNumber());
        email.setText(data.getEmail());
        items.setText(data.getNo_of_items());
        ExpandablePanel panel = (ExpandablePanel)v.findViewById(R.id.expandableLayout);
        CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);
        final View forlist=v;
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            @Override
            public void onExpand(View handle, final View content) {
                moreView.setText(content.getResources().getString(R.string.less));
                final ProductLayoutData data1=new ProductLayoutData();
                final ArrayList<PostGroupListData> items=new ArrayList<PostGroupListData>();
                RequestQueue requestQueue;
                StringRequest request = new StringRequest(Request.Method.POST, functions.add+"orderitems.php", new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject mainobj=new JSONObject(response);
                            JSONArray jsonArr=mainobj.getJSONArray("info");
                            data1.setDelivery_charges(Long.parseLong(jsonArr.getJSONObject(0).getString("PriceRange")));
                            data1.setPay_method(jsonArr.getJSONObject(0).getString("PayMethod"));
                            for (int i = 0; i < jsonArr.length(); i++) {
                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                PostGroupListData pgld=new PostGroupListData();
                                pgld.setProductID(jsonObj.getString("ProductID"));
                                pgld.setProductName(jsonObj.getString("Title"));
                                pgld.setQuantity(jsonObj.getString("Quantity"));
                                pgld.setDescription(jsonObj.getString("Description"));
                                pgld.setPrice("Price");
                                items.add(pgld);
                            }
                            data1.setItems(items);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }// in case error
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //send data to server using POST
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();

                        hashMap.put("orderid", data.getOrderID());
                        hashMap.put("id",HomeActivity.id);
                        hashMap.put("hash", HASH.getHash());
                        return hashMap;
                    }
                };
                try{
                    requestQueue= Volley.newRequestQueue(context);
                    requestQueue.add(request);
                }
                catch (Exception e)
                {
                    Toast.makeText(context,"Request Issue",Toast.LENGTH_SHORT).show();
                }
                    final ListView productsList = (ListView)forlist.findViewById(R.id.product_list);
                    productsList.setAdapter(new ProductItemAdapter(context,data1.getItems()));
                    productsList.setOnTouchListener(new View.OnTouchListener() {
                        // Setting on Touch Listener for handling the touch inside ScrollView
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // Disallow the touch request for parent scroll on touch of child view
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            return false;
                        }
                    });
                View PriceRangeIcon=(View)content.findViewById(R.id.PriceMark);
                TextView PriceRange2=(TextView)content.findViewById(R.id.riyalPrice);
                TextView ItemsPrice=(TextView)content.findViewById(R.id.ItemsPrice);
                TextView PriceRangeText=(TextView)content.findViewById(R.id.PriceRange);
                TextView TotalPrice=(TextView)content.findViewById(R.id.TotalPrice);
                TextView PayMethod=(TextView)content.findViewById(R.id.PaymentType);
                ItemsPrice.setText(String.valueOf(data1.getTotal()));
                PriceRangeText.setText(String.valueOf(data1.getDelivery_charges()));
                TotalPrice.setText(String.valueOf(data1.getTotal() + data1.getDelivery_charges()));
                if (data1.getPay_method().equals("1"))
                {
                    PayMethod.setText(R.string.wire_transfer);
                }
                else if (data1.getPay_method().equals("2"))
                {
                    PayMethod.setText(R.string.payment_on_delivery);
                }
                if (data1.getDelivery_charges()==20) {
                    PriceRangeIcon.setBackgroundResource(R.drawable.solid_green_circle);
                    PriceRange2.setText(R.string.ryal_20);
                } else if (data1.getDelivery_charges()==30) {
                    PriceRangeIcon.setBackgroundResource(R.drawable.orange_circle);
                    PriceRange2.setText(R.string.ryal30);
                } else if (data1.getDelivery_charges()==40) {
                    PriceRangeIcon.setBackgroundResource(R.drawable.maroon_circle);
                    PriceRange2.setText(R.string.ryal40);
                } else if (data1.getDelivery_charges()==50) {
                    PriceRangeIcon.setBackgroundResource(R.drawable.red_circle);
                    PriceRange2.setText(R.string.ryal50);
                }
            }
            @Override
            public void onCollapse(View handle, View content) {
                moreView.setText(content.getResources().getString(R.string.more));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PickSetActivity.class);
                intent.putExtra("orderID",data.getOrderID());
                context.startActivity(intent);
            }
        });
        return v;
    }
   }
