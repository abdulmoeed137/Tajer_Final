package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tawseel.com.tajertawseel.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings;

        settings = this.getSharedPreferences("tajer", Context.MODE_PRIVATE); //1
        String Tid = settings.getString("id", null);

        SharedPreferences settings2;

        settings2 = this.getSharedPreferences("deligate", Context.MODE_PRIVATE); //1
        String Did = settings2.getString("id2", null);
        if (Tid!=null) {
            LoginActivity.LoginID = Tid;
            LoginActivity.uname= settings.getString("uname", null);
            LoginActivity.email= settings.getString("email",null);
            finish();
            startActivity(new Intent(SplashScreen.this, HomeActivity.class));
        }else if (Did!=null) {
            LoginActivity.DeligateID = Did;
            LoginActivity.email=settings2.getString("email",null);
            finish();
            startActivity(new Intent(SplashScreen.this, DeligateHomeActivity.class));
        }else {

            setContentView(R.layout.activity_splash_screen);
            ImageView logoImage = (ImageView) findViewById(R.id.logo_imageView);
            Glide.with(this).load(R.drawable.splash_logo3).into(logoImage);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                //this code will be executed on devices running ICS or later

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 100);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                            startActivity(i);
                            finish();

                        }
                    }, 3000);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    }
                }, 3000);
            }
        }

    }
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                Intent i= new Intent(SplashScreen.this,LoginActivity.class);
                                startActivity(i);
                                finish();

                            }
                        },3000);

                    }

                }  else
                {
                    Toast.makeText(SplashScreen.this,"Permission Failed!",Toast.LENGTH_SHORT).show();finish();
                    finish();
                }
            }
        }
    }
}
