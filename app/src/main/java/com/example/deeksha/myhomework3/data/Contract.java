package com.example.deeksha.myhomework3.data;

import android.provider.BaseColumns;

/**
 * Created by deeksha on 7/26/17.
 */

public class Contract {

    //it defines constants that help application
    // work with the content URIs, column names, intent actions, and other features of a content provider.
    //    /* Inner class that defines the table contents */
    //https://developer.android.com/training/basics/data-storage/databases.html
    public static final class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "news_app";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "published_at";
    }

}
