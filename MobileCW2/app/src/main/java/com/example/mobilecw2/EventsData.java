package com.example.mobilecw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Movie Information.db";
    private static final int DATABASE_VERSION = 1;
    /* Create a helper object for the Events database */

    public static final String TABLE_NAME = "MovieDetails" ;
    // Columns in the Events database
    public static final String ID = "ID" ;
    public static final String MOVIE_NAME = "MOVIE_NAME" ;
    public static final String MOVIE_YEAR = "MOVIE_YEAR" ;
    public static final String MOVIE_DIRECTOR = "MOVIE_DIRECTOR" ;
    public static final String MOVIE_ACTORS = "MOVIE_ACTORS" ;
    public static final String MOVIE_RATING = "MOVIE_RATING" ;
    public static final String MOVIE_REVIEW = "MOVIE_REVIEW" ;
    public static final String FAVOURITE_MOVIE = "FAVOURITE_MOVIE" ;
    String[] FROM={MOVIE_NAME,MOVIE_YEAR,MOVIE_DIRECTOR,MOVIE_ACTORS,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE_MOVIE};


    public EventsData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,MOVIE_NAME TEXT,MOVIE_YEAR TEXT,MOVIE_DIRECTOR TEXT,MOVIE_ACTORS TEXT,MOVIE_RATING TEXT,MOVIE_REVIEW TEXT,FAVOURITE_MOVIE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String movieName, String movieYear, String movieDirector, String movieActors ,String movieRating,String movieReview, String favMovie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_NAME,movieName);
        values.put(MOVIE_YEAR,movieYear);
        values.put(MOVIE_DIRECTOR,movieDirector);
        values.put(MOVIE_ACTORS,movieActors);
        values.put(MOVIE_RATING,movieRating);
        values.put(MOVIE_REVIEW,movieReview);
        values.put(FAVOURITE_MOVIE,favMovie);
        db.insert(TABLE_NAME,null,values);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from MovieDetails" ,null);
        return cursor;
    }

    public Cursor getFavMovies(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select MOVIE_NAME from MovieDetails where FAVOURITE_MOVIE=?",new String[]{"YES"}); //Selects all the favourite movies with YES
        return cursor;
        
    }

    public Cursor getSelectedMovie(String movieName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from MovieDetails where MOVIE_NAME=?",new String[]{movieName});
        return cursor;

    }

    public Cursor getSearchResult(String searchName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, MOVIE_NAME + " LIKE ? OR "+MOVIE_DIRECTOR+" LIKE ? OR "+MOVIE_ACTORS+" LIKE ?",
                new String[]{"%"+searchName+"%","%"+searchName+"%","%"+searchName+"%"}, null, null,
                null);  //retrieves all the data similar in the fields MOVIE_NAME,MOVIE_DIRECTOR,MOVIE_ACTORS searched by the user
        return cursor;

    }






}