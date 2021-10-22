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

        items.add("The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Ann 2 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Ann 4 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Please note that you will need to be vaccinated to be on campus. You will need to present you student card and scan it at the entrance to enter the Morisset library");
        items.add("During reading week, the operation hours of the library will be changed.\nThe hours will be from 7am-5pm on Mon-Fri, and 10am-5pm on the weekends\nThank you for using the Morriset library");
        return items;
    }
}
