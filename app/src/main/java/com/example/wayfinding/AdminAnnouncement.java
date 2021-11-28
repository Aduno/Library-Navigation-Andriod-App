package com.example.wayfinding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wayfinding.classes.ConnectionHelper;
import com.example.wayfinding.classes.UserSettings;
import com.example.wayfinding.classes.VolleyCallBack;

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

        announcementList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = announcementList.getSelectedItemPosition();

            }
        });
        if (settings.getDarkMode()) {
            frame.setBackgroundColor((Color.rgb(100, 100, 100)));
        } else {
            frame.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        items = new ArrayList<>();
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
}
