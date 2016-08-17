package tawseel.com.tajertawseel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import tawseel.com.tajertawseel.R;

/**
 * Created by Junaid-Invision on 7/12/2016.
 *
 *
 * mm
 */
public class LoginActivity extends BaseActivity {

    ProgressDialog progress;
    static String LoginID;
    EditText email_ET, pass_ET;
    private RequestQueue requestQueue;
    private static final String URL = functions.add + "login.php";
    private StringRequest request;
    public static String uname;
    public static String email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.splash_logo3).into(logo);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        email_ET = (EditText) findViewById(R.id.email);
        pass_ET = (EditText) findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(this);

        assert loginButton != null;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass_ET.getText().toString().equals("d"))
                {

                    Intent i = new Intent(LoginActivity.this, DeligateHomeActivity.class);
                    i.putExtra("DeligateID",email_ET.getText().toString());
                    i.putExtra("flag",true);
                    Toast.makeText(getApplicationContext(), "Welcome "+email_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent (LoginActivity.this,FinancialRequestActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                LoginID=1+"";
                uname="tajer";
                //if success
              //  progress.dismiss();
                Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);

                //Intent i = new Intent (LoginActivity.this,FinancialRequestActivity.class);
                startActivity(i);
                finish();}

           /*     email = email_ET.getText().toString();
                final String pass = pass_ET.getText().toString();
                if (functions.isEmailTrue(email, getApplicationContext()) || functions.isPasswordTrue(pass,getApplicationContext())) {
                    progress = ProgressDialog.show(LoginActivity.this, "Loading",
                            "Please Wait..", true);
                   request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        //if response
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                try {
                                    if (jsonObject.names().get(0).equals("success")) {
                                     LoginID=jsonObject.getString("success");
                                        uname=jsonObject.getString("uname");
                                        //if success
                                        progress.dismiss();
                                        Toast.makeText(getApplicationContext(), "Welcome "+uname, Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        progress.dismiss();
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    progress.dismiss();
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                progress.dismiss();
                                e.printStackTrace();
                            }


                        }// in case error
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        //send data to server using POST
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();

                            hashMap.put("email", email);
                            hashMap.put("pass", pass);
                            hashMap.put("hash", HASH.getHash());
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);}
                    */


            }
        });
    }





}