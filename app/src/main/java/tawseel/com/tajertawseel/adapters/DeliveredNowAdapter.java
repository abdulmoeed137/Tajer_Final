package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 8/9/2016.
 */
public class DeliveredNowAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;


    public DeliveredNowAdapter(Context c)
    {
        context = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView  = inflater.inflate(R.layout.delivered_now_list_item,null,false);

        }
        final TextView moreView = (TextView) convertView.findViewById(R.id.moreButton);
        ExpandablePanel panel = (ExpandablePanel)convertView.findViewById(R.id.expandableLayout);
        final ListView productsList = (ListView) convertView.findViewById(R.id.product_list);
    productsList.setAdapter(new DeliveredNowChildItemAdapter(context));
        productsList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

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
        return convertView ;
    }
}
