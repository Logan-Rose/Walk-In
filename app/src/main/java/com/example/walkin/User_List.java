package com.example.walkin;

/**
 * Adapted from Code provided for lab 5
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class User_List extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    public User_List(Activity context, List<User> users) {
        super(context, R.layout.activity_user_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_list, null, true);

        TextView textViewName = /*(TextView)*/ listViewItem.findViewById(R.id.textViewName);
        TextView textViewType = /*(TextView)*/ listViewItem.findViewById(R.id.textViewType);

        User user = users.get(position);
        textViewName.setText(user.getName());
        textViewType.setText(user.getType());
        return listViewItem;
    }
}