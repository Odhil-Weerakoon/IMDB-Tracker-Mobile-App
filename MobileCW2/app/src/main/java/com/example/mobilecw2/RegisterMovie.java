package com.example.mobilecw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterMovie extends AppCompatActivity {

    Button saveDetails;
    EditText movieName;
    EditText movieYear;
    EditText movieDirector;
    EditText movieActors;
    EditText movieRating;
    EditText movieReview;
    int movie_year;
    int movie_Rating;

    private final EventsData eventsData=new EventsData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
    }

    public void saveBTn(View view) {
        saveDetails=findViewById(R.id.save_Btn);
        movieName=findViewById(R.id.movie_Title);
        movieYear=findViewById(R.id.movie_Year);
        movieDirector=findViewById(R.id.movie_Director);
        movieActors=findViewById(R.id.movie_Actors);
        movieRating=findViewById(R.id.movie_Rating);
        movieReview=findViewById(R.id.movie_Review);



        if(TextUtils.isEmpty(movieName.getText().toString()) ||  TextUtils.isEmpty(movieDirector.getText().toString()) ||
                TextUtils.isEmpty(movieActors.getText().toString()) || TextUtils.isEmpty(movieReview.getText().toString())
                || TextUtils.isEmpty(movieYear.getText().toString())  || TextUtils.isEmpty(movieRating.getText().toString())){ //checks whether all the fileds are not left empty
            checkEmptyFields();
        }
        else {
            movie_year= Integer.parseInt(movieYear.getText().toString());
            movie_Rating= Integer.parseInt(movieRating.getText().toString());

            if(movie_Rating==0 || movie_Rating>10){   //checks whether the rating of movie is between 1-10
                checkRatingRange();
            }
            else if(movie_year<=1895){ //checks whether the movie entered is greater than 1895
                checkMovieYear();
            }
            else {
                eventsData.insertData(movieName.getText().toString(),String.valueOf(movie_year),movieDirector.getText().toString(),movieActors.getText().toString(),String.valueOf(movie_Rating),movieReview.getText().toString(),"NO");
                Toast.makeText(getApplicationContext(),"Details of the Movie Saved Successfully!", Toast.LENGTH_SHORT).show();
                movieName.getText().clear();
                movieYear.getText().clear();
                movieDirector.getText().clear();
                movieActors.getText().clear();
                movieRating.getText().clear();
                movieReview.getText().clear();
                registerMessage();
            }
        }
    }

    private void checkMovieYear() {        // method is used to show a Alert if the user enter a wrong year out of range
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

    private void checkEmptyFields() {        // method is used to show a Alert if the user leaves some fields empty
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

    private void checkRatingRange() {        // method is used to show a Alert if the user enter invalid movie rating
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Enter a Movie Rating Between 1 and 10");
        dialog.setTitle("Alert!");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void registerMessage() {        // method is used to show  the user he has registered the movie successfully
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("You Have Registered the Movie Successfully!");
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