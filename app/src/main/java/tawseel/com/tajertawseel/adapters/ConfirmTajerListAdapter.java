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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.TjerMapActivity;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ConfirmTajerListAdapter extends BaseAdapter {


    Context c;
    LayoutInflater inflater;


    public ConfirmTajerListAdapter(Context context) {
        c = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.confirm_tajer_list_item, null, false);

        }

        convertView.findViewById(R.id.BtnAlert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDialogue(c);
            }
        });
        convertView.findViewById(R.id.BtnMove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(c, TjerMapActivity.class);
                c.startActivity(i);
            }
        });


        return convertView;
    }


    public void showNotificationDialogue(Context c) {
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.confirmation_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        // lp.copyFrom(c.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
//        ListView lv = (ListView) dialog.findViewById(R.id.ordersList);
//        lv.setAdapter(new OrdeDialogueAdapter(c));
        dialog.show();
    }
}
