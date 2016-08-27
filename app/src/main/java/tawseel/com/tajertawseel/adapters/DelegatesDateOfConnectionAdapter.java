package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DateOfConnectionsData;

/**
 * Created by Junaid-Invision on 8/18/2016.
 */
public class DelegatesDateOfConnectionAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater li;
    private ArrayList<DateOfConnectionsData> List=new ArrayList<>();

    public DelegatesDateOfConnectionAdapter (Context c,ArrayList<DateOfConnectionsData> list)
    {
        List=list;
        context =c;
        li = LayoutInflater.from(c);
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
        if(convertView== null ){


            DateOfConnectionsData data=(DateOfConnectionsData) getItem(position);
            if(data.getTitle().compareTo("")!=0){
                convertView = li.inflate(R.layout.date_tag,null,false);
                TextView date=(TextView)convertView.findViewById(R.id.dt_title);
                date.setText(data.getTitle());
            }
            else
            {
                convertView = li.inflate(R.layout.date_of_connections_list_item,null,false);
                TextView gname=(TextView)convertView.findViewById(R.id.group_name);
                TextView gid=(TextView)convertView.findViewById(R.id.GroupID);
                TextView time=(TextView)convertView.findViewById(R.id.gtime);
                TextView date=(TextView)convertView.findViewById(R.id.gdate);
                TextView dname=(TextView)convertView.findViewById(R.id.g_dname);
                TextView qc=(TextView) convertView.findViewById(R.id.g_quantity_customer);
                RatingBar bar=(RatingBar)convertView.findViewById(R.id.g_drating);
                gname.setText(data.getGname());
                gid.setText(data.getGid());
                time.setText(data.getTime());
                date.setText(data.getDate());
                dname.setText(data.getDname());
                qc.setText(data.getDelivers());
                bar.setRating(Float.parseFloat(data.getStars()));
            }



        }
        return convertView;
    }
}
