package tawseel.com.tajertawseel.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tawseel.com.tajertawseel.R;

/**
 * Created by AbdulMoeed on 7/16/2016.
 */
public class ProductList extends BaseActivity {
    RadioGroup rg;
    Button btn_ok;
    private RequestQueue requestQueue;
    ProgressDialog progress;
    private StringRequest request;
    private static String URL = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_label_layout);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        rg=(RadioGroup)this.findViewById(R.id.rg1);
        btn_ok=(Button)this.findViewById(R.id.btn_ok) ;
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectID=rg.getCheckedRadioButtonId();
                RadioButton rb = new RadioButton(ProductList.this);
                rb=(RadioButton)findViewById(selectID);
                 final String p_id=rb.getId()+"",p_name=rb.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductList.this);
                builder.setTitle("Quantity");

// Set up the input
                final EditText input = new EditText(ProductList.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("Add to Cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String p_quan = input.getText().toString();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        progress = ProgressDialog.show(this, "Loading",
                "Please Wait..", true);
       URL =functions.add + "showProduct.php?id="+LoginActivity.LoginID;
        requestQueue = Volley.newRequestQueue(this);
        //json to get information
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            JSONArray jsonArr=response.getJSONArray("info");
                           final RadioButton rb[]= new RadioButton[jsonArr.length()];
                            final TextView tv[]= new TextView[jsonArr.length()];
                            TextView et[]= new TextView[jsonArr.length()];
                            ImageView iv[] = new ImageView[jsonArr.length()];

                            for (int  i= 0; i < jsonArr.length();i++) {
//display info in txt box

                                final JSONObject jsonObj = jsonArr.getJSONObject(i);
                                rb[i] = new RadioButton(ProductList.this);

                                et[i] = new TextView(ProductList.this);
                                tv[i] = new TextView(ProductList.this);
                                rb[i].setText(jsonObj.getString("Title"));
                                rb[i].setId(Integer.parseInt(jsonObj.getString("ProductID")));
                                et[i].setText(jsonObj.getString("Description"));
                                tv[i].setText("Discounted Price : "+jsonObj.getString("DiscountPrice"));

                               rg.addView(rb[i],lp);
                                rg.addView(et[i],lp);
                                rg.addView(tv[i],lp);
                            }
                            progress.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        progress.dismiss();
                    }
                }


        );
        //request server  to again send data
        requestQueue.add(jsonObjectRequest);


    }

}