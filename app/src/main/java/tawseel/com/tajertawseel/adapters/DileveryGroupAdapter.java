package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeliveryGroupData;
import tawseel.com.tajertawseel.activities.WaitingForAcceptanceActivity;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class DileveryGroupAdapter extends BaseAdapter {
   private Context context;
    private LayoutInflater inflater;
    ArrayList<DeliveryGroupData> List;


    public DileveryGroupAdapter (Context c, ArrayList<DeliveryGroupData> list)
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

        if (convertView == null)
        {
            convertView  = inflater.inflate(R.layout.delivery_group_list_item,null,false);
            TextView grpName = (TextView)convertView.findViewById(R.id.group_name);
            TextView qty_cust= (TextView)convertView.findViewById(R.id.quantity_customer);
           TextView btn = (TextView) convertView.findViewById(R.id.start_delivery_button);
            DeliveryGroupData data = (DeliveryGroupData)getItem(position);
            btn.setTag("lol");
            grpName.setText(data.getName());
            qty_cust.setText(data.getNoOfOrders());

        }


        final TextView startDeliveryButton = (TextView)convertView.findViewById(R.id.start_delivery_button);

        startDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Intent i = new Intent(context, WaitingForAcceptanceActivity.class);
                i.putExtra("grpId",v.getId()+"abcd");
                context.startActivity(i);

            }
        });

        return convertView;
    }
}
