package tawseel.com.tajertawseel.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.ConfirmTajerActivity;
import tawseel.com.tajertawseel.activities.LocationManage;
import tawseel.com.tajertawseel.activities.LoginActivity;
import tawseel.com.tajertawseel.activities.UpdateLocation;
import tawseel.com.tajertawseel.activities.functions;

/**
 * Created by Junaid-Invision on 8/16/2016.
 */
public class delegateHomeFragment1 extends Fragment implements OnMapReadyCallback,LocationListener {

    View mRootView;
    private GoogleMap mMap;
    LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES =1; // 1 minute
    LatLng origin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_delegates_home1, null, false);
        setupComponents();
        setupMap();
        return mRootView;

    }

    public void setupComponents() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton b = (FloatingActionButton) mRootView.findViewById(R.id.myLocation);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    origin = new LatLng(LocationManage.Lat,LocationManage.Long);
                } catch (Exception e) {

                    Toast.makeText(getActivity(), "No Old Location Saved", Toast.LENGTH_SHORT).show();
                }
                mMap.clear();
                LatLng positionUpdate = origin;
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate, 12);
                mMap.animateCamera(update);
                addMarker(origin.latitude,origin.longitude, "Deligate", R.drawable.destination_marker);

            }
        });
        FloatingActionButton minus= (FloatingActionButton) mRootView.findViewById(R.id.minus_button);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        FloatingActionButton plus = (FloatingActionButton)mRootView.findViewById(R.id.plus_button);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getActivity(), "Location Permission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
        try {
            origin = new LatLng(LocationManage.Lat,LocationManage.Long);
        } catch (Exception e) {

            Toast.makeText(getActivity(), "No Old Location Saved", Toast.LENGTH_SHORT).show();
        }




        mRootView.findViewById(R.id.ButtonConfirmationTajer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
            }
        });

    }


//
//    else if(v.getId() == R.id.new_button)
//    {
//        showDialogue();
//    }


    public void showDialogue()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.available_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getActivity().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.findViewById(R.id.Online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
RunVolley("1");
                dialog.dismiss();
                Intent i = new Intent(getActivity(),UpdateLocation.class);
                getActivity().startService(i);
            }
        });
        dialog.findViewById(R.id.Offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
RunVolley("0");
                dialog.dismiss();
                Intent i = new Intent(getActivity(),UpdateLocation.class);
                getActivity().stopService(i);
            }
        });
        dialog.show();

//        dialog.findViewById(R.id.BtnNewGroup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // startActivity(new Intent(getActivity(), PostNewGroupActivity.class));
//            }
//        });
    }
    void RunVolley(final String value){


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request;
        final  ProgressDialog progress = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading");
        progress.show();
        request = new StringRequest(Request.Method.POST, functions.add+"DeligateOnOffLine.php", new Response.Listener<String>() {
            //if response
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    try {
                        if (jsonObject.names().get(0).equals("success")) {

                            //if success
                            Snackbar.make(getActivity().findViewById(android.R.id.content), jsonObject.getString("success"), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                            progress.dismiss();


                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), jsonObject.getString("failed"), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)

                                    .show();
                            progress.dismiss();
                        }
                    } catch (JSONException e) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setActionTextColor(Color.RED)

                                .show();
                        progress.dismiss();

                    }

                } catch (JSONException e) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(Color.RED)

                            .show();
                    progress.dismiss();

                }
            }// in case error
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Srvc",error.toString());
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Error", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)

                        .show();
                progress.dismiss();
            }
        }) {
            //send data to server using POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", LoginActivity.DeligateID);
                hashMap.put("hash", "CCB612R");
                hashMap.put("Status",value);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng positionUpdate = origin;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionUpdate, 12);
        mMap.animateCamera(update);
        addMarker(origin.latitude,origin.longitude, "Deligate", R.drawable.destination_marker);

    }
    private void addMarker(double lat, double lng, String title, int markericon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(lat, lng))
                .title(title)
                .anchor(.5f, 1f).icon(BitmapDescriptorFactory.fromResource(markericon));


        mMap.addMarker(markerOptions);


//        LatLng latLng = new LatLng(lat,lng);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, Application.DEFAULT_ZOOM);
//        mMap.animateCamera(update);

    }

    @Override
    public void onLocationChanged(Location location) {

        LocationManage.Lat = location.getLatitude();
        LocationManage.Long=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
