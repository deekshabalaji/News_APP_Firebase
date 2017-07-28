package com.example.deeksha.myhomework3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deeksha.myhomework3.data.Contract;
import com.squareup.picasso.Picasso;

/**
 * Created by deeksha on 7/26/17.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    final private ItemClickListener listener;
    private Context context;

    //accessing data with the cursor
    private Cursor mCursor;

    //replaced the arraylist of pojo file to cursor
    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.mCursor = cursor;
        this.listener = listener;
    }

    //modified the interface
    public interface ItemClickListener {
        void onListItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean ap = false;
        View view = inflater.inflate(R.layout.item, viewGroup, ap);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    //instead of sizeof() of arraylist
    // i took the count of the cursor to access the data
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView Title;
        public final TextView Description;
        public final TextView Time;
        //added the field for image and implemented in such a way that it been displayed by using picasso
        public final ImageView img;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title);
            Description = (TextView) itemView.findViewById(R.id.description);
            Time = (TextView) itemView.findViewById(R.id.time);
            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        public void bind(int pos) {
            mCursor.moveToPosition(pos);

            Title.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_TITLE)));
            Description.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_DESCRIPTION)));
            Time.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_PUBLISHED_AT)));

            String urlToImage = mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_URL_TO_IMAGE));
            Log.d(TAG, urlToImage);
            //Use Picasso to load a thumbnail for each news item in the recycler view.
            if(urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
                        .into(img);
            }
        }

        //added the cursor
        @Override
        public void onClick(View v) {

            listener.onListItemClick(mCursor, getAdapterPosition());
        }
    }
}
