package com.example.mobilecw2;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;;
import java.util.ArrayList;



public class Ratings extends AppCompatActivity {
    ListView listView;
    EventsData eventsData;
    String movieClicked;
    String url;
    ArrayList<String>data=new ArrayList<>();
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE="com.example.mobilecw2.extra.MESSAGE";
    String titleBaseUrl="https://imdb-api.com/en/API/SearchTitle/k_votwil61/";
    String ratingBaseUrl="https://imdb-api.com/en/API/UserRatings/k_votwil61/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        eventsData=new EventsData(this);
        listView = findViewById(R.id.allMovieList);
        getMovies();

    }

    public void getMovies(){
        Cursor cursor = eventsData.getData();
        ArrayList<String> movieTitles=new ArrayList<>();
        while(cursor.moveToNext()){
            movieTitles.add(cursor.getString(1));
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,movieTitles );// is an Android SDK class for adapting an array of objects
        // as a dataSource. Adapters are used by Android to treat a result set uniformly whether it's from a database, file, or in-memory objects so that it can be displayed in a UI element.
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieClicked=(String)parent.getItemAtPosition(position);

            }
        });
    }


    public void findRating(View view) {
        Intent intent=new Intent(Ratings.this,MovieRatings.class);
        intent.putExtra(EXTRA_MESSAGE,movieClicked);  //Passing the movie selected by the user to the intent MovieRating
        startActivity(intent);

    }


}