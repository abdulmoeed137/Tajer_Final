package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PostGroupData;
import tawseel.com.tajertawseel.activities.PostGroupListData;

/**
 * Created by Junaid-Invision on 7/9/2016.
 */
public class ProductItemAdapter extends BaseAdapter {

    ArrayList<PostGroupListData> List;
    private LayoutInflater inflater;
    private Context context;

    public ProductItemAdapter(Context c, ArrayList<PostGroupListData> list) {
        List = list;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_product_item, null, false);
            holder.ProductName = (TextView) convertView.findViewById(R.id.ProductName);
            holder.Description = (TextView) convertView.findViewById(R.id.ProductDescription);
            holder.ProductPrice = (TextView) convertView.findViewById(R.id.ProductPrice);
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();
        PostGroupListData data = (PostGroupListData) getItem(position);
        holder.ProductName.setText(data.getQuantity()+" - "+data.getProductName());
        holder.ProductPrice.setText(data.getPrice());
        holder.Description.setText(data.getDescription());
        return convertView;

    }
}