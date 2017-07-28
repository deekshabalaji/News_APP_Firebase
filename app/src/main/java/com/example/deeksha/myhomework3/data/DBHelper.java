package com.example.deeksha.myhomework3.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by deeksha on 7/26/17.
 */
//Create a Database Using a SQL Helper
public class DBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsitems.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create and maintain the database and tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String NEWSITEM_TABLE = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " (" +
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_ARTICLES.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_URL + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_URL_TO_IMAGE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_PUBLISHED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        Log.d(TAG, "Create SQL: " + NEWSITEM_TABLE);
        db.execSQL(NEWSITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //https://developer.android.com/training/basics/data-storage/databases.html#DbHelper
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_ARTICLES.TABLE_NAME);
        onCreate(db);
    }
}
