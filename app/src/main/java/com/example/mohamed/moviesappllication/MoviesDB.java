package com.example.mohamed.moviesappllication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohamed on 26/04/2016.
 */

public class MoviesDB extends SQLiteOpenHelper {

    private static String dataBaseName = "moviesDB";
    SQLiteDatabase moviesDB;


    public MoviesDB(Context context) {

        super(context, dataBaseName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Movie (Id integer primary key autoincrement ,Movie_Id integer not null," +
                " Path text not null , Title text not null,overview text not null,date text not null,rate double not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Movie");
        onCreate(db);
    }

    public void addNewMovie(int id, String path, String title, String overview, String date, double rate) {
        ContentValues newMovie = new ContentValues();
        newMovie.put("Path", path);
        newMovie.put("Title", title);
        newMovie.put("Movie_Id", id);
        newMovie.put("overview", overview);
        newMovie.put("date", date);
        newMovie.put("rate", rate);
        moviesDB = getWritableDatabase();
        moviesDB.insert("Movie", null, newMovie);
        moviesDB.close();
    }

    public Cursor fetchMovies() {
        moviesDB = getReadableDatabase();
        String[] Fetched_Attributes = {"Path", "Title", "Movie_Id", "overview", "date", "rate"};
        Cursor moviesCursor = moviesDB.rawQuery("select * from Movie",null);
        if (moviesCursor.getCount() != 0) {
            moviesCursor.moveToFirst();
            moviesDB.close();
            return moviesCursor;
        }
        moviesDB.close();
        return null;
    }

    public boolean checkMovie(String title) {
        moviesDB = getReadableDatabase();
        String[] Fetched_Attributes = {title};
        Cursor Users_Cursor = moviesDB.rawQuery("select * from Movie where Title like ? ", Fetched_Attributes);
        if (Users_Cursor.getCount() != 0) {
            moviesDB.close();
            return true;
        }
        moviesDB.close();
        return false;
    }

}
