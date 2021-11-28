package com.example.wayfinding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wayfinding.classes.ConnectionHelper;
import com.example.wayfinding.classes.UserSettings;
import com.example.wayfinding.classes.VolleyCallBack;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Locale;


public class HomePageActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ANNOUNCEMENT =0;
    private static final int REQUEST_CODE_POI =1;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    private LinearLayout homeFrame;
    private ListView announcementList;
    private SearchableSpinner searchBar;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    private Intent speechIntent;
    private Button speechButton;
    private ArrayList<String> locationList;
    //This URL has to be changed depending on the PC unless an external server with a static ip is setup
    public static final String URL = "http://10.196.62.226:8080";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        announcementList = findViewById(R.id.announcement);
        searchBar = findViewById(R.id.search_bar);
        speechButton = findViewById(R.id.speech_button);
        homeFrame = findViewById(R.id.homeFrame);


        Intent intent = getIntent();

        UserSettings settings = (UserSettings) intent.getSerializableExtra("key");

        if(settings.getDarkMode()){
           //Set background of homeframe background to gray or whatever color
            homeFrame.setBackgroundColor(Color.rgb(100, 100, 100));

        }
        else{
            homeFrame.setBackgroundColor(Color.WHITE);
        }

        // ------------------------- Initializes speech recognizer -------------------------//
        // Based on tutorial from https://www.geeksforgeeks.org/how-to-convert-speech-to-text-in-android/
        SpeechRecognizer recognizer;
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the location");

        //Sets up listener for the speaker button
        speechButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speechToText(v);
            }
        });

        //---------------------------------- Search bar ----------------------------------//
        //Retrieves POI information from the database
        locationList = new ArrayList<>();
        ConnectionHelper.getDatabaseInfo(getApplicationContext(), URL, REQUEST_CODE_POI, locationList, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                searchBar.setAdapter(new ArrayAdapter<>(HomePageActivity.this,android.R.layout.simple_spinner_dropdown_item, locationList));
                searchBar.setTitle(getString(R.string.search_spinner_title));
                searchBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String location = parent.getItemAtPosition(position).toString();

                        //pass location info to algorithm interface

                        openNavigation(location);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        locationList.add("Elevator");
        locationList.add("Entrance");
        locationList.add("Service Desk");
        searchBar.setAdapter(new ArrayAdapter<>(HomePageActivity.this,android.R.layout.simple_spinner_dropdown_item, locationList));
        searchBar.setTitle(getString(R.string.search_spinner_title));
        searchBar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String location = parent.getItemAtPosition(position).toString();

                        //pass location info to algorithm interface

                        openNavigation(location);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        //------------------------------- Announcements ------------------------------//
        //Retrieves announcement info from the database
        items = new ArrayList<>();
        ConnectionHelper.getDatabaseInfo(getApplicationContext(), URL, REQUEST_CODE_ANNOUNCEMENT, items, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                //Populates the announcement list with the announcements
                adapter = new ArrayAdapter<String>(HomePageActivity.this, android.R.layout.simple_list_item_1, items){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        TextView item = (TextView) super.getView(position,convertView,parent);
                        item.setTypeface(Typeface.createFromAsset(getAssets(),"amethysta.ttf"));
                        return item;
                    }
                };
                announcementList.setAdapter(adapter);
            }
        });
    }

    float x1,y1,x2,y2;
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if(x1+175<x2){
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
        }
        return false;
    }

    /**
     * Speech to text functionality for the search bar
     */
    public void speechToText(View v){
        try {
            startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(HomePageActivity.this, "Failed. Check your microphone permissions with Google" , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Functions related to speech to text functionality
     * Code from https://www.geeksforgeeks.org/how-to-convert-speech-to-text-in-android/
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SPEECH_INPUT){
            if(resultCode == RESULT_OK && data!=null){
                //Need to test if the result is within the list of possible location
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                String formatted = result.get(0).substring(0,1).toUpperCase() + result.get(0).substring(1);
                Log.d("voiceoutput",formatted);
                if(locationList.contains(formatted)){
                    openNavigation(result.get(0));
                }
            }
        }
    }
    /**
     * Starts the intent for navigation and displays a message for the user
     * @param location desired location of the user
     */
    protected void openNavigation(String location){
        String msg = "Finding the best route to "+location+"...";
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,NavigationScreen.class);
        intent.putExtra("destination",location);
        startActivity(intent);
        finish(); //Added to stop a bug where the navigation wont reopen after backing once and trying to get back to navigation
    }
}