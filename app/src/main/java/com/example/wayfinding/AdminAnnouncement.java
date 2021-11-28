package com.example.wayfinding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wayfinding.classes.ConnectionHelper;
import com.example.wayfinding.classes.UserSettings;
import com.example.wayfinding.classes.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminAnnouncement extends AppCompatActivity {
    private ListView announcementList;
    private Button addAnnouncementButton;
    ArrayList<String> items;
    ArrayAdapter<String> adp;
    UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcements);

        announcementList = findViewById(R.id.announcement);
        addAnnouncementButton = findViewById(R.id.add_announcement_button);
        Intent intent = getIntent();
        LinearLayout frame = findViewById(R.id.adminFrame);
        settings = (UserSettings) intent.getSerializableExtra("key");

        if (settings.getDarkMode()) {
            frame.setBackgroundColor((Color.rgb(100, 100, 100)));
        } else {
            frame.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        items = new ArrayList<>();
        if(settings.getFrench()){

        }else {
            items.add("Please keep your mask on inside the building and complete the daily health checkin when " +
                    "coming on campus.");
            items.add("The library will be closed early for December 24. Operating hours will be 10:00AM to 4:00PM");
            items.add("The elevators will go under maintenance on Dec 10");
        }
        ConnectionHelper.getDatabaseInfo(this, HomePageActivity.URL, 0, items, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                adp = new ArrayAdapter<String>(AdminAnnouncement.this, android.R.layout.simple_list_item_1, items) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView item = (TextView) super.getView(position, convertView, parent);
                        item.setTypeface(Typeface.createFromAsset(getAssets(), "amethysta.ttf"));
                        return item;
                    }
                };
                announcementList.setAdapter(adp);
            }
        },settings);

        announcementList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                items.remove(items.get(i));
                announcementList.setAdapter(new ArrayAdapter<String>(AdminAnnouncement.this, android.R.layout.simple_list_item_1, items));
                deleteItem(items.get(i));
                return false;
            }
        });
    }

    public void goToHome(View v) {
        Intent intent = new Intent(AdminAnnouncement.this, MainActivity.class);
        startActivity(intent);
    }

    public void addAnnouncementClick(View v) {
        //Create pop up
        startActivity(new Intent(AdminAnnouncement.this, Pop.class));
    }
    public void deleteItem(String announcment){
        StringRequest req = new StringRequest(Request.Method.POST, HomePageActivity.URL+"/delete_announcement.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
