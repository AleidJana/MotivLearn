package com.example.jana.motivlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class CommentAdapter extends BaseAdapter {

    Context context;
    String NameList[];
    String HoursList[];
    String ContentList[];

    LayoutInflater inflter;

    public CommentAdapter(Context applicationContext, String[] NameList, String[] HoursList,String[] ContentList) {
        this.context = context;
        this.NameList = NameList;
        this.HoursList = HoursList;
        this.ContentList = ContentList;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return NameList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.comment_item, null);
        TextView Name = (TextView) view.findViewById(R.id.textViewName);
        Name.setText(NameList[i]);
        TextView Hours = (TextView) view.findViewById(R.id.textViewHours);
        Hours.setText(HoursList[i]);
        TextView Content = (TextView) view.findViewById(R.id.content);
        Content.setText(ContentList[i]);

        return view;
    }
}
