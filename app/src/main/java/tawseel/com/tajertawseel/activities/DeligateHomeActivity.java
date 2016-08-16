package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)  {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

            }
        }

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
            Intent i = new Intent(DeligateHomeActivity.this,UpdateLocation.class);
            startService(i);
        }
    }

}
