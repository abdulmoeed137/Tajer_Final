package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import tawseel.com.tajertawseel.R;

/**
 * Created by Monis on 7/27/2016.
 */

public class Tajer_Lap_Activity extends BaseActivity {

    Button newButton,oldButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tajer_lap);
        newButton =(Button)findViewById(R.id.new_order_button_);
        oldButton=(Button)findViewById(R.id.old_order_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent (Tajer_Lap_Activity.this, New_Orders_Activity.class);
                startActivity(i);
                finish();
            }
        });

        oldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent (Tajer_Lap_Activity.this, Old_Orders_Activity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
