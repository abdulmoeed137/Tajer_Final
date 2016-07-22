package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DileveryGroupAdapter;

/**
 * Created by Junaid-Invision on 7/12/2016.
 */
public class DeliveryGroupActivity extends BaseActivity {


    private ListView groupListView;
    private ImageView postGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_groups);
        setUpToolbar();
        setupComponents();

    }

    public void setupComponents ()
    {
        groupListView = (ListView)findViewById(R.id.group_list_view);
        groupListView.setAdapter(new DileveryGroupAdapter(this));
        postGroup = (ImageView)findViewById(R.id.post_group_button);

        postGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryGroupActivity.this,PostGroupActivity.class);
                startActivity(i);
            }
        });


        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView tickView = (ImageView)view.findViewById(R.id.tick_view);

                LinearLayout layout = (LinearLayout) view.findViewById(R.id.container);
                if(tickView.getVisibility() == View.INVISIBLE)
                {

                    tickView.setVisibility(View.VISIBLE);
                  //  layout.setBackgroundColor(getResources().getColor(R.color.));
                }
                else
                {
                    tickView.setVisibility(View.INVISIBLE);
                   // layout.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
