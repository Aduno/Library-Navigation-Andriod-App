package com.example.wayfinding.classes;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Utility class used to get information from the database
 */
public class ConnectionHelper {

    /**
     * Contacts DB and retreives annoucement information
     * Based on tutorial from https://www.youtube.com/watch?v=GKyEJmCoK5s&t=273s by Sandip Bhattacharya
     */
    public static ArrayList<String> getDatabaseInfo(Context context, String URL, int requestCode){
        ArrayList<String> items = new ArrayList<>();
        switch(requestCode){
            case 0:
                URL = URL+"/get_announcement.php";
                break;
            case 1:
                URL = URL+"/get_POI.php";
                break;
            default:
                throw new IllegalArgumentException("Invalid request code");
        }
        StringRequest req = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                items.add(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError e){
                Toast.makeText(context,"Failed to connect to database", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);

        return items;
    }


    public static void addAnnouncement(Context context, String URL, int requestCode){
        ArrayList<String> items = new ArrayList<>();

        //Change this to the right php file
        URL = URL+"/get_announcement.php";

        StringRequest req = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                items.add(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError e){
                Toast.makeText(context,"Failed to connect to database", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);

    }
}
