package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DeliveredNowAdapter;

/**
 * Created by Junaid-Invision on 8/9/2016.
 */
public class DeliveredNowActivity extends BaseActivity {

    private RequestQueue requestQueue;
    ListView mListView ;
    String GroupID,ConfirmationCode,StatusCode,GroupName,DeligateName,ItemPrice2,PriceRange2,DeligateNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delivered_now);
        setupToolbar();
        setupComponents();
    }

    private void setupComponents() {
        requestQueue= Volley.newRequestQueue(this);
        mListView = (ListView)findViewById(R.id.mListView);

        mListView.setAdapter(new DeliveredNowAdapter(this));
        GroupID=getIntent().getExtras().getString("GroupID");
        ConfirmationCode=getIntent().getExtras().getString("ConfirmationCode");
        StatusCode=getIntent().getExtras().getString("StatusCode");
        GroupName=getIntent().getExtras().getString("GroupName");
        DeligateName=getIntent().getExtras().getString("DeligateName");
        PriceRange2=getIntent().getExtras().getString("ItemPrice");
        ItemPrice2=getIntent().getExtras().getString("PriceRange");
        DeligateNumber=getIntent().getExtras().getString("DeligateNumber");

        TextView header = (TextView)findViewById(R.id.header);
        TextView groupid = (TextView)findViewById(R.id.GroupID);
        TextView StatusBox = (TextView)findViewById(R.id.BtnStatus) ;
        TextView DeligateNameTextField= (TextView)findViewById(R.id.DeligateName) ;
        TextView ConfirmCode = (TextView)findViewById(R.id.StatusText);
        TextView PriceRange = (TextView)findViewById(R.id.PriceRange) ;
        TextView ItemPrice = (TextView)findViewById(R.id.ItemPrice) ;
        ImageView CallDeligate= (ImageView) findViewById(R.id.BtnDeligateCall) ;

        PriceRange.setText(PriceRange2+"");
        ItemPrice.setText(ItemPrice2+"");
       ConfirmCode.setText(ConfirmationCode+"");
       DeligateNameTextField.setText(DeligateName+"");
        groupid.setText(GroupID+"");
        header.setText(GroupName+"");
        if (StatusCode.toString().equals("1")) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                StatusBox.setBackgroundResource(R.drawable.red_rectangle);

            } else {
                StatusBox.setBackground(getResources().getDrawable(R.drawable.red_rectangle));
            }
            StatusBox.setTextColor(Color.RED);
        }

        assert CallDeligate != null;
        CallDeligate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+DeligateNumber));
                if (ActivityCompat.checkSelfPermission(DeliveredNowActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DeliveredNowActivity.this,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });


    }


    public void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.dilevered_now));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
