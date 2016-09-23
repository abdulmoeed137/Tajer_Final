package tawseel.com.tajertawseel.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

/**
 * Created by AbdulMoeed on 7/13/2016.
 */
public class functions {
    public static String add="http://roadofchange.org/BaikTajerTawseel/";
    public static String  AvailableDeligates;
    static boolean keepRunning=true;
    public static String bg = "#EEEDED";

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
    static public boolean isNameTrue(String name,Context context)
    {

        if (name.isEmpty()) {

            setStatus("Name Is Empty!",context);
            return false;
        }
        if (name.length()<=4)
        {
            setStatus("Name too short!",context);
            return false;
        }
        else return true;
    }
  static  boolean isIdTrue(String id,Context context)
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
