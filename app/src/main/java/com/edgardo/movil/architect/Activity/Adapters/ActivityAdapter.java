package com.edgardo.movil.architect.Activity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.R;

import java.util.ArrayList;


public class ActivityAdapter extends BaseAdapter {


    private ArrayList<Activity> activities;
    private Context context;

    public ActivityAdapter(ArrayList<Activity> activities, Context context) {
        this.activities = activities;
        this.context = context;
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int i) {
        return activities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Activity activity = activities.get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_activity, null);
        }
        TextView nameTV = (TextView) view.findViewById(R.id.activityNameTV);
        if(activity.getName() == null){
            System.out.println(">>>>>NULL");
        }
        nameTV.setText(activity.getName());
        TextView noteTV = (TextView) view.findViewById(R.id.activityNoteTV);
        noteTV.setText(activity.getNote());
        view.setTag(activities.get(i));
        return  view;
    }
}
