package tawseel.com.tajertawseel.firebase;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.firebase.messaging.RemoteMessage;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.DeligateNotificationData;
import tawseel.com.tajertawseel.activities.NotificationOrderDetails;
import tawseel.com.tajertawseel.activities.dialog;
import tawseel.com.tajertawseel.adapters.OrdeDialogueAdapter;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("GroupID"));
    }

    private void showNotification(String message,String GroupID) {

        Intent i = new Intent(this,NotificationOrderDetails.class);
        i.putExtra("id",GroupID);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Tawseel")
                .setContentText(message)
                .setSmallIcon(R.drawable.tjer)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
        
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
