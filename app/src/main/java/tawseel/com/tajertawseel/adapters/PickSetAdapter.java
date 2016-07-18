package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/3/2016.
 */
public class PickSetAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;


    public PickSetAdapter (Context c)
    {
        context = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 13;
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

        View v = inflater.inflate(R.layout.pick_set_item,null,false);

        return v;
    }
}
