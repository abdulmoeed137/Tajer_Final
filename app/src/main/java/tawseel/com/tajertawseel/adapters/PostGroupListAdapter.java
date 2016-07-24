package tawseel.com.tajertawseel.adapters;

import android.content.Context;
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





        final TextView moreView = (TextView) v.findViewById(R.id.moreButton);
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


      //  CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);

        ListView productsList = (ListView)v.findViewById(R.id.product_list);
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
