package tawseel.com.tajertawseel.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.NewOrderProductAdapter;

/**
 * Created by Monis on 7/27/2016.
 */

public class New_Orders_Activity extends BaseActivity {

    ScrollView productList;
    Button add,finishb;
    RadioGroup radioGroup1;
    private RequestQueue requestQueue;
    private static String URL = functions.add+"products.php";
    public static RadioButton rbs[];
    int index=-1;
    public static ArrayList<PostGroupListData> pList=new ArrayList<PostGroupListData>();
    private ArrayList<PostGroupListData> allproducts=new ArrayList<PostGroupListData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders_lab);
Toast.makeText(this,String.valueOf(pList.size()),Toast.LENGTH_SHORT).show();
        productList=(ScrollView)findViewById(R.id.nscrollView);
        add=(Button)findViewById(R.id.add_item_button);
        finishb=(Button)findViewById(R.id.finish_button);
        radioGroup1=(RadioGroup)findViewById(R.id.nchoresRadioGroup);
        addRadioGroupListener();
        final RelativeLayout rl=(RelativeLayout)findViewById(R.id.nrl);
        final RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject mainObj=new JSONObject(response);
                    JSONArray jsonArr=mainObj.getJSONArray("info");
                    rbs=new RadioButton[jsonArr.length()];
                    for (int i = 0; i < jsonArr.length(); i++) {
                        final JSONObject jsonObj = jsonArr.getJSONObject(i);
                        PostGroupListData data=new PostGroupListData();
                        data.setProductID(jsonObj.getString("ProductID"));
                        data.setProductName(jsonObj.getString("Title"));
                        data.setDescription(jsonObj.getString("Description"));
                        data.setPrice(jsonObj.getString("Price"));
                        allproducts.add(data);
                        rbs[i]=new RadioButton(New_Orders_Activity.this);
                        rbs[i].setText(jsonObj.getString("Title")+"\n"+jsonObj.get("Description"));
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
            requestQueue= Volley.newRequestQueue(New_Orders_Activity.this);
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(New_Orders_Activity.this,"Request Issue",Toast.LENGTH_SHORT).show();
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


    public void FinishClick(View view) {

        AddNewOrderActivity.productsList.setAdapter(new NewOrderProductAdapter(AddNewOrderActivity.context,pList));
        finish();
    }

    public void addClick(View view) {
        if(index==-1)
        {
            Toast.makeText(New_Orders_Activity.this,"Select item first.",Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(New_Orders_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.add_product_to_item_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0.3f;
        TextView title=(TextView)dialog.findViewById(R.id.product_title);
        title.setText(allproducts.get(index).getProductName());
        final EditText quantity_et=(EditText)dialog.findViewById(R.id.pquantity_et);
        Button additem=(Button)dialog.findViewById(R.id.add_newitem_button);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity_et.getText().toString().compareTo("")==0)
                {

                    Toast.makeText(New_Orders_Activity.this,"Set quantity first",Toast.LENGTH_SHORT).show();
                    return;
                }
                allproducts.get(index).setQuantity(quantity_et.getText().toString());
                pList.add(allproducts.get(index));
                Toast.makeText(New_Orders_Activity.this,"Product added to Order",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}