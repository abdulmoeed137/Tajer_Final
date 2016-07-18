package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.CustomerRequestAdapter;

/**
 * Created by Junaid-Invision on 7/3/2016.
 */
public class CustomerRequestActivity extends BaseActivity {

    ListView mListView;
    ImageView postRequestButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);

       setUpToolbar();
        setUpComponents();
    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option1));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    public void setUpComponents()
    {
        mListView = (ListView)findViewById(R.id.customer_request_listView);
        mListView.setAdapter(new CustomerRequestAdapter(this));
        postRequestButton = (ImageView)findViewById(R.id.post_your_request_button);
        setUpListeners();
    }

    public void setUpListeners ()
    {


        postRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i  = new Intent (CustomerRequestActivity.this, AddNewOrderActivity.class);

                startActivity(i);

            }
        });
    }
}
