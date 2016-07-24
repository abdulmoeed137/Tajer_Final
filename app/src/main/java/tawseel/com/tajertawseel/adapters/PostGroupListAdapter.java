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

      final  ViewHolder holder;
        final PostGroupData data = (PostGroupData) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();

        convertView= inflater.inflate(R.layout.post_group_item, null, false);

            holder.CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
            holder.CustomerEmail = (TextView) convertView.findViewById(R.id.Email);
            holder.CustomerPhone = (TextView) convertView.findViewById(R.id.Phone);
            convertView.setTag(holder);

         final TextView moreView = (TextView) convertView.findViewById(R.id.moreButton);
        ExpandablePanel panel = (ExpandablePanel) convertView.findViewById(R.id.expandableLayout);


    panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
        @Override
        public void onExpand(View handle, View content) {
            moreView.setText(content.getResources().getString(R.string.less));
           holder.PriceRangeIcon = (View) content.findViewById(R.id.PriceMark);
            holder.PriceRange2 = (TextView) content.findViewById(R.id.riyalPrice);
            holder.ItemsPrice = (TextView) content.findViewById(R.id.ItemsPrice);
           holder.PriceRangeText = (TextView) content.findViewById(R.id.PriceRange);
            holder.TotalPrice= (TextView) content.findViewById(R.id.TotalPrice);
            holder.ItemsPrice.setText(data.getItemsPrice());
            holder.PriceRangeText.setText(data.getPriceRange());
            holder.TotalPrice.setText(Integer.parseInt(data.getItemsPrice()) + (Integer.parseInt(data.getPriceRange())) + "");
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

        }

        @Override
        public void onCollapse(View handle, View content) {

            moreView.setText(content.getResources().getString(R.string.more));

        }
    });


    //  CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);

    ListView productsList = (ListView) convertView.findViewById(R.id.product_list);
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
        }
        else
            holder=(ViewHolder) convertView.getTag();
         holder.CustomerName.setText(data.getCustomerName());
        holder.CustomerEmail.setText(data.getCustomerEmail());
        holder.CustomerPhone.setText(data.getCustomerPhone());

         return convertView;

    }

}
