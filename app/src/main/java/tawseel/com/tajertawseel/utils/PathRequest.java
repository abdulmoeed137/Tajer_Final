package tawseel.com.tajertawseel.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Junaid-Invision on 7/20/2016.
 */
public class PathRequest {




        private String url;
        private RequestQueue queue;
        private JsonObjectRequest request;
        private Context context;


        private  List<List<HashMap<String, String>>> routes = null;

        public  void makeUrl (double latitude , double longitude, double latitude2, double longitude2 , Context c, GoogleMap map)



        {

            context = c;






            String temp = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "origin="+latitude+","+longitude+ "&" +"destination="+latitude2+","+ longitude2+"&"+
                    "waypoints=optimize:true|"+latitude+","+longitude+"|"+latitude2+","+longitude2+"&"+
                    "sensor=false";

            url = temp;





            makeConnection(map);






        }


    public void makeConnection (final GoogleMap map)
    {
        queue = Volley.newRequestQueue(context);


        request = new JsonObjectRequest(Request.Method.POST,url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                try {
                    Log.d("Map Params Returned",""+jsonObject.get("routes"));
                    PathJSONParser parser = new PathJSONParser();
                    routes = parser.parse(jsonObject);


                    PolylineOptions pathLine  =     makeMarkerOptions(routes);
//                    map.addPolyline(pathLine);
//                    paths.addAll(pathLine.getPoints());

                    try{
                    map.addPolyline(pathLine);}
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                   // Log.d("Paths .size",""+paths.size());vvv


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("Map Error Returned",""+volleyError);

            }
        });


        queue.add(request);


    }


    private PolylineOptions makeMarkerOptions(List<List<HashMap<String, String>>> routes) {

        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List<HashMap<String, String>> path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(3);
            polyLineOptions.color(Color.RED);




            Log.d("Marker Options", "" +polyLineOptions);

        }

        return polyLineOptions;

    }

}


