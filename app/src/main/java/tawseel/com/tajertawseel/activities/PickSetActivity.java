package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.PickSetAdapter;

/**
 * Created by Junaid-Invision on 7/3/2016.
 */
public class PickSetActivity extends BaseActivity {


    ListView mListView;
    CustomBoldTextView demandButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick_set);
        setUpToolbar();
        setUpComponents();
    }


    public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView)findViewById(R.id.title_text);
        toolbarTitle.setText(getString(R.string.pick_set));
        setSupportActionBar(toolbar);
    }


    public void setUpComponents (){
        mListView = (ListView)findViewById(R.id.pickSetListView);
        mListView.setAdapter(new PickSetAdapter(this));


        demandButton = (CustomBoldTextView)findViewById(R.id.add_demand_basket);
        demandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(PickSetActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.dialogue_layout);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                lp.dimAmount = 0.3f;
                dialog.show();

            }
        });
    }
}
