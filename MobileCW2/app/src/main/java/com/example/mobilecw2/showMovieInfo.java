package com.example.mobilecw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.mobilecw2.EventsData.FAVOURITE_MOVIE;
import static com.example.mobilecw2.EventsData.ID;
import static com.example.mobilecw2.EventsData.MOVIE_ACTORS;
import static com.example.mobilecw2.EventsData.MOVIE_DIRECTOR;
import static com.example.mobilecw2.EventsData.MOVIE_NAME;
import static com.example.mobilecw2.EventsData.MOVIE_RATING;
import static com.example.mobilecw2.EventsData.MOVIE_REVIEW;
import static com.example.mobilecw2.EventsData.MOVIE_YEAR;
import static com.example.mobilecw2.EventsData.TABLE_NAME;

public class showMovieInfo extends AppCompatActivity {
    String movieName;
    EditText movieTitle;
    EditText movieYear;
    EditText movieDirector;
    EditText movieActers;
    EditText movieReview;
    EditText favStatus;
    RatingBar ratingBar;
    EventsData eventsData;
    String id;
    int movie_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_info);
        movieTitle=findViewById(R.id.movieTitle);
        movieYear=findViewById(R.id.movieYear);
        movieDirector=findViewById(R.id.movDirec);
        movieActers=findViewById(R.id.actorList);
        movieReview=findViewById(R.id.movRev);
        favStatus=findViewById(R.id.favMovie);
        ratingBar=findViewById(R.id.ratingBar);
        eventsData=new EventsData(this);

        Intent intent = getIntent();
        movieName = intent.getStringExtra(EditMovies.EXTRA_MESSAGE); //getting the movie selected by the user from the EditMovies Class
        getMovieInfo();


    }

    public void getMovieInfo(){
        System.out.println(movieName);
        Cursor cursor = eventsData.getSelectedMovie(movieName);  //gets all the information of the row of the selected movie
        if (cursor.getCount() == 0){  //checks whether data is present in the database
            Log.e("database","no data");
            System.out.println("no data");
        }else {
            while (cursor.moveToNext()) {  //adding and setting all the information taken from the database of the selected movie
                id=cursor.getString(0);
                movieTitle.setText(cursor.getString(1));
                movieYear.setText(cursor.getString(2));
                movieDirector.setText(cursor.getString(3));
                movieActers.setText(cursor.getString(4));
                float rating=Float.parseFloat(cursor.getString(5));
                ratingBar.setRating(rating);
                movieReview.setText(cursor.getString(6));
                favStatus.setText(cursor.getString(7));
            }
        }

    }

    public void updateInfo(View view) {
        if(TextUtils.isEmpty(movieTitle.getText().toString()) ||  TextUtils.isEmpty(movieDirector.getText().toString()) ||  //checks whether all the field data are entered without leaving them empty
                TextUtils.isEmpty(movieActers.getText().toString()) || TextUtils.isEmpty(movieReview.getText().toString())
                || TextUtils.isEmpty(movieYear.getText().toString())){
            checkEmptyFields();
        }
        else {
            movie_year= Integer.parseInt(movieYear.getText().toString());
            String favStat=favStatus.getText().toString();
            System.out.println(favStat);
            if(movie_year<=1895){  //checks whether the entered movie year is less than or equal to 1895
                checkMovieYear();
            }
            else {
                String movieName= String.valueOf(movieTitle.getText());
                String movYear= String.valueOf(movieYear.getText());
                String movieDir= String.valueOf(movieDirector.getText());
                String movieAct= String.valueOf(movieActers.getText());
                String rating= String.valueOf(ratingBar.getRating());
                String movieRev= String.valueOf(movieReview.getText());
                String favState= String.valueOf(favStatus.getText());
                addDetailsToDb(id,movieName,movYear,movieDir,movieAct,rating,movieRev,favState);
                displayMessage();

            }
        }

    }

    public void addDetailsToDb(String id,String movieName, String movieYear, String movieDir, String movieAct, String rating, String movieRev, String favState){
        SQLiteDatabase database=eventsData.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MOVIE_NAME,movieName);
        contentValues.put(MOVIE_YEAR,movieYear);
        contentValues.put(MOVIE_DIRECTOR,movieDir);
        contentValues.put(MOVIE_ACTORS,movieAct);
        contentValues.put(MOVIE_RATING,rating);
        contentValues.put(MOVIE_REVIEW,movieRev);
        contentValues.put(FAVOURITE_MOVIE,favState);
        database.update(TABLE_NAME,contentValues,ID+" =?",new String[]{id});  //updating the database with the updated information entered by te user
        database.close();
    }

    private void displayMessage() {        //a method used to show a message to the user that he has updated the information
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Movie Information Update Successfully!");
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

    private void checkEmptyFields() {        //alertDialog method is used to show a Alert if the user press the  submit button without entering a character
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Enter all the Details Required");
        dialog.setTitle("Alert!");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void checkMovieYear() {        //alertDialog method is used to show a Alert if the user has entered a wrong year
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Enter a Movie Year Greater than 1895!");
        dialog.setTitle("Alert!");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}