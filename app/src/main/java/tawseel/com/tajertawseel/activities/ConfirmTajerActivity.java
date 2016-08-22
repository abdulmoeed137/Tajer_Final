package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ConfirmTajerListAdapter;

/**
 * Created by Junaid-Invision on 8/22/2016.
 */
public class ConfirmTajerActivity extends BaseActivity {

    ListView ListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tajer);
        setUpComponents();
        setUpToolbar();
    }


    public void setUpComponents()
    {
        ListView = (ListView)findViewById(R.id.listView);
        ListView.setAdapter(new ConfirmTajerListAdapter(this));
    }
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        // title.setText(getString(R.string.connecting_profile_delegates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //title.setText("تأكيد");
        // deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
    }
}
