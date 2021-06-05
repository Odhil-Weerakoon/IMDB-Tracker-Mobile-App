package com.example.mobilecw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import static com.example.mobilecw2.EventsData.FAVOURITE_MOVIE;
import static com.example.mobilecw2.EventsData.MOVIE_NAME;
import static com.example.mobilecw2.EventsData.TABLE_NAME;

public class DisplayMovies extends AppCompatActivity {

    ListView listView;
    EventsData eventsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        eventsData=new EventsData(this); //create the EventData class object
        listView = findViewById(R.id.dispList);
        getMovies();

    }

    public void getMovies(){
        Cursor cursor = eventsData.getData(); //creating the cursor object to access the getData method in the EventData Class
        ArrayList<String> movieTitles=new ArrayList<>();
        while(cursor.moveToNext()){  //moves the cursor to next line to get the the films one by one and add to array list
            movieTitles.add(cursor.getString(1));
        }
        Collections.sort(movieTitles);//sorting the films according to the alphabetical order
        System.out.println(movieTitles);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,movieTitles );// is an Android SDK class for adapting an array of objects
        // as a dataSource. Adding the arraylist of movies to the Array Adapter
        listView.setAdapter(listAdapter);
    }


    public void addFavouriteMovie(View view) {
        for(int i=0; i<listView.getCount();i++){
            SparseBooleanArray selectMovie=listView.getCheckedItemPositions(); //to the check whether the movie is ticked or not in its position
            if(selectMovie.get(i)) {//if the movie is ticked then getting the name of the film in the position and passing to the addFavMovieToDb
                String movieName = (String) listView.getItemAtPosition(i);
                addFavMovieToDb(movieName);

            }
        }
    }

    public void addFavMovieToDb(String favMovie){
        SQLiteDatabase database=eventsData.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FAVOURITE_MOVIE,"YES"); //Adds YES to the Favourite movie column
        database.update(TABLE_NAME,contentValues,MOVIE_NAME+" =?",new String[]{favMovie}); //updates the favourite column by adding YES to the required place
        database.close();
        displayMessage();
    }

    private void displayMessage() {        //Dialog Box  is used to show that user has added the movie to the his favourite list
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Movies Added To the Favourite List Successfully!");
        dialog.setTitle("Success!");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }



}