package com.example.mobilecw2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class showImage extends AppCompatActivity {
    String imageId;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    Bitmap bitmap;
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        movieImage=findViewById(R.id.movImage);

        Intent intent = getIntent();
        imageId = intent.getStringExtra(MovieRatings.EXTRA_MESSAGE);
        new GetImage().execute();

    }

    public class GetImage extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            movieImage.setImageBitmap(bitmap);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.d(LOG_TAG,"Inside doInBackground()");
                URL imageUrl = new URL(imageId);
                HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();//send request to server
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));//To get the response from the Server
                //BufferedReader is a Java class to reads the text from an Input stream
                String value = bufferedReader.readLine(); //method to read a line from a text.

                httpURLConnection.disconnect();
                bufferedReader.close();


                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream()); // Class that Creates Bitmap objects from various sources, including files, streams, and byte-arrays.

                return value;

            } catch (Exception e) {
                Log.d(LOG_TAG,"Error in do in background");
                e.printStackTrace();
            }
            return null;
        }
    }
}