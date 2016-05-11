package com.edgardo.movil.architect.GetResources;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import com.edgardo.movil.architect.DB.DataBase;
import com.edgardo.movil.architect.DB.TData;
import com.edgardo.movil.architect.FirebaseModels.Activity;
import com.edgardo.movil.architect.Activity.Maps.ActivityMapFragment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecording implements Serializable {

    private MediaRecorder myAudioRecorder;
    private MediaPlayer mediaPlayer = null;
    private String outputFile = null;
    private ActivityMapFragment activityMapFragment = null;
    private Activity activity;

    private static final String AUDIO_RECORDER_FOLDER = "Proyecto/AudioRecorder";
    private static final String AUDIO_RECORDER_FILE_EXT = ".mp3";

    public AudioRecording() {

    }

    public AudioRecording(ActivityMapFragment activityMapFragment, Activity activity) {
        this.activityMapFragment = activityMapFragment;
        this.activity = activity;
    }

    public void starRecording(){
        try {
            if(mediaPlayer != null){ if(mediaPlayer.isPlaying()){ mediaPlayer.stop(); } }

            outputFile = getFilename();
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            myAudioRecorder.setOutputFile(outputFile);
            myAudioRecorder.prepare();
            myAudioRecorder.start();

        } catch (IOException e) {e.printStackTrace(); }

    }

    public void stopRecording(){
        try {
            if (myAudioRecorder != null) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                activityMapFragment.setMicMarker();
                DataBase db = new DataBase(activityMapFragment.getActivity().getApplicationContext());
                TData audio = new TData(activity.getName(), activityMapFragment.user.getName(), outputFile, "audio");
                db.insertData(audio);
                Toast.makeText(activityMapFragment.getActivity().getApplicationContext(), "Grabado finalizado", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            File a = new File(outputFile);
            a.delete();
            Toast.makeText(activityMapFragment.getActivity().getApplicationContext(), "No puedes grabar un audio tan corto", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAudio(String audioName){

        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        audioName = file.getAbsolutePath() + "/" + audioName;

        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioName);
            mediaPlayer.prepare();
            mediaPlayer.start();

        }

        catch (IOException e) { e.printStackTrace(); }
    }

    public  void stopAudio(){
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "AUD_" + timeStamp;

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + audioFileName +  AUDIO_RECORDER_FILE_EXT);
    }

}
