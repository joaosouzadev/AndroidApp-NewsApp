package com.example.android.newsapp;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<News> {

    public static final String LOG_TAG = MainActivity.class.getName();

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the {@link NewsClass} object located at this position in the list
        News currentNews = getItem(position);

        TextView newsTitleTextView = listItemView.findViewById(R.id.newsTitle);
        newsTitleTextView.setText(currentNews.getTitle());

        TextView newsCategoryTextView = listItemView.findViewById(R.id.news_category);
        newsCategoryTextView.setText(currentNews.getCategory());

        TextView newsAuthorTextView = listItemView.findViewById(R.id.news_author);
        newsAuthorTextView.setText(currentNews.getAuthor());

        TextView newsDateTextView = listItemView.findViewById(R.id.news_date);

        SimpleDateFormat dateFormatJSON = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dateFormatFinal = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        try {
            Date dateNews = dateFormatJSON.parse(currentNews.getDate());
            String date = dateFormatFinal.format(dateNews);
            newsDateTextView.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.news_image);
        new newsImageAsyncTask(imageView).execute(currentNews.getImageUrl());

        return listItemView;
    }

    // inner class to download image and return it
    private class newsImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;


        public newsImageAsyncTask(ImageView btmpImage) {
            this.image = btmpImage;

        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap image = null;

            try {
                InputStream in = new java.net.URL(url).openStream();
                image = BitmapFactory.decodeStream(in); // decode the InputStream to image
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {
            //set the image obtained
            image.setImageBitmap(result);
        }
    }


}
