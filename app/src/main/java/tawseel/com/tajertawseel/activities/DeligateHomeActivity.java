package tawseel.com.tajertawseel.activities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.firebase.FirebaseInstanceIDService;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class DeligateHomeActivity extends BaseActivity {
    static String DeligateID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_screen);
        setUpContents();
    }


    private void setUpContents() {
        boolean CheckLogin=false;
        try {
          CheckLogin = getIntent().getExtras().getBoolean("flag");
        }
        catch (Exception e)
        {}
        if (CheckLogin) {
            CheckLogin=false;
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            FirebaseInstanceIDService dd = new FirebaseInstanceIDService();
            String token = FirebaseInstanceId.getInstance().getToken();
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            DeligateID = getIntent().getExtras().getString("DeligateID");

            dd.registerToken(token, DeligateID, wInfo.getMacAddress() + "");
        }
    }

}
