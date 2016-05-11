package com.edgardo.movil.architect.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.edgardo.movil.architect.Activity.Maps.NewActivityMap;
import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.R;
import com.firebase.client.Firebase;

public class NewActivity extends AppCompatActivity {

    SeekBar radiusSeekBar;
    NewActivityMap map;
    int radius;
    private Firebase root;
    private String firebaseUrl = "https://apparchitect.firebaseio.com/activities/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_new);
        root = new Firebase(firebaseUrl);
        map = (NewActivityMap) getFragmentManager().findFragmentById(R.id.newMap);

        radiusSeekBar = (SeekBar) findViewById(R.id.radiusSeekBar);
        radiusSeekBar.setEnabled(false);

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                radius = i;
                map.changeRadius(radius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button send = (Button) findViewById(R.id.createActivityBtn);

        assert send != null;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText t=(EditText)findViewById(R.id.editText);
                //TODO: Agregar validacion de nombre (si este ya existe)
                // TODO: Verfiicar el marcador antes de enviar
                Activity activity = new Activity();
                activity.setName(((EditText)findViewById(R.id.activityNameet)).getText().toString());
                activity.setRadius(radius);
                activity.setLatitude(map.markLatLng.latitude);
                activity.setLongitude(map.markLatLng.longitude);
                //String note = activity.getRadius() + "m" + " alrededor de la " + map.getAddressFromLatLng(map.markLatLng);
                String note = "Descripción: "+t.getText();
                activity.setNote(note);
                root.child(activity.getName()).setValue(activity);
                Intent i = new Intent(NewActivity.this,ActivitiesActivity.class);
                startActivity(i);
            }
        });

    }
}
