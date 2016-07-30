package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/29/2016.
 */
public class DateOfConnectionsAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Context context;

    public DateOfConnectionsAdapter (Context c)
    {
       context =c;
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

        if(convertView== null ){
        if(position == 7 || position == 5 || position== 1)
        convertView = inflater.inflate(R.layout.date_tag,null,false);
        else
        convertView = inflater.inflate(R.layout.date_of_connections_list_item,null,false);
        }

        return convertView;
    }
}
