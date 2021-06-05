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


public class Favourites extends AppCompatActivity {

    ListView listView;
    EventsData eventsData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        eventsData=new EventsData(this);
        listView = findViewById(R.id.favList);

        getFavMovies();




    }

    public void getFavMovies(){
        Cursor cursor = eventsData.getFavMovies();  //create the cursor object and retrieves the favourite movies
        ArrayList<String> favMovies=new ArrayList<>();
        while(cursor.moveToNext()){
            favMovies.add(cursor.getString(0)); //add the favourite movies to the FavMovies arraylist one by one
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,favMovies);// is an Android SDK class for adapting an array of objects
        // as a dataSource.adds the movies to the list View
        listView.setAdapter(listAdapter);

        for (int x=0; x<listAdapter.getCount(); x++) {
            listView.setItemChecked(x, true); //making All the items in the list view checked initially
        }
    }


    public void saveBtnOnclick(View view) {
        for(int i=0; i<listView.getCount();i++){
            SparseBooleanArray selectMovie=listView.getCheckedItemPositions();
            if(!selectMovie.get(i)) { //gets the movies that are unselected or un-ticked
                String movieName = (String) listView.getItemAtPosition(i);
                updateDb(movieName); //calls the updateDb method and pass the movies that are unselected one by one

            }
        }
    }

    public void updateDb(String favMovie){
        SQLiteDatabase database=eventsData.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FAVOURITE_MOVIE,"NO");
        database.update(TABLE_NAME,contentValues,MOVIE_NAME+" =?",new String[]{favMovie});  //updates the favourite movie column by adding NO to the unselected movies saved  by the user
        database.close();
        displayMessage();
    }

    private void displayMessage() {        //this method is used to show a message that the  user has Successfully updated his favourite movie list
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Favourite Movie List Updated Successfully!");
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