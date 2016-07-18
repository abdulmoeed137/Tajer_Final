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

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PickSetActivity;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 7/3/2016.
 */
public class CustomerRequestAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;


    public CustomerRequestAdapter (Context c)
    {
        context = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        return 4;
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
        View v  = inflater.inflate(R.layout.customer_request_item,null,false);

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


        CustomBoldTextView textView = (CustomBoldTextView) v.findViewById(R.id.start_delivery_button);
//        Spinner layout = (Spinner) v.findViewById(R.id.popup_view);
//
//        layout.setAdapter(new ListPopupAdapter(context));



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

    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent(context, PickSetActivity.class);
            context.startActivity(intent);

        }
    });



        return v;

    }
}
