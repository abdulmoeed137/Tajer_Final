package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PickSet_data;

/**
 * Created by Junaid-Invision on 7/3/2016.
 */
public class PickSetAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<PickSet_data> List=new ArrayList<PickSet_data>();

    public PickSetAdapter (Context c,ArrayList<PickSet_data> list)
    {
        List=list;
        context = c;
        inflater = LayoutInflater.from(c);
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

        View v = inflater.inflate(R.layout.pick_set_item,null,false);
        TextView gid=(TextView)v.findViewById(R.id.gid);
        TextView gname=(TextView)v.findViewById(R.id.gname);
        TextView gorders=(TextView)v.findViewById(R.id.gorders);

        PickSet_data data=(PickSet_data) getItem(position);
        v.setId(Integer.parseInt(data.getGid()));
        gid.setText(data.getGid());
        gname.setText(data.getGname());
        gorders.setText(data.getGmembers());

        return v;
    }
}
