package tawseel.com.tajertawseel.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.OrdeDialogueAdapter;

/**
 * Created by AbdulMoeed on 8/18/2016.
 */
public class dialog extends BaseActivity {
    static public PendingIntent pendingIntent;
    static public AlarmManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_dialog);
      finish();
       showNotificationDialogue(this);

    }
    public void showNotificationDialogue(Context c)
    {
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.order_notification_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        // lp.copyFrom(c.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        ListView lv = (ListView)dialog.findViewById(R.id.ordersList);
        lv.setAdapter(new OrdeDialogueAdapter(c));
        dialog.show();


        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),HomePickSetActivity.class);
//                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getActivity(), AddNewOrderActivity.class));
            }
        });

    }

}
