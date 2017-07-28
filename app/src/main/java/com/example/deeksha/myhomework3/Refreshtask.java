package com.example.deeksha.myhomework3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.deeksha.myhomework3.data.DBHelper;
import com.example.deeksha.myhomework3.data.DBUtils;
import com.example.deeksha.myhomework3.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by deeksha on 7/26/2017.
 */

public class Refreshtask {

    public static final String ACTION_REFRESH = "Refresh";
    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DBUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DBUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }

}
