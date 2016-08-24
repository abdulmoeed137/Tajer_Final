package tawseel.com.tajertawseel.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TooManyListenersException;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PostGroupData;
import tawseel.com.tajertawseel.activities.PostGroupListData;
import tawseel.com.tajertawseel.activities.TjerMapActivity;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ConfirmTajerListAdapter extends BaseAdapter {

    ArrayList<PostGroupData> List = new ArrayList<>();
    Context c;
    LayoutInflater inflater;


    public ConfirmTajerListAdapter(Context context , ArrayList<PostGroupData> list) {
        c = context;
        inflater = LayoutInflater.from(context);
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

        final  ViewHolder holder;
         PostGroupData data = (PostGroupData) getItem(position);
        if (convertView == null) {
           holder = new ViewHolder();

            convertView= inflater.inflate(R.layout.confirm_tajer_list_item, null, false);

            holder.CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
            holder.CustomerEmail = (TextView) convertView.findViewById(R.id.Email);
            holder.CustomerPhone = (TextView) convertView.findViewById(R.id.Phone);
            holder.OrderProductQuantity= (TextView)convertView.findViewById(R.id.OrderProductQuantity);
convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        holder.CustomerName.setText(data.getCustomerName());
        holder.CustomerEmail.setText(data.getCustomerEmail());
        holder.CustomerPhone.setText(data.getCustomerPhone());
        holder.OrderProductQuantity.setText(data.getOrderProductQuantity());




        return convertView;
    }
}
