package com.example.jana.motivlearn;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class CommentAdapter extends BaseAdapter {

    Context context;
    List<String> NameList;
    List<String> HoursList;
    List<String> ContentList;

    LayoutInflater inflter;

    public CommentAdapter(Context applicationContext, List<String> NameList, List<String> HoursList, List<String> ContentList) {
        this.context = context;
        this.NameList = NameList;
        this.HoursList = HoursList;
        this.ContentList = ContentList;

        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
       return NameList.size();
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
        Name.setText(NameList.get(i));
        TextView Hours = (TextView) view.findViewById(R.id.textViewHours);
        Hours.setText(HoursList.get(i));
        TextView Content = (TextView) view.findViewById(R.id.content);
        Content.setText(ContentList.get(i));
        return view;
    }
}
