package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminAnnouncement extends AppCompatActivity {
    private ListView announcementList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcements2);

        announcementList = findViewById(R.id.announcement);

        ArrayList<String> items = new ArrayList<>();
        items.add("The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Ann 2 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Ann 4 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Please note that you will need to be vaccinated to be on campus. You will need to present you student card and scan it at the entrance to enter the Morisset library");
        items.add("During reading week, the operation hours of the library will be changed.\nThe hours will be from 7am-5pm on Mon-Fri, and 10am-5pm on the weekends\nThank you for using the Morriset library");
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
}