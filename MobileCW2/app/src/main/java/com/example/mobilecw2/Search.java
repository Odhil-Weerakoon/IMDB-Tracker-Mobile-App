package com.example.mobilecw2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText searchBar;
    String movName;
    String movDirect;
    String movActors;
    String finalLine;
    EventsData eventsData;
    ListView listView;
    ArrayList<String> searchData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar=findViewById(R.id.searchBox);
        listView = findViewById(R.id.searchList);

        eventsData=new EventsData(this);

    }


    public void searchInfo(View view) {
        searchData.clear();
        String search= String.valueOf(searchBar.getText()); //gets the text entered by the user to search
        Cursor cursor = eventsData.getSearchResult(search);  //gets all the information regarding the search
        if (cursor.getCount() == 0){
            Log.e("database","no data");
            System.out.println("no data");
        }else {
            while (cursor.moveToNext()) {   //Add the similar data retrieved from the database to the Array List
                movName=(cursor.getString(0));
                movDirect=(cursor.getString(2));
                movActors=(cursor.getString(3));
                finalLine="Movie Name:"+movName+"\n"+"Director:"+movDirect+"\n"+"Actors:"+movActors;
                searchData.add(finalLine);

            }
        }
        getMovies();
    }

    public void getMovies(){
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchData);// is an Android SDK class for adapting an array of objects
        // as a dataSource. Adapters are used by Android to treat a result set uniformly whether it's from a database, file, or in-memory objects so that it can be displayed in a UI element.
        listView.setAdapter(listAdapter);
    }
}