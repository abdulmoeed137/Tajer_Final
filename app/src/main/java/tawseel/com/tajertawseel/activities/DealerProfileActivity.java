package tawseel.com.tajertawseel.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 8/25/2016.
 */
public class DealerProfileActivity extends BaseActivity {


    String id="";
    TextView date,time,mname,name,bio,contact,delivers;
    RatingBar bar;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dealer);
        Bundle extras = getIntent().getExtras();
        mname=(TextView)findViewById(R.id.sp_mname);
        name=(TextView)findViewById(R.id.sp_name);
        bio=(TextView)findViewById(R.id.sp_bio);
        contact=(TextView)findViewById(R.id.sp_contact);
        delivers=(TextView)findViewById(R.id.sp_delivers);
        date=(TextView)findViewById(R.id.spdate);
        time=(TextView) findViewById(R.id.sptime);
        bar=(RatingBar)findViewById(R.id.sp_ratingbar);
        if (extras != null) {
            String idt[]=extras.getString("TajerID").split(" ");
            if(idt[0]!=null)
                id=idt[0];
            else
                id=extras.getString("TajerID");
            if(idt[1]!=null)
                date.setText(idt[1]);
            if(idt[2]!=null)
                time.setText(idt[2]);
        }

        setUpToolbar();
        ImageView img = (ImageView)findViewById(R.id.BtnCall);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+contact.getText().toString()));
                if (ActivityCompat.checkSelfPermission(DealerProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DealerProfileActivity.this,"Call Permission Required",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });
        final ProgressDialog progress = ProgressDialog.show(DealerProfileActivity.this, "Loading",
                "Please Wait..");
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(functions.bg)));
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"sellers.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        mname.setText(jsonObj.getString("UserName"));
                        name.setText(jsonObj.getString("UserName"));
                        contact.setText(jsonObj.getString("Contact"));
                        delivers.setText(jsonObj.getString("delivers"));
                        bio.setText(jsonObj.getString("Bio"));
                        Float idelivers= Float.parseFloat(jsonObj.getString("delivers"));
                        bar.setRating(idelivers);
                    }
                    progress.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.hide();
                    if ((e.getClass().equals(TimeoutError.class)) || e.getClass().equals(NoConnectionError.class)){
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                            .setAction("Reload", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                    startActivity(getIntent());
                                }
                            })
                            .setActionTextColor(Color.RED)

                            .show();}
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progress.hide();
                if ((error.getClass().equals(TimeoutError.class)) || error.getClass().equals(NoConnectionError.class)){
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setActionTextColor(Color.RED)

                        .show();}
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",DeligateHomeActivity.DeligateID);
                hashMap.put("hash",HASH.getHash());
                hashMap.put("tid",id);
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(DealerProfileActivity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                    .setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setActionTextColor(Color.RED)

                    .show();
        }

    }

    public void setUpToolbar() {
      Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView) findViewById(R.id.title_text);
        toolbarTitle.setText(getString(R.string.dealer_profile));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
