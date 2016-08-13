package tawseel.com.tajertawseel.firebase;

import android.os.StrictMode;

import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import tawseel.com.tajertawseel.activities.LoginActivity;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    public void registerToken(String token, String DeligateID,String DeviceID) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()

                .add("Token",token).add("DeviceID", DeviceID).add("DeligateID",DeligateID).add("hash","CCB612R")

                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.100/ms/PushNotificationReg.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
