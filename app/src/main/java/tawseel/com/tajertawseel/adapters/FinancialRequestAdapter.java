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
        View v= inflater.inflate(R.layout.financial_history_item,null,false);
        TextView name=(TextView)v.findViewById(R.id.fhl_name);
        TextView items=(TextView)v.findViewById(R.id.fhl_OrderProductQuantity);
        TextView dprice=(TextView)v.findViewById(R.id.fhl_delivery);
        TextView dprice1=(TextView)v.findViewById(R.id.fhl_delivery1);
        TextView iprice=(TextView)v.findViewById(R.id.fhl_items);
        TextView profit=(TextView)v.findViewById(R.id.fhl_ttprofit);
        TextView finalp=(TextView)v.findViewById(R.id.fhl_final);

        FinancialHistoryData temp=(FinancialHistoryData) getItem(position);

        name.setText(temp.getDname());
        items.setText(temp.getNitems());
        dprice.setText(temp.getDeliveryprice());
        dprice1.setText(temp.getDeliveryprice());
        iprice.setText(temp.getItemprice());
        double profitint=Double.parseDouble(temp.getItemprice())*0.05;
        double finalint=Double.parseDouble(temp.getItemprice())-profitint;
        profit.setText(String.valueOf((profitint)));
        finalp.setText(String.valueOf(finalint));
        return v;
    }
}
