package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/2/2016.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupContents();
    }

    private void setupContents ()
    {

        mDrawerLayout = (DrawerLayout)findViewById(R.id.homeDrawer);
        mDrawerLayout.openDrawer(Gravity.RIGHT);

        setupListeners();

    }
    public void setupListeners ()
    {
        mDrawerLayout.findViewById(R.id.option1).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option2).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option3).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option4).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option5).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option6).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.option7).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.option1)
        {
            Toast.makeText(HomeActivity.this,"Option1",Toast.LENGTH_SHORT).show();
            Intent i = new Intent (HomeActivity.this,CustomerRequestActivity.class);
            startActivity(i);

        }
        else if(v.getId() == R.id.option2)
        {
            Toast.makeText(HomeActivity.this,"Option2",Toast.LENGTH_SHORT).show();
        }

        else if(v.getId() == R.id.option3)
        {
            Toast.makeText(HomeActivity.this,"Option3",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.option4)
        {
            Toast.makeText(HomeActivity.this,"Option4",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.option5)
        {
            Toast.makeText(HomeActivity.this,"Option5",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.option6)
        {
            Toast.makeText(HomeActivity.this,"Option6",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.option7)
        {
            Toast.makeText(HomeActivity.this,"Option7",Toast.LENGTH_SHORT).show();
        }
    }
}
