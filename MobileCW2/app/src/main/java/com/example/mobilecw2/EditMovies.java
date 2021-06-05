package com.example.mobilecw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class EditMovies extends AppCompatActivity {

    ListView listView;
    EventsData eventsData;
    String movieClicked;
    public static final String EXTRA_MESSAGE="com.example.mobilecw2.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);
        eventsData=new EventsData(this);
        listView = findViewById(R.id.editList);
        getMovies();
    }

    public void getMovies(){
        Cursor cursor = eventsData.getData();
        ArrayList<String> movieTitles=new ArrayList<>();
        while(cursor.moveToNext()){
            movieTitles.add(cursor.getString(1));  //getting all the Movies Saved in the Database
        }
        Collections.sort(movieTitles);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,movieTitles );// is an Android SDK class for adapting an array of objects
        // as a dataSource.
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieClicked=(String)parent.getItemAtPosition(position);
                Intent intent=new Intent(EditMovies.this,showMovieInfo.class);
                intent.putExtra(EXTRA_MESSAGE,movieClicked); //passing the movie selected by the user to the next intent showMovieInfo
                startActivity(intent);

            }
        });
    }
}