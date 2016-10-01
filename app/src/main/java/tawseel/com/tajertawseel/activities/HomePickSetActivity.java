package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.PickSetPagerAdapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class HomePickSetActivity extends BaseActivity {

    TabLayout tabs ;
ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_pickset);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }

    public void setUpComponents ()
    {
        tabs = (TabLayout)findViewById(R.id.tab);
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PickSetPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);

        View view = LayoutInflater.from(this).inflate(R.layout.tab_text_layout,null,false);
        TextView Title = (TextView)view.findViewById(R.id.tab_text);
        Title.setText("الطلبات المجمة");
        tabs.getTabAt(0).setCustomView(view);


        View view2 = LayoutInflater.from(this).inflate(R.layout.tab_text_layout,null,false);
        TextView Title2 = (TextView)view2.findViewById(R.id.tab_text);
       Title2.setText("طلبات");
        tabs.getTabAt(1).setCustomView(view2);

    }

    @Override
    public void onBackPressed() {
        finish();
       // startActivity(new Intent(HomePickSetActivity.this,HomeActivity.class));
    }
}
