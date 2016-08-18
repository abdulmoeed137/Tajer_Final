package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/18/2016.
 */
public class DelegatesDateOfConnectionAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater li;


    public DelegatesDateOfConnectionAdapter (Context c)
    {
        context =c;
        li = LayoutInflater.from(c);
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
            if (position == 0 || position == 5) {
                convertView = li.inflate(R.layout.date_tag, null, false);
            } else {
                convertView = li.inflate(R.layout.date_of_connections_list_item, null, false);
            }
        }
        return convertView;
    }
}
