package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class DealerProfileActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dealer);

        setUpToolbar();

    }

    public void setUpToolbar() {
      Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView) findViewById(R.id.title_text);
        toolbarTitle.setText(getString(R.string.dealer_profile));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
