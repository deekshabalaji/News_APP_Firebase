package com.example.deeksha.myhomework3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.deeksha.myhomework3.data.Contract;
import com.example.deeksha.myhomework3.data.DBHelper;
import com.example.deeksha.myhomework3.data.DBUtils;


/**
 * Created by deeksha on 7/26/17.
 */

// main activity implements LoaderManager.LoaderCallbacks<Void>
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,
        NewsAdapter.ItemClickListener{

    static final String TAG = "mainactivity";

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private EditText EditText;
    private ProgressBar pbr;
    //variables for cursor and database
    private SQLiteDatabase db;
    private Cursor cursor;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //https://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager.html
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        pbr = (ProgressBar) findViewById(R.id.progress);

        //if the app is running for the first time
        //then it loads the data into database
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        //if it for the second or later one,it just get refresh and produce the new result
        ScheduleUtils.scheduleRefresh(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#getReadableDatabase()
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DBUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        switch (itemNumber) {
            case R.id.search:
                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //implemented the methods for loader
    //https://developer.android.com/guide/components/loaders.html
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            //https://developer.android.com/reference/android/content/Loader.html#onStartLoading()
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                pbr.setVisibility(View.VISIBLE);
            }
    //https://developer.android.com/reference/android/content/AsyncTaskLoader.html
            @Override
            public Void loadInBackground() {
                Refreshtask.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        pbr.setVisibility(View.INVISIBLE);
        //gets data from the database
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DBUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {}
//update the onlistitemclick() to accept the cursor as on eof the arguments
    @Override
    public void onListItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_URL));
        Log.d(TAG, String.format("Url %s", url));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
