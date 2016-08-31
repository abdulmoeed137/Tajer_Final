package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.FinancialHistoryData;

/**
 * Created by Junaid-Invision on 8/13/2016.
 */
public class FinancialRequestAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<FinancialHistoryData> Data;

    public FinancialRequestAdapter(Context c,ArrayList<FinancialHistoryData> data)
    {
        Data=data;
        context = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.financial_history_item, null, false);
            holder= new ViewHolder();
            holder.name2 = (TextView) convertView.findViewById(R.id.fhl_name);
            holder.items = (TextView) convertView.findViewById(R.id.fhl_OrderProductQuantity);
            holder.dprice = (TextView) convertView.findViewById(R.id.fhl_delivery);
            holder.dprice1 = (TextView) convertView.findViewById(R.id.fhl_delivery1);
            holder.iprice = (TextView) convertView.findViewById(R.id.fhl_items);
            holder.profit = (TextView) convertView.findViewById(R.id.fhl_ttprofit);
            holder. finalp = (TextView) convertView.findViewById(R.id.fhl_final);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        FinancialHistoryData temp=(FinancialHistoryData) getItem(position);

        holder.items.setText(temp.getNitems());
        holder.dprice.setText(temp.getDeliveryprice());
        holder.dprice1.setText(temp.getDeliveryprice());
        holder.iprice.setText(temp.getItemprice());
        double profitint=Double.parseDouble(temp.getItemprice())*0.05;
        double finalint=Double.parseDouble(temp.getItemprice())-profitint;
        holder.profit.setText(String.valueOf((profitint)));
        holder. finalp.setText(String.valueOf(finalint));
        return convertView;
    }
}
