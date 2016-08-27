package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by Junaid-Invision on 8/27/2016.
 */
public class BruaalDileveryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;


    public BruaalDileveryAdapter(Context c){
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

        if(convertView == null)
        {
            convertView= inflater.inflate(R.layout.brual_delivery_item, null, false);


        }

        final ListView productsList = (ListView) convertView.findViewById(R.id.product_list);

        final TextView moreView = (TextView) convertView.findViewById(R.id.moreButton);

        ExpandablePanel panel = (ExpandablePanel) convertView.findViewById(R.id.expandableLayout);

        productsList.setAdapter(new ProductItemAdapter(context,null));

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


        return convertView;
    }
}
