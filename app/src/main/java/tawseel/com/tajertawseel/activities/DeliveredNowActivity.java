package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DeliveredNowAdapter;

/**
 * Created by Junaid-Invision on 8/9/2016.
 */
public class DeliveredNowActivity extends BaseActivity {


    ListView mListView ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delivered_now);
        setupToolbar();
        setupComponents();
    }

    private void setupComponents() {
        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(new DeliveredNowAdapter(this));
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
