package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    private ListView announcementList;
    private Spinner searchBar;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        announcementList = findViewById(R.id.announcement);
        searchBar = findViewById(R.id.search_bar);

        //Sets up the search bar
        //Need to communicate with the DB to get the POI
        ArrayList<String> locationList = new ArrayList<>();
        locationList.add("Printers");
        locationList.add("Elevator");

        searchBar.setAdapter(new ArrayAdapter<>(HomePageActivity.this,android.R.layout.simple_spinner_dropdown_item, locationList));

        searchBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String location = parent.getItemAtPosition(position).toString();

                //pass location info to algorithm interface


                String msg = "Finding the best route to "+location+"...";
                Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePageActivity.this,NavigationScreen.class);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Need to populate the list with announcement information
        items = new ArrayList<>();
        getAnnouncements(items);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TextView item = (TextView) super.getView(position,convertView,parent);
                item.setTypeface(Typeface.createFromAsset(getAssets(),"amethysta.ttf"));
                return item;
            }
        };
        announcementList.setAdapter(adapter);
    }

    /**
     * Contacts DB and retreives annoucement information
     * @param items
     */
    public void getAnnouncements(ArrayList<String> items){
        //need to connect to db  and retrieve info
        items.add("The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Acc 2 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Acc 4 The library will be closed from 6-8PM on October 16 for temporary repairs on the elevator");
        items.add("Please note that you will need to be vaccinated to be on campus. You will need to present you student card and scan it at the entrance to enter the Morisset library");
        items.add("During reading week, the operation hours of the library will be changed.\nThe hours will be from 7am-5pm on Mon-Fri, and 10am-5pm on the weekends\nThank you for using the Morriset library");
    }
    /**
     * Search bar functionality. It queries the DB for information regarding the points of interest and using the current position and the end position,
     * calls on the pathfinding algorithm to find the optimal distance
     */
}