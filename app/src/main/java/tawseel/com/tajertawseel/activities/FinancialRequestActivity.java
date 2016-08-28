package tawseel.com.tajertawseel.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.CustomBoldTextView;
import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.FinancialRequestAdapter;

/**
 * Created by Junaid-Invision on 8/13/2016.
 */
public class FinancialRequestActivity extends BaseActivity {

    ListView mListView;
    String Groupid,tprice,tdelivery;
    TextView gid,ncustomers,date,itemprice,deliveryprice,dname;
    RatingBar drating;
    private RequestQueue requestQueue;
    ArrayList<FinancialHistoryData> data=new ArrayList<FinancialHistoryData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_request_history);
        tprice=getIntent().getExtras().getString("totalitem");
        tdelivery=getIntent().getExtras().getString("totald");
        Groupid=getIntent().getExtras().getString("gid");

        setUpComponents();
    }

    private void setUpComponents() {


        mListView = (ListView)findViewById(R.id.financial_history_list_view);
        gid=(TextView)findViewById(R.id.fh_GroupID);
        ncustomers=(TextView)findViewById(R.id.fh_NoOfCustomers);
        date=(TextView)findViewById(R.id.fh_time);
        itemprice=(TextView)findViewById(R.id.fh_ItemPrice);
        deliveryprice=(TextView)findViewById(R.id.fh_PriceRange);
        dname=(TextView)findViewById(R.id.fh_DeligateName);
        drating=(RatingBar)findViewById(R.id.fh_ratingbar);

        StringRequest request = new StringRequest(Request.Method.POST,functions.add+"financialhistory.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    date.setText(jsonArr.getJSONObject(0).getString("DeliveryDate"));
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FinancialHistoryData temp=new FinancialHistoryData();
                        temp.setDname(jsonObj.getString("UserName"));
                        temp.setDeliveryprice(jsonObj.getString("PriceRange"));
                        temp.setItemprice(jsonObj.getString("ItemsPrice"));
                        temp.setNitems(jsonObj.getString("Items"));
                        data.add(temp);
                    }
                    gid.setText(Groupid);
                    ncustomers.setText(data.size()+"");
                    itemprice.setText(tprice);
                    deliveryprice.setText(tdelivery);
                    mListView.setAdapter(new FinancialRequestAdapter(FinancialRequestActivity.this,data));

                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("gid",Groupid);
                hashMap.put("id",HomeActivity.id);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(FinancialRequestActivity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(FinancialRequestActivity.this,"Internet Connection Error",Toast.LENGTH_SHORT).show();
        }
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
