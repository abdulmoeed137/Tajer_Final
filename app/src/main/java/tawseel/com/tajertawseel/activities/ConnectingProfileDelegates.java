package tawseel.com.tajertawseel.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/30/2016.
 */
public class ConnectingProfileDelegates extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting_profile_delegates);
        Bundle extras = getIntent().getExtras();
        FavouriteDelegateItemData temp=new FavouriteDelegateItemData();

        if (extras != null) {
            temp.setName(extras.getString("name"));
            temp.setCar(extras.getString("car"));
            temp.setCarnum(extras.getString("carnum"));
            temp.setModel(extras.getString("model"));
            temp.setNdelivers(extras.getString("delivers"));
            temp.setContact(extras.getString("contact"));
            temp.setStars(extras.getString("stars"));
        }

        TextView main_name = (TextView) findViewById(R.id.mname);
        TextView name = (TextView) findViewById(R.id.profile_name);
        TextView car = (TextView) findViewById(R.id.profile_car);
        TextView num = (TextView) findViewById(R.id.profile_carnum);
        TextView model = (TextView) findViewById(R.id.profile_model);
        TextView contact = (TextView) findViewById(R.id.profile_contact);
        TextView delivers = (TextView) findViewById(R.id.profile_delivers);
        TextView reviews = (TextView) findViewById(R.id.profile_reviews);
        RatingBar rating=(RatingBar)findViewById(R.id.profile_ratingbar);

        main_name.setText(temp.getName());
        name.setText(temp.getName());
        car.setText(temp.getCar());
        num.setText(temp.getCarnum());
        model.setText(temp.getModel());
        contact.setText(temp.getContact());
        delivers.setText(temp.getNdelivers());
        reviews.setText(temp.getNdelivers());
        rating.setRating(Float.parseFloat(temp.getStars()));

        setUpToolbar();
    }


    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (CustomBoldTextView) toolbar.findViewById(R.id.title_text);
        // title.setText(getString(R.string.connecting_profile_delegates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // deleteIcon = (ImageView)toolbar.findViewById(R.id.delete_icon);
    }

}
