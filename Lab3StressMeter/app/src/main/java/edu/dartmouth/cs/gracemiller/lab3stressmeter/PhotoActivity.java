package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends Activity {

    int mStressScore, mPicID;
    Button mCancelButton, mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent mData = getIntent();
        mStressScore = mData.getIntExtra("stress_score", 0);
        mPicID = mData.getIntExtra("drawable_id", 0);

        Log.d("photoAct", "stress " + mStressScore);


        ImageView image;
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(mPicID);

        //listener for cancel button
        mCancelButton = (Button) findViewById(R.id.cancel_btn_id);
        mSubmitButton = (Button) findViewById(R.id.submit_btn_id);




    }

    // how do we finish for the entire app?
    public void handleCancel(View v) {
        finish();
    }

    public void handleSubmit(View v) {

        //handle and save into a csv file

        //long or int?
        int mTimeSec = (int) System.currentTimeMillis() / 1000;
//        String ts = tsLong.toString();
        writeCSV(mTimeSec);

        //need to exit the entire app
        finish();

        Intent exit_intent = new Intent(this,MainActivity.class);
        exit_intent.putExtra("exit", true);
        startActivity(exit_intent);
    }

    private void writeCSV(int time) {

        File stressData = new File(getFilesDir(),getString(R.string.photo_path));

        if (stressData.exists() == false) {
            try {
                stressData.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FileWriter mWriter = new FileWriter(stressData);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(",");
                mBuffWriter.write(mStressScore);
                mBuffWriter.write(".");
                mBuffWriter.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                FileWriter mWriter = new FileWriter(stressData,true);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(",");
                mBuffWriter.write(mStressScore);
                mBuffWriter.write("/");
                mBuffWriter.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

}