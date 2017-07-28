package com.example.deeksha.myhomework3.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.COLUMN_DESCRIPTION;
import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.COLUMN_PUBLISHED_AT;
import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.COLUMN_TITLE;
import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.COLUMN_URL;
import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.COLUMN_URL_TO_IMAGE;
import static com.example.deeksha.myhomework3.data.Contract.TABLE_ARTICLES.TABLE_NAME;


/**
 * Created by deeksha on 7/26/17.
 */

public class DBUtils {

    //retrieve all data from the database
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    //inserting data(json response from the website) into database
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsItems) {

        db.beginTransaction();
        try {
            for (NewsItem a : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_TITLE, a.getTitle());
                cv.put(COLUMN_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_URL, a.getUrl());
                cv.put(COLUMN_URL_TO_IMAGE, a.getUrlToImage());
                cv.put(COLUMN_PUBLISHED_AT, a.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    //delete all data from the database
    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}