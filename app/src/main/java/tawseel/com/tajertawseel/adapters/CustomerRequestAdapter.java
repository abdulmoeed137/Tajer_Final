package tawseel.com.tajertawseel.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.Customer_request_item_data;
import tawseel.com.tajertawseel.activities.PickSetActivity;
import tawseel.com.tajertawseel.activities.PostGroupListData;
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

    private RequestQueue requestQueue;
    public CustomerRequestAdapter (Context c, ArrayList<Customer_request_item_data> list)
    {
        List=list;

        context = c;
        inflater = LayoutInflater.from(c);
        requestQueue = Volley.newRequestQueue(context);
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

        final  ViewHolder holder;
        final Customer_request_item_data data=(Customer_request_item_data)getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView  = inflater.inflate(R.layout.customer_request_item,null,false);
            holder.CustomerName=(TextView)convertView.findViewById(R.id.co_name);
            holder.CustomerPhone=(TextView)convertView.findViewById(R.id.co_number);
            holder.CustomerEmail=(TextView)convertView.findViewById(R.id.co_email);
            holder.OrderProductQuantity=(TextView)convertView.findViewById(R.id.co_nitems);


            convertView.setTag(holder);

        }
        else
            holder=(ViewHolder) convertView.getTag();

        final TextView moreView = (TextView) convertView.findViewById(R.id.moreButton);

        holder.CustomerName.setText(data.getCustomerName());
        holder.CustomerPhone.setText(data.getCustomerPhone());
        holder.CustomerEmail.setText(data.getCustomerEmail());
        holder.OrderProductQuantity.setText(data.getOrderProductQuantity());
        ExpandablePanel panel = (ExpandablePanel)convertView.findViewById(R.id.expandableLayout);
        final CustomBoldTextView textView = (CustomBoldTextView) convertView.findViewById(R.id.start_delivery_button);
        textView.setTag(data.getID()+"");
        final View finalConvertView=convertView;
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            @Override
            public void onExpand(View handle, final View content) {

                holder.PriceRangeIcon = (View) content.findViewById(R.id.PriceMark);
                holder.PriceRange2 = (TextView) content.findViewById(R.id.riyalPrice);
                holder.ItemsPrice = (TextView) content.findViewById(R.id.ItemsPrice);
                holder.PriceRangeText = (TextView) content.findViewById(R.id.PriceRange);
                holder.TotalPrice= (TextView) content.findViewById(R.id.TotalPrice);
                holder.PayMethod= (TextView)content.findViewById(R.id.PaymentType) ;

                holder.ItemsPrice.setText(data.getItemsPrice());
                holder.PriceRangeText.setText(data.getPriceRange());
                holder.TotalPrice.setText(Integer.parseInt(data.getItemsPrice()) + (Integer.parseInt(data.getPriceRange())) + "");
                if (data.getPayMethod().equals("1"))
                {
                    holder.PayMethod.setText(R.string.wire_transfer);
                }
                else if (data.getPayMethod().equals("2"))
                {
                    holder.PayMethod.setText(R.string.payment_on_delivery);
                }
                if (data.getPriceRange().equals("20")) {
                    holder.PriceRangeIcon.setBackgroundResource(R.drawable.solid_green_circle);
                    holder.PriceRange2.setText(R.string.ryal_20);

                } else if (data.getPriceRange().equals("30")) {
                    holder.PriceRangeIcon.setBackgroundResource(R.drawable.orange_circle);
                    holder.PriceRange2.setText(R.string.ryal30);
                } else if (data.getPriceRange().equals("40")) {
                    holder.PriceRangeIcon.setBackgroundResource(R.drawable.maroon_circle);
                    holder.PriceRange2.setText(R.string.ryal40);
                } else if (data.getPriceRange().equals("50")) {
                    holder.PriceRangeIcon.setBackgroundResource(R.drawable.red_circle);
                    holder.PriceRange2.setText(R.string.ryal50);
                }

                final ListView productsList = (ListView) finalConvertView.findViewById(R.id.product_list);
                final  ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Loading...");
                progress.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  functions.add+"OrderDetails.php?id="+data.getID(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArr=response.getJSONArray("info");
                                    ArrayList<PostGroupListData> list = new ArrayList<>();
                                    for(int i=0;i<jsonArr.length();i++) {
                                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                        PostGroupListData item= new PostGroupListData();
                                        item.setProductID(jsonObj.getString("ProductID"));
                                        item.setDescription(jsonObj.getString("Description"));
                                        item.setPrice(jsonObj.getString("Price"));
                                        item.setProductName(jsonObj.getString("ProductName"));
                                        item.setQuantity(jsonObj.getString("Quantity"));
                                        list.add(item);
                                    }
                                    productsList.setAdapter(new ProductItemAdapter(context,list));
                                    progress.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progress.dismiss();
                                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                        Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                                .setAction("Reload", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        context.startActivity(((Activity)context).getIntent());  ((Activity)context).finish();
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
                                    Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                            .setAction("Reload", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    context.startActivity(((Activity)context).getIntent());  ((Activity)context).finish();
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

                productsList.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
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
                intent.putExtra("orderID",v.getTag()+"");
                Toast.makeText(context,v.getTag()+"",Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
                ((Activity)context).finish();

            }
        });
        return convertView;

    }

   }
