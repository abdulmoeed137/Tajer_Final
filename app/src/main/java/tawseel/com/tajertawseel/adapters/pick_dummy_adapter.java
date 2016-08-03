package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class pick_dummy_adapter extends BaseAdapter {

    Context context;
    LayoutInflater li;

    public pick_dummy_adapter(Context context) {
        this.context = context;
        this.li = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 15;
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
        return li.inflate(R.layout.pick_set_item,null,false);
    }
}
