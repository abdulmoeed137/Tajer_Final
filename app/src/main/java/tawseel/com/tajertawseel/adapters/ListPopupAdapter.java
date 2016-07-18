package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/14/2016.
 */
public class ListPopupAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] Ryals;
    private int[] circleIds  = {R.drawable.solid_green_circle,R.drawable.orange_circle,R.drawable.red_circle,R.drawable.maroon_circle};

    public ListPopupAdapter (Context c)
    {
        inflater = LayoutInflater.from(c);
        context = c;
        Ryals = c.getResources().getStringArray(R.array.ryal_array);
    }

    @Override
    public int getCount() {
        return Ryals.length;
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
        convertView =  inflater.inflate(R.layout.popup_list_item,null,false);

        TextView ryalTextView  = (TextView)convertView.findViewById(R.id.ryal_textView);
        ryalTextView.setText(Ryals[position]);
        View circleView = (View) convertView.findViewById(R.id.circle_view);
        final int sdk = android.os.Build.VERSION.SDK_INT;
       if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            circleView.setBackgroundDrawable(context. getResources().getDrawable(circleIds[position]) );
        } else {
            circleView.setBackground(context. getResources().getDrawable(circleIds[position]));
       }
        return convertView;
    }
}
