package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<News> {


    public Adapter(Context context, List<News> newsList) {

        super(context, 0, newsList);

    }



    /**

     * Returns a list item view that displays information about the news at the given position

     * in the list of news

     */

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,

        // otherwise, if convertView is null, then inflate a new list item layout.

        View listItemView = convertView;

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(

                    R.layout.list_item, parent, false);

        }



        // Find the news at the given position in the list of news

        News currentNews = getItem(position);



        // Find the TextView with view ID article_title)

        TextView articleTitleView = (TextView) listItemView.findViewById(R.id.article_title);

        // Display the title of the current news in that TextView

        articleTitleView.setText(currentNews.getTitle());



        // Find the TextView with view ID section

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);

        // Display the section of the current news in that TextView

        sectionView.setText(currentNews.getSection());



        // Find the TextView with view ID date

        TextView dataView = (TextView) listItemView.findViewById(R.id.date);

        // Display the data of the current news in that TextView

        dataView.setText(currentNews.getDate());

        // Find the TextView with view ID author

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        // Display the Author name of the current news in that TextView

        authorView.setText(currentNews.getAuthor());



        // Return the list item view that is now showing the appropriate data

        return listItemView;

    }



}