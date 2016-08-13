package tawseel.com.tajertawseel.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.customviews.ExpandablePanel;

/**
 * Created by AbdulMoeed on 7/24/2016.
 */
public class ViewHolder {
    //DeliverGroupAdapter
    TextView name, noOfOrders, btn, grpID, ItemPrice, PriceRange;
    //PostGroupAdapter
    TextView PriceRange2, PayMethod, ItemsPrice, CustomerName, CustomerEmail, CustomerPhone, OrderID, PriceRangeText, TotalPrice, OrderProductQuantity;
    View PriceRangeIcon;
    ImageView OrderMoveButton, OrderDeleteButton;
    //product GroupList

    TextView Quantity, ProductID, ProductName, ProductPrice, Description;


    //PickSetAdapter
    TextView gid, gname, gorders;

    public void tickVisible(boolean visible, View view, Context c) {
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
        ImageView tickView = (ImageView) layout.findViewById(R.id.tick_view);
        if (visible) {
            tickView.setVisibility(View.VISIBLE);
            layout.setBackgroundColor(c.getResources().getColor(R.color.grey));
        } else {
            tickView.setVisibility(View.INVISIBLE);
            layout.setBackgroundColor(c.getResources().getColor(android.R.color.transparent));
        }
    }

    //
    TextView Name, Member, GroupID, StatusCode, ConfrmatonCode, DeliveryCode;
ImageView BtnCall;
   public void CallDeligate(final Context c, View vb) {
        final Context c2=c;
        ImageView img = (ImageView) vb.findViewById(R.id.BtnCall);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+v.getTag()));
                if (ActivityCompat.checkSelfPermission(c2, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                  Toast.makeText(c2,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    return;
                }
                c2.startActivity(callIntent);
            }
        });

    }
TextView moreView; ExpandablePanel panel;ListView productsList;
}
