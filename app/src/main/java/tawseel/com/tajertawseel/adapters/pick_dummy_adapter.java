package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PickSet_data;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class pick_dummy_adapter extends BaseAdapter {

    Context context;
    LayoutInflater li;
    int selectedItem = -1;
    ArrayList<PickSet_data> List=new ArrayList<PickSet_data>();
    public pick_dummy_adapter(Context context,ArrayList<PickSet_data> list) {
        this.context = context;
        this.li = LayoutInflater.from(context);
        List=list;
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
        ViewHolder holder;
        if (convertView == null) {

            convertView= li.inflate(R.layout.pick_set_item, null, false);
            holder = new ViewHolder();
      holder.gid=(TextView)convertView.findViewById(R.id.gid);
           holder. gname=(TextView)convertView.findViewById(R.id.gname);
           holder. gorders=(TextView)convertView.findViewById(R.id.gorders);
            convertView.setTag(holder);
        }
            else
            holder=(ViewHolder)convertView.getTag();

            if(selectedItem!= -1)
            {
                if(position == selectedItem)
                {
                    holder.tickVisible(true,convertView,context);

                }
                else
                {
                    holder.tickVisible(false,convertView,context);
                }
            }
        PickSet_data data=(PickSet_data) getItem(position);
       holder. gid.setText(data.getGid());
      holder.  gname.setText(data.getGname());
      holder. gorders.setText(data.getGmembers());
        return convertView;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }
// getter for fetching the selected item position
    public int getSelectedItem() {
        return selectedItem;
    }
}
