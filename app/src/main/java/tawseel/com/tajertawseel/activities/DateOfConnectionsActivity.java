package tawseel.com.tajertawseel.activities;

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
import tawseel.com.tajertawseel.adapters.DateOfConnectionsAdapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class DateOfConnectionsActivity extends BaseActivity {
    ListView mLisView;
    private ImageView deleteIcon;
    private CustomBoldTextView title;
    private int selectedCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_connections);
        setUpToolbar();
        setupComponents();
    }



    public void setupComponents()
    {
        mLisView = (ListView)findViewById(R.id.connectionsListView);
        mLisView.setAdapter(new DateOfConnectionsAdapter(DateOfConnectionsActivity.this));

        mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LinearLayout layout = (LinearLayout)view.findViewById(R.id.container);

                if(layout!=null)
                {
                    ImageView TickView = (ImageView)view.findViewById(R.id.tick_view);

                    if(TickView.getVisibility() == View.INVISIBLE)
                    {
                        TickView.setVisibility(View.VISIBLE);
                        layout.setBackgroundColor(getResources().getColor(R.color.grey));
                        selectedCount = selectedCount+1;
                        title.setText(""+selectedCount);
                        if(deleteIcon.getVisibility() == View.INVISIBLE)
                        {
                            deleteIcon.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        TickView.setVisibility(View.INVISIBLE);
                        layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        selectedCount = selectedCount-1;

                        title.setText(""+selectedCount);
                        if(selectedCount == 0)
                        {
                            title.setText(getString(R.string.drawer_option3));
                            deleteIcon.setVisibility(View.INVISIBLE);
                        }

                    }

                }
            }
        });
    }


    private void setUpToolbar ()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.drawer_option3));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);

    }
}
