package tawseel.com.tajertawseel.activities;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

/**
 * Created by AbdulMoeed on 7/13/2016.
 */
public class functions {
    public static String add="http://192.168.0.100/ms/";
  static public boolean isPasswordTrue(String name,Context context)
    {

        if (name.isEmpty()) {

            setStatus("Password Is Empty!",context);
            return false;
        }
        if (name.length()<=4)
        {
            setStatus("Password too short!",context);
            return false;
        }
        else return true;
    }
    boolean isIdTrue(String id,Context context)
    {
        if (id.isEmpty()) {
            setStatus("Id Is Empty!",context);
            return false;
        }
        if (id.length()<=3)
        {
            setStatus("Id too short!",context);
            return false;
        }
        else return true;
    }
    static public   boolean isEmailTrue(String email,Context context)
    {
        if (email.isEmpty()) {
            setStatus("Email Is Empty!",context);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            setStatus("Email not Correct!",context);
            return false;
        }
        else return true;
    }
    static  public void setStatus (String msg, Context context)
    {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
