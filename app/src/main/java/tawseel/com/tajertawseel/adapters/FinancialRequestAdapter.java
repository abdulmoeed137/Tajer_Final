package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/13/2016.
 */
public class FinancialRequestAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public FinancialRequestAdapter(Context c)
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
        return inflater.inflate(R.layout.financial_history_item,null,false);
    }
}
