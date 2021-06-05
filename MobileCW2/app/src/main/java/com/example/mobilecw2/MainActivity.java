package com.example.mobilecw2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerMovieBtn = findViewById(R.id.register_Movie);
        Button displayMoviesBtn = findViewById(R.id.display_movies);
        Button favouritesBtn = findViewById(R.id.favourite);
        Button editMoviesBtn = findViewById(R.id.edit_Movies);
        Button searchBtn = findViewById(R.id.searches);
        Button ratingsBtn = findViewById(R.id.moive_ratings);

        registerMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstIntent = new Intent(MainActivity.this,RegisterMovie.class);
                startActivity(firstIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });

       displayMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(MainActivity.this,DisplayMovies.class);
                startActivity(secondIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });

        favouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thirdIntent = new Intent(MainActivity.this,Favourites.class);
                startActivity(thirdIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });

        editMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fourthIntent = new Intent(MainActivity.this,EditMovies.class);
                startActivity(fourthIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fifthIntent = new Intent(MainActivity.this,Search.class);
                startActivity(fifthIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });
        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sixthIntent = new Intent(MainActivity.this,Ratings.class);
                startActivity(sixthIntent);//Call the startActivity() method with the new Intent as the argument.
            }
        });

    }



}