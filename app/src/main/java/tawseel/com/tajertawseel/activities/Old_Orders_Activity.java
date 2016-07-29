package tawseel.com.tajertawseel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.NewOrderProductAdapter;

/**
 * Created by Monis on 7/27/2016.
 */

public class Old_Orders_Activity extends  BaseActivity {

    ScrollView orderList;
    Button donebutton;
    RadioGroup radioGroup1;
    private RequestQueue requestQueue;
    private static String URL = functions.add+"unconfirmed_orders.php";
    public static RadioButton rbs[];
    int index=-1;
    ArrayList<Customer_request_item_data> clist=new ArrayList<Customer_request_item_data>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_orders_lab);
        orderList=(ScrollView)findViewById(R.id.scrollView);
        donebutton=(Button)findViewById(R.id.Add_to_form_button);
        radioGroup1=(RadioGroup)findViewById(R.id.choresRadioGroup);
        addRadioGroupListener();
        final RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        final RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    rbs=new RadioButton[jsonArr.length()];
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        Customer_request_item_data data=new Customer_request_item_data();
                        data.setOrderID(jsonObj.getString("OrderID"));
                        data.setName(jsonObj.getString("UserName"));
                        data.setEmail(jsonObj.getString("Email"));
                        data.setNumber(jsonObj.getString("Mobile"));
                        data.setNo_of_items(jsonObj.getString("Items"));
                        clist.add(data);
                        rbs[i]=new RadioButton(Old_Orders_Activity.this);
                        rbs[i].setText(jsonObj.getString("UserName")+"\nNumber of items="+jsonObj.getString("Items"));
                        radioGroup1.addView(rbs[i],lp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",HomeActivity.id);
                hashMap.put("hash",HASH.getHash());
                return hashMap;
            }
        };
        try{
            requestQueue= Volley.newRequestQueue(Old_Orders_Activity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(Old_Orders_Activity.this,"Request Issue",Toast.LENGTH_SHORT).show();
        }
    }

    void addRadioGroupListener()
    {
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int j=0;j<rbs.length;j++){
                    if(rbs[j].isChecked())
                    {
                        index=j;
                        break;
                    }
                }
                            }
        });
    }


    public void addschClick(View view) {
        if(index==-1)
        {
            Toast.makeText(this,"Select a order first.",Toast.LENGTH_SHORT).show();
        return;
        }
        Intent i=new Intent(Old_Orders_Activity.this,AddNewOrderActivity.class);
        i.putExtra("oid",clist.get(index).getOrderID());
        i.putExtra("name",clist.get(index).getName());
        i.putExtra("email",clist.get(index).getName());
        i.putExtra("mobile",clist.get(index).getNumber());
        startActivity(i);

        try{
        AddNewOrderActivity.oldid=Integer.parseInt(clist.get(index).getOrderID());}
        catch (Exception e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }
}
