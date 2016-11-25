package tawseel.com.tajertawseel.adapters;

/**
 * Created by AbdulMoeed on 11/24/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveryGroupData;
import tawseel.com.tajertawseel.activities.WaitingForAcceptanceActivity;
import tawseel.com.tajertawseel.fragments.HomeFragment3Data;

public class DeliveryGroupAdapter2Home extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    ArrayList<HomeFragment3Data> List;


    public DeliveryGroupAdapter2Home(Context c, ArrayList<HomeFragment3Data> list)
    {
        context = c ;
        inflater = LayoutInflater.from(c);
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

        ViewHolder holder ;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.delivery_group_list_item2, null, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.group_name);
            holder.noOfOrders  = (TextView) convertView.findViewById(R.id.quantity_customer);
            holder.btn = (TextView) convertView.findViewById(R.id.start_delivery_button);
            holder.ItemPrice = (TextView) convertView.findViewById(R.id.ItemsPrice);
            holder.PriceRange = (TextView) convertView.findViewById(R.id.PriceRange);
            holder.grpID = (TextView) convertView.findViewById(R.id.GroupID);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        HomeFragment3Data data = (HomeFragment3Data)getItem(position);

        holder.name.setText(data.getName());
        holder.noOfOrders.setText(data.getNoOfOrders());
        holder.ItemPrice.setText(data.getItemPrice());
        holder.PriceRange.setText(data.getPriceRange());
        holder.grpID.setText(data.getGroupID());
        holder.btn.setTag(data.getGroupID());





        final TextView startDeliveryButton = (TextView)convertView.findViewById(R.id.start_delivery_button);

        startDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, WaitingForAcceptanceActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                i.putExtra("GroupID",v.getTag()+"");
                context.startActivity(i);


            }
        });

        return convertView;
    }
}
