package com.example.mobilecw2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
;

public class MovieRatings extends AppCompatActivity {

    String movieName;
    ListView listView;
    String title;
    String id;
    String image;
    String rating;

    ArrayList<String> data=new ArrayList<>();
    ArrayList<String> iD=new ArrayList<>();
    ArrayList<String> ratings=new ArrayList<>();
    ArrayList<String> allData=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    String titleBaseUrl="https://imdb-api.com/en/API/SearchTitle/k_81og7bju/";
    String ratingBaseUrl="https://imdb-api.com/en/API/UserRatings/k_81og7bju/";
    public static final String EXTRA_MESSAGE="com.example.mobilecw2.extra.MESSAGE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_ratings);
        listView = findViewById(R.id.movieRatingList);
        Intent intent = getIntent();
        movieName = intent.getStringExtra(Ratings.EXTRA_MESSAGE);
        titleBaseUrl="https://imdb-api.com/en/API/SearchTitle/k_81og7bju/"+movieName;
        System.out.println(movieName);
        new GetTitleDetails().execute();


    }

    public class GetTitleDetails extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);  //Json is  used for transmitting data in web applications
                JSONArray jsonArray=(JSONArray) jsonObject.get("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                    title = (String) jsonObject1.get("title");
                    id=(String) jsonObject1.get("id");
                    image=(String) jsonObject1.get("image");
                    iD.add(i,id);
                    data.add(i,title);
                    images.add(i,image);
                }
                new GetRatingDetails().execute();

            } catch (Exception e) {
                Log.d(LOG_TAG, "onPostExecute: error");
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                Log.d(LOG_TAG,"Inside doInBackground()");
                URL url = new URL(titleBaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//send request to server
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));//To get the response from the Server
                //BufferedReader is a Java class to reads the text from an Input stream
                String value = bufferedReader.readLine(); //method to read a line from a text.

                httpURLConnection.disconnect();
                bufferedReader.close();



                return value;

            } catch (Exception e) {
                Log.d(LOG_TAG,"Error in do in background");
                e.printStackTrace();
            }
            return null;

        }
    }

    public class GetRatingDetails extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String result1) {
            try {
                System.out.println(result1);

                getMovies();


            } catch (Exception e) {
                Log.d(LOG_TAG, "onPostExecute: error");
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                for(int i=0;i<iD.size();i++){
                    System.out.println(id);
                    Log.d(LOG_TAG,"Inside doInBackground()");
                    URL url = new URL(ratingBaseUrl+iD.get(i));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//send request to server
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));//To get the response from the Server
                    //BufferedReader is a Java class to reads the text from an Input stream
                    String value = bufferedReader.readLine(); //method to read a line from a text.
                    httpURLConnection.disconnect();
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(value);
                    Log.d(LOG_TAG,value);
                    rating = jsonObject.getString("totalRating");
                    ratings.add(i,rating);
                    allData.add(i,data.get(i)+": "+ratings.get(i));

                }
                return null;
            } catch (Exception e) {
                Log.d(LOG_TAG,"Error in do in background");
                e.printStackTrace();
            }
            return null;
        }
    }

    public void getMovies(){
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,allData);// is an Android SDK class for adapting an array of objects
        // as a dataSource. Adapters are used by Android to treat a result set uniformly whether it's from a database, file, or in-memory objects so that it can be displayed in a UI element.
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MovieRatings.this,showImage.class);
                intent.putExtra(EXTRA_MESSAGE,images.get(position)); //Passing the Url of the image of the movie selected by the user
                startActivity(intent);
            }
        });
    }















}