package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.FinancialRequestAdapter;

/**
 * Created by Junaid-Invision on 8/13/2016.
 */
public class FinancialRequestActivity extends BaseActivity {

    ListView mListView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_request_history);
        setUpComponents();
    }

    private void setUpComponents() {


        mListView = (ListView)findViewById(R.id.financial_history_list_view);
        mListView.setAdapter(new FinancialRequestAdapter(this));
        setUpToolbar();
    }



    public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.financial_history_of_Request));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}
