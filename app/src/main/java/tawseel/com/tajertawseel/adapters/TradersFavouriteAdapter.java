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
import tawseel.com.tajertawseel.activities.FavouriteDelegateItemData;
import tawseel.com.tajertawseel.activities.FavouriteSellerItemData;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class TradersFavouriteAdapter extends BaseAdapter {



    private Context c;
    private LayoutInflater li;
    private ArrayList<FavouriteSellerItemData> List=new ArrayList<FavouriteSellerItemData>();

    public TradersFavouriteAdapter (Context c,ArrayList<FavouriteSellerItemData> list)
    {
        List=list;
        this.c = c;
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

        if (convertView == null)
        {
            convertView = li.inflate(R.layout.delegates_question_list_item,null,false);
            TextView delivers=(TextView)convertView.findViewById(R.id.ndelivers);
            RatingBar bar=(RatingBar)convertView.findViewById(R.id.ratingbar);
            TextView name=(TextView)convertView.findViewById(R.id.delegate_name);
            FavouriteSellerItemData data=(FavouriteSellerItemData) getItem(position);
            delivers.setText(data.getNdelivers());
            name.setText(data.getName());
            bar.setRating(Float.parseFloat(data.getStars()));
        }


        return convertView ;
    }
}
