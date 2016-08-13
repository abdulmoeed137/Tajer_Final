package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import tawseel.com.tajertawseel.R;

/**
 * Created by AbdulMoeed on 8/13/2016.
 */
public class DeligateNotificationData extends BaseActivity {
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deligate_notification_data);
        setUpContents();
    }

    private void setUpContents() {
        final EditText GroupInfoText = (EditText)findViewById(R.id.GroupInfoText) ;
        requestQueue = Volley.newRequestQueue(this);
        Toast.makeText(DeligateNotificationData.this,getIntent().getExtras().getString("GroupID"),Toast.LENGTH_SHORT).show();
    }
}
