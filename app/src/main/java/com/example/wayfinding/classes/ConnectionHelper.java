package com.example.wayfinding.classes;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wayfinding.AdminAnnouncement;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Utility class used to get information from the database
 */
public class ConnectionHelper{

    /**
     * Contacts DB and retreives annoucement information
     * Based on tutorial from https://www.youtube.com/watch?v=GKyEJmCoK5s&t=273s by Sandip Bhattacharya
     * Callback functionality from https://stackoverflow.com/questions/49342841/android-wait-for-volley-response-for-continue
     */
    public static void getDatabaseInfo(Context context, String URL, int requestCode, ArrayList<String> items, VolleyCallBack callBack){
        switch(requestCode){
            case 0:
                URL = URL+"/get_announcement.php";
                break;
            case 1:
                URL = URL+"/get_poi.php";
                break;
            default:
                throw new IllegalArgumentException("Invalid request code");
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    String[] split = response.split(",");
                    for (String e : split) {
                        items.add(e);
                    }
                    callBack.onSuccess();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError e){
                Toast.makeText(context,"Failed to connect to database", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(req);
    }
}
