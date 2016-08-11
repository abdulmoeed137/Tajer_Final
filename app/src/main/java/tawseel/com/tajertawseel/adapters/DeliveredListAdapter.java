package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.Manifest;


import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/2/2016.
 */
public class DeliveredListAdapter extends BaseAdapter {
ArrayList<HomeFragment2Data> List = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    public DeliveredListAdapter (Context c,ArrayList<HomeFragment2Data> list){
      context =c;
        List=list;
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

        ViewHolder holder ;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.delivered_list_item,null,false);
            holder = new ViewHolder();
            holder.Member = (TextView) convertView.findViewById(R.id.members);
            holder.BtnCall= (ImageView)convertView.findViewById(R.id.BtnCall);



           holder.Name= (TextView)convertView.findViewById(R.id.Name) ;
            holder.GroupID = (TextView) convertView.findViewById(R.id.GroupID);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

       HomeFragment2Data data = (HomeFragment2Data)getItem(position);

        holder.Name.setText(data.getName());

        holder.GroupID.setText(data.getGroupID());
         holder.Member.setText(data.getMembers());
        holder.BtnCall.setTag(data.getDeligateContact());
      holder.CallDeligate(context,convertView);

        return convertView;
    }



}
