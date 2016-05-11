package com.edgardo.movil.architect.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edgardo.movil.architect.Activity.Adapters.SubmitAdapter;
import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.FirebaseModels.Submit;
import com.edgardo.movil.architect.R;

public class ViewActivitiesActivity extends AppCompatActivity {

    private ListView submitsListLV;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_view);
        activity = (Activity) getIntent().getSerializableExtra("activity");
        submitsListLV = (ListView) findViewById(R.id.submitsLV);
        loadSubmits();
        setOnClick();
    }

    private void loadSubmits(){
        SubmitAdapter adapter = new SubmitAdapter(activity.getSubmits(),ViewActivitiesActivity.this);
        submitsListLV.setAdapter(adapter);
    }

    private void setOnClick(){
        submitsListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent studentSubmit = new Intent(ViewActivitiesActivity.this, ViewActivityActivity.class);
                studentSubmit.putExtra("submit",(Submit)view.getTag());
                studentSubmit.putExtra("activity",activity);
                startActivity(studentSubmit);
            }
        });
    }
}
