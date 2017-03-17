package com.example.android.newsreport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gowon on 2017-02-26.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final News currentNews = getItem(position);

        String dateString = "", timeString = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        try {
            Date date = format.parse(currentNews.getPublishDate());

            format = new SimpleDateFormat("yyyy MM dd", Locale.US);
            dateString = format.format(date);

            format = new SimpleDateFormat("HH:mm", Locale.US);
            timeString = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.mTitleView.setText(currentNews.getTitle());
        viewHolder.mSectionNameView.setText(currentNews.getSectionName());
        viewHolder.mDateView.setText(dateString);
        viewHolder.mTimeView.setText(timeString);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title_text_view)
        TextView mTitleView;
        @BindView(R.id.section_name_text_view)
        TextView mSectionNameView;
        @BindView(R.id.date_text_view)
        TextView mDateView;
        @BindView(R.id.time_text_view)
        TextView mTimeView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
