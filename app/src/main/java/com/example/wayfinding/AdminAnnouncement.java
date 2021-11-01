package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminAnnouncement extends AppCompatActivity {
    private ListView announcementList;
    private Button addAnnouncementButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcements2);

        announcementList = findViewById(R.id.announcement);
        addAnnouncementButton = findViewById(R.id.add_announcement_button);


        ArrayList<String> items = new ArrayList<>();



        ArrayAdapter<String> adp = new ArrayAdapter<String>(AdminAnnouncement.this,android.R.layout.simple_list_item_1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                item.setTypeface(Typeface.createFromAsset(getAssets(), "amethysta.ttf"));
                return item;
            }
        };
        announcementList.setAdapter(adp);

    }

    public void goToHome(View v){
        Intent intent = new Intent(AdminAnnouncement.this, HomePageActivity.class);
        startActivity(intent);
    }

    public void addAnnouncementClick(View v){
        //Create pop up

        //Get information from the fields

        //Call the connection helper to upload that information to the DB
    }
}