package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    private ListView announcementList;
    private SearchableSpinner searchBar;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        announcementList = findViewById(R.id.announcement);
        searchBar = findViewById(R.id.search_bar);

        //Checks permissions before installing the speech to text recognizer
        //Initializes recognizer
        //Based on tutorial from https://medium.com/voice-tech-podcast/android-speech-to-text-tutorial-8f6fa71606ac



        //Sets up the search bar
        //Need to communicate with the DB to get the POI
        ArrayList<String> locationList = new ArrayList<>();
        locationList.add("Printers");
        locationList.add("Elevator");

        searchBar.setAdapter(new ArrayAdapter<>(HomePageActivity.this,android.R.layout.simple_spinner_dropdown_item, locationList));
        searchBar.setTitle(getString(R.string.search_spinner_title));

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

    /**
     * Speech to text functionality for the search bar
     *
     * based on tutorial from https://www.youtube.com/watch?v=zHgATbPcq04
     */
    public void speechToText(View v){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak the location");

        try{

        }catch(Exception e){

        }
    }
}