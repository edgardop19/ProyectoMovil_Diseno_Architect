package com.edgardo.movil.architect.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edgardo.movil.architect.Activity.Maps.ViewActivityMapFragment;
import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.FirebaseModels.Submit;
import com.edgardo.movil.architect.R;

public class ViewActivityActivity extends AppCompatActivity {

    private Submit submit;
    private Activity activity;
    private ViewActivityMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        submit = (Submit) getIntent().getSerializableExtra("submit");
        activity = (Activity) getIntent().getSerializableExtra("activity");
        mapFragment = (ViewActivityMapFragment) getFragmentManager().findFragmentById(R.id.mapViewActivity);
        mapFragment.setSubmit(submit);
        mapFragment.setActivity(activity);
        mapFragment.setFragmentManager(getSupportFragmentManager());

    }
}
