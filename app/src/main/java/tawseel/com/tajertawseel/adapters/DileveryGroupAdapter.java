package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.WaitingForAcceptanceActivity;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class DileveryGroupAdapter extends BaseAdapter {
   private Context context;
    private LayoutInflater inflater;



    public DileveryGroupAdapter (Context c)
    {
        context = c ;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        return 5;
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
            convertView  = inflater.inflate(R.layout.delivery_group_list_item,null,false);
        }


        final TextView startDeliveryButton = (TextView)convertView.findViewById(R.id.start_delivery_button);

        startDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, WaitingForAcceptanceActivity.class);
                context.startActivity(i);

            }
        });

        return convertView;
    }
}
