package com.takeme.takemeapp.listadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.takeme.takemeapp.R;

/**
 * Created by tal Zamir on 08/09/2015.
 */
public class NavigationDrawerListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] actions;
    private final int[] imageId;

    public NavigationDrawerListAdapter(Context context, String[] actions, int[] imageId) {

        super(context, R.layout.drawer_item, actions);
        this.context = context;
        this.actions = actions;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the layout inflater.
        LayoutInflater lInflate = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = lInflate.inflate(R.layout.drawer_item,null,true);
        TextView actionTextView = (TextView)convertView.findViewById(R.id.nav_action_text);
        ImageView actionImageView = (ImageView)convertView.findViewById(R.id.nav_action_icon);

        actionTextView.setText(actions[position]);
        actionImageView.setImageResource(imageId[position]);
//        actionImageView.setImageResource(R.drawable.ic_drawer);
        return convertView;

    }
}
