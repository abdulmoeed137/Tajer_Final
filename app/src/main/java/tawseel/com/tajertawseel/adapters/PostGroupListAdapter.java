package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveryGroupData;
import tawseel.com.tajertawseel.activities.PostGroupData;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class PostGroupListAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<PostGroupData>List;


    public PostGroupListAdapter (Context c   ,ArrayList<PostGroupData> list)
    {
        context = c;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v  = inflater.inflate(R.layout.post_group_item,null,false);


        TextView CustomerName = (TextView) v.findViewById(R.id.CustomerName);
    TextView email = (TextView) v.findViewById(R.id.Email);
    TextView phone = (TextView) v.findViewById(R.id.Phone);
    final PostGroupData data = (PostGroupData) getItem(position);
    CustomerName.setText(data.getCustomerName());
    email.setText(data.getCustomerEmail());
    phone.setText(data.getCustomerPhone());


    final TextView moreView = (TextView) v.findViewById(R.id.moreButton);
    ExpandablePanel panel = (ExpandablePanel) v.findViewById(R.id.expandableLayout);


    panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
        @Override
        public void onExpand(View handle, View content) {
            moreView.setText(content.getResources().getString(R.string.less));
            View PriceRange = (View) content.findViewById(R.id.PriceMark);
            TextView riyalPrice = (TextView) content.findViewById(R.id.riyalPrice);
            TextView ItemPrice = (TextView) content.findViewById(R.id.ItemsPrice);
            TextView PriceRangeText = (TextView) content.findViewById(R.id.PriceRange);
            TextView TotalPrice = (TextView) content.findViewById(R.id.TotalPrice);
            ItemPrice.setText(data.getItemsPrice());
            PriceRangeText.setText(data.getPriceRange());
            TotalPrice.setText(Integer.parseInt(data.getItemsPrice()) + (Integer.parseInt(data.getPriceRange())) + "");
            if (data.getPriceRange().equals("20")) {
                PriceRange.setBackgroundResource(R.drawable.solid_green_circle);
                riyalPrice.setText(R.string.ryal_20);

            } else if (data.getPriceRange().equals("30")) {
                PriceRange.setBackgroundResource(R.drawable.orange_circle);
                riyalPrice.setText(R.string.ryal30);
            } else if (data.getPriceRange().equals("40")) {
                PriceRange.setBackgroundResource(R.drawable.maroon_circle);
                riyalPrice.setText(R.string.ryal40);
            } else if (data.getPriceRange().equals("50")) {
                PriceRange.setBackgroundResource(R.drawable.red_circle);
                riyalPrice.setText(R.string.ryal50);
            }


        }

        @Override
        public void onCollapse(View handle, View content) {

            moreView.setText(content.getResources().getString(R.string.more));

        }
    });


    //  CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);

    ListView productsList = (ListView) v.findViewById(R.id.product_list);
    productsList.setAdapter(new ProductItemAdapter(context));


    productsList.setOnTouchListener(new View.OnTouchListener() {
        // Setting on Touch Listener for handling the touch inside ScrollView
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // Disallow the touch request for parent scroll on touch of child view
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    });


    return v;

    }

}
