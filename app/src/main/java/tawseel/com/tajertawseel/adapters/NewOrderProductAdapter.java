package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.PostGroupListData;

/**
 * Created by Junaid-Invision on 7/10/2016.
 */
public class NewOrderProductAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PostGroupListData> List=new ArrayList<PostGroupListData>();



  public NewOrderProductAdapter(Context c,ArrayList<PostGroupListData> list)
  {
      List=list;
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
        View view=inflater.inflate(R.layout.product_item_cancellable,null,false);
        TextView title=(TextView)view.findViewById(R.id.cancel_item_title);
        TextView desc=(TextView)view.findViewById(R.id.cancel_item_desc);
        TextView price=(TextView)view.findViewById(R.id.cancel_item_price);
        ImageView cancel=(ImageView)view.findViewById(R.id.cancel_item_iv);
        final PostGroupListData data=(PostGroupListData) getItem(position);
        title.setText(data.getProductName()+"-"+data.getQuantity());
        desc.setText(data.getDescription());
        price.setText(data.getPrice());
        cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        for(int i=0;i<List.size();i++)
            if(List.get(i).getProductName().compareTo(data.getProductName())==0)
            {
                List.remove(i);
                break;
            }
        ((BaseAdapter)NewOrderProductAdapter.this).notifyDataSetChanged();
    }
});
        return view;
    }
}
