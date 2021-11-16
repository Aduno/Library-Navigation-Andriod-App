package com.example.wayfinding;
import  android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Pop extends Activity{
    String URL = HomePageActivity.URL;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_admin);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.72));
    }

    public void onclick(View view) {
        EditText text_input1 = findViewById(R.id.textView3);

        TextView textView = findViewById(R.id.welcome_2);

        Toast.makeText(this, "announcement", Toast.LENGTH_SHORT).show();
        StringRequest req = new StringRequest(Request.Method.POST, URL+"/add_announcement.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1"))
                        textView.setText("Announcement published successfully.");
                    else
                        textView.setText("Announcement published unsuccessfully.");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("announcement", text_input1.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }
}
