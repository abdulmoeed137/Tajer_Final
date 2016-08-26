package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.TradersFavouriteAdapter;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class TradersFavouriteActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView listView;
    private CustomBoldTextView title;
    private ImageView deleteIcon;
    private boolean longClick  = false;
    private int ItemCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_favourite);
        setUpToolbar();
        setUpComponent();
    }

    private void setUpComponent() {

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new TradersFavouriteAdapter(this));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!longClick) {
                    Intent intent = new Intent(TradersFavouriteActivity.this, DealerProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    longClick = false;
                }
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.container);
                ImageView iView = (ImageView)view.findViewById(R.id.tick_view);
                longClick = true;


                if(iView.getVisibility()==View.INVISIBLE)
                {
                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
                    iView.setVisibility(View.VISIBLE);
                    ItemCount = ItemCount+1;
                    title.setText(ItemCount+"");
                    if(deleteIcon.getVisibility() == View.INVISIBLE)
                    {
                        deleteIcon.setVisibility(View.VISIBLE);
                    }
                }

                else
                {
                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    iView.setVisibility(View.INVISIBLE);
                    //ItemCount = ItemCount-1;

                    if(ItemCount >0)
                    {
                        ItemCount = ItemCount-1;
                        title.setText(""+ItemCount);
                    }


                    if(ItemCount==0)
                    {
                        title.setText(getString(R.string.drawer_option4));
                        deleteIcon.setVisibility(View.INVISIBLE);
                    }
                }


                return false;
            }
        });

    }


    public void setUpToolbar() {
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title = (CustomBoldTextView) findViewById(R.id.title_text);
        title.setText(getString(R.string.trader_favourite));
        deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
