package tawseel.com.tajertawseel.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;

/**
 * Created by AbdulMoeed on 7/24/2016.
 */
public class ViewHolder {
     //DeliverGroupAdapter
     TextView name ,noOfOrders,btn,grpID,ItemPrice,PriceRange;
     //PostGroupAdapter
     TextView PriceRange2,PayMethod,ItemsPrice,CustomerName,CustomerEmail,CustomerPhone,OrderID,PriceRangeText,TotalPrice,OrderProductQuantity;
     View PriceRangeIcon;
     ImageView OrderMoveButton,OrderDeleteButton;
     //product GroupList

     TextView Quantity ,ProductID ,ProductName, ProductPrice ,Description;


     //PickSetAdapter
     TextView gid,gname,gorders;

     public void tickVisible (boolean visible, View view, Context c)
     {    RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
          ImageView tickView = (ImageView) layout.findViewById(R.id.tick_view);
          if (visible){
               tickView.setVisibility( View.VISIBLE);
               layout.setBackgroundColor(c.getResources().getColor(R.color.grey));
          }
          else
          {
               tickView.setVisibility( View.INVISIBLE);
               layout.setBackgroundColor(c.getResources().getColor(android.R.color.transparent));
          }
     }

}
