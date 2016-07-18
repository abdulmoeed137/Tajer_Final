package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.ListPopupAdapter;
import tawseel.com.tajertawseel.adapters.NewOrderProductAdapter;

/**
 * Created by Junaid-Invision on 7/10/2016.
 */
public class AddNewOrderActivity extends BaseActivity implements View.OnClickListener {


    ListView productsList;
    LinearLayout postnewLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_order);
        setUpToolbar();
        setUpComponents();
    }
public void allProduct()
{

    Toast.makeText(AddNewOrderActivity.this,"OK",Toast.LENGTH_SHORT).show();
}


   public void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CustomBoldTextView title = (CustomBoldTextView)toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.add_new_order));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public void setUpComponents ()
    {
        productsList = (ListView)findViewById(R.id.product_list);
        productsList.setAdapter(new NewOrderProductAdapter(this));
        postnewLayout = (LinearLayout)findViewById(R.id.post_your_new_product_container);

        productsList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Spinner layout = (Spinner) findViewById(R.id.popup_view);

        layout.setAdapter(new ListPopupAdapter(this));




        postnewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddNewOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.add_new_order_dialogue);
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

    @Override
    public void onClick(View v) {
       if (v.getId()== R.id.btnAllProd)
        {

          startActivity(new Intent(getApplicationContext(),ProductList.class));

        }
    }
}
