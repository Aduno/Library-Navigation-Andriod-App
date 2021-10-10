package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    private ListView announcementList;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        announcementList = findViewById(R.id.announcement);

        //Need to populate the list with announcement information
        items = new ArrayList<>();
        getAnnouncements(items);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
        announcementList.setAdapter(adapter);
    }

    /**
     * Contacts DB and retreives annoucement information
     * @param items
     */
    public void getAnnouncements(ArrayList<String> items){
        //need to connect to db  and retrieve info
        items.add("The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
    }
}