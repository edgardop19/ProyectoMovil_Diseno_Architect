package com.edgardo.movil.architect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edgardo.movil.architect.Activity.Adapters.ActivityAdapter;
import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.FirebaseModels.Submit;
import com.edgardo.movil.architect.Login.CurrentUser;
import com.edgardo.movil.architect.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ActivitiesActivity extends AppCompatActivity {

    Firebase root;
    String firebaseUrl = "https://apparchitect.firebaseio.com/activities/";
    ListView activitiesList;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Firebase.setAndroidContext(this);
        currentUser = new CurrentUser(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = new Firebase(firebaseUrl);
        activitiesList = (ListView) findViewById(R.id.activiesListLV);
        loadActivities();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(isProfessor()){
            fab.show();
        }else{
            fab.hide();
        }
      fab.show();
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(ActivitiesActivity.this, NewActivity.class);
                startActivity(i);
            }
        });

        activitiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isProfessor()){
                    Intent professorIntent = new Intent(ActivitiesActivity.this, ViewActivitiesActivity.class);
                    professorIntent.putExtra("activity", (Activity)view.getTag());
                    startActivity(professorIntent);

                }else{
                    Intent studentIntent = new Intent(ActivitiesActivity.this, ActivityActivity.class);
                    studentIntent.putExtra("activity", (Activity)view.getTag());
                    startActivity(studentIntent);
                }
            }
        });

    }

    private void loadActivities(){
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Activity> activities = new ArrayList<Activity>();
                Activity activity;
                for(DataSnapshot fbActivity : dataSnapshot.getChildren()){
                    activity = new Activity();
                    activity.setName(fbActivity.child("name").getValue().toString());
                    activity.setLatitude(Double.parseDouble(fbActivity.child("latitude").getValue().toString()));
                    activity.setLongitude(Double.parseDouble(fbActivity.child("longitude").getValue().toString()));
                    activity.setRadius(Double.parseDouble(fbActivity.child("radius").getValue().toString()));
                    activity.setNote(fbActivity.child("note").getValue().toString());
                    ArrayList<Submit> submits = new ArrayList<Submit>();
                    for(DataSnapshot submit : fbActivity.child("submits").getChildren()){
                        submits.add(submit.getValue(Submit.class));
                    }
                    activity.setSubmits(submits);
                    activities.add(activity);
                }
                ActivityAdapter adapter = new ActivityAdapter(activities, ActivitiesActivity.this);
                activitiesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private boolean isProfessor(){
        return currentUser.getType().equals("Profesor");
    }

}
