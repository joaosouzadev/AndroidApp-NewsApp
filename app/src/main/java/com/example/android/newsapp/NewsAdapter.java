package com.example.android.newsapp;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JOAO on 25-Apr-18.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public static final String LOG_TAG = MainActivity.class.getName();

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main, parent, false);
        }

        News currentNews = getItem(position);

        TextView tv_title = (TextView) listItemView.findViewById(R.id.news_title);
        tv_title.setText(currentNews.getTitle());

        TextView tv_author = (TextView) listItemView.findViewById(R.id.news_author);
        tv_author.setText(currentNews.getAuthor());

        TextView tv_category = (TextView) listItemView.findViewById(R.id.news_category);
        tv_category.setText(currentNews.getTitle());

        Date dateObject = new Date(currentNews.getDate());

        TextView tv_date = (TextView) listItemView.findViewById(R.id.news_date);
        String formattedDate = formatDate(dateObject);
        tv_date.setText(formattedDate);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.news_image);
        new newsImageAsyncTask(imageView).execute(currentNews.getImageUrl());

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }


    // inner class to download image and return it
    private class newsImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public newsImageAsyncTask(ImageView btmpImage) {
            this.image = btmpImage;
        }

        protected Bitmap doInBackground(String... urls){
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
