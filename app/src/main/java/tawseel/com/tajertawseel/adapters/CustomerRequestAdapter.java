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

import java.util.ArrayList;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.Customer_request_item_data;
import tawseel.com.tajertawseel.activities.PickSetActivity;
import tawseel.com.tajertawseel.activities.ProductLayoutData;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 7/3/2016.
 * Edit: M Monis
 */
public class CustomerRequestAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Customer_request_item_data> List;
    ProductLayoutData List1[];

    public CustomerRequestAdapter (Context c, ArrayList<Customer_request_item_data> list, ProductLayoutData list1[])
    {
        List=list;
        List1=list1;
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

        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            @Override
            public void onExpand(View handle, View content) {

                moreView.setText(content.getResources().getString(R.string.less));

            }

            @Override
            public void onCollapse(View handle, View content) {

                moreView.setText(content.getResources().getString(R.string.more));

            }
        });


        CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);

        ListView productsList = (ListView)v.findViewById(R.id.product_list);
        productsList.setAdapter(new ProductItemAdapter(context,List1[position].getItems()));
        View PriceRangeIcon=(View)v.findViewById(R.id.PriceMark);
        TextView PriceRange2=(TextView)v.findViewById(R.id.riyalPrice);
        TextView ItemsPrice=(TextView)v.findViewById(R.id.ItemsPrice);
        TextView PriceRangeText=(TextView)v.findViewById(R.id.PriceRange);
        TextView TotalPrice=(TextView)v.findViewById(R.id.TotalPrice);
        TextView PayMethod=(TextView)v.findViewById(R.id.PaymentType);

        ProductLayoutData data1=List1[position];
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

        productsList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
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
