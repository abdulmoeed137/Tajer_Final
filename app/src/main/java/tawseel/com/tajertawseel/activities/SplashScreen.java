package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tawseel.com.tajertawseel.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logoImage = (ImageView)findViewById(R.id.logo_imageView);
        Glide.with(this).load(R.drawable.splash_logo3).into(logoImage);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i= new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        },3000);
    }
}
