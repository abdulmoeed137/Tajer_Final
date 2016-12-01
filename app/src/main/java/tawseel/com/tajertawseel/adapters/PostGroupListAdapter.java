package tawseel.com.tajertawseel.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.HASH;
import tawseel.com.tajertawseel.activities.PickSetActivity;
import tawseel.com.tajertawseel.activities.PostGroupData;
import tawseel.com.tajertawseel.activities.PostGroupListData;
import tawseel.com.tajertawseel.activities.functions;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class PostGroupListAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<PostGroupData> List;
    private RequestQueue requestQueue;
    ArrayList<PostGroupListData> list = new ArrayList<>();
    String Flag;

    public PostGroupListAdapter (Context c   ,ArrayList<PostGroupData> list,String Flag)
    {
        this.Flag=Flag;
        context = c;
        requestQueue = Volley.newRequestQueue(context);
        inflater = LayoutInflater.from(c);
        List=list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

      final  ViewHolder holder;
        final PostGroupData data = (PostGroupData) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();

        convertView= inflater.inflate(R.layout.post_group_item, null, false);

            holder.CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
            holder.CustomerEmail = (TextView) convertView.findViewById(R.id.Email);
            holder.CustomerPhone = (TextView) convertView.findViewById(R.id.Phone);
            holder.OrderProductQuantity= (TextView)convertView.findViewById(R.id.OrderProductQuantity);
          holder.OrderMoveButton = (ImageView) convertView.findViewById(R.id.BtnMove);
           holder.OrderDeleteButton = (ImageView) convertView.findViewById(R.id.BtnDelete);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        holder.CustomerName.setText(data.getCustomerName());
        holder.CustomerEmail.setText(data.getCustomerEmail());
        holder.CustomerPhone.setText(data.getCustomerPhone());
        holder.OrderProductQuantity.setText(data.getOrderProductQuantity());
        holder.OrderDeleteButton.setTag(data.getID());
       holder.OrderMoveButton.setTag(data.getID());

         final TextView moreView = (TextView) convertView.findViewById(R.id.moreButton2);
        ExpandablePanel panel = (ExpandablePanel) convertView.findViewById(R.id.expandableLayout);


            final View finalConvertView = convertView;
            panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
        @Override
        public void onExpand(View handle, View content) {
            moreView.setText(content.getResources().getString(R.string.less));
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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, functions.add+"OrderDetails.php?id="+data.getOrderID(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArr=response.getJSONArray("info");

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
                            };
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                            progress.dismiss();
                        }
                    });

            //dummy Adapter
            // groupListView.setAdapter(new DileveryGroupAdapter(DeliveryGroupActivity.this,list));
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


    //  CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);
if (Flag.equals("false"))
{
    holder.OrderDeleteButton.setVisibility(View.GONE);
    holder.OrderMoveButton.setVisibility(View.GONE);
}
      holder.OrderMoveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(context,v.getTag()+"",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, PickSetActivity.class);
              i.putExtra("orderID",v.getTag()+"");
              context.startActivity(i);
              ((Activity)context).finish();

          }
      });

        holder.OrderDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String w=v.getTag()+"";
                final RequestQueue requestQueue;

                requestQueue = Volley.newRequestQueue(context);
                ContextThemeWrapper themedContext;
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
                    themedContext = new ContextThemeWrapper( context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar );
                }
                else {
                    themedContext = new ContextThemeWrapper( context, android.R.style.Theme_Light_NoTitleBar );
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(themedContext);

                builder.setTitle("Are You Sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final  ProgressDialog progress = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setMessage("Deleting...");
                        progress.show();
                        StringRequest request;

                        request = new StringRequest(Request.Method.POST, functions.add+"DeleteOrderFromGroup.php", new Response.Listener<String>() {
                            //if response
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    try {
                                        if (jsonObject.names().get(0).equals("success")) {
                                        List.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            progress.dismiss();


                                        } else {
                                            progress.dismiss();
                                            Toast.makeText(context, jsonObject.getString("Error while Deleting"), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        progress.dismiss();
                                        if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                            Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                                    .setAction("Undo", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                        }
                                                    })
                                                    .setActionTextColor(Color.RED)

                                                    .show();}
                                    }

                                } catch (JSONException e) {
                                    progress.dismiss();
                                    e.printStackTrace();
                                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                                        Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                                .setAction("Undo", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                })
                                                .setActionTextColor(Color.RED)

                                                .show();}
                                }


                            }// in case error
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();
                                if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                                    Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                            .setAction("Undo", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })
                                            .setActionTextColor(Color.RED)

                                            .show();}
                            }
                        }) {
                            //send data to server using POST
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("id",w);
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


         return convertView;

    }

}
