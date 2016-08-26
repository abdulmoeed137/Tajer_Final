package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class TradersFavouriteAdapter extends BaseAdapter {



    private Context c;
    private LayoutInflater li;

    public TradersFavouriteAdapter (Context c)
    {
        this.c = c;
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

        if (convertView == null)
        {
            convertView = li.inflate(R.layout.delegates_question_list_item,null,false);
        }


        return convertView ;
    }
}
