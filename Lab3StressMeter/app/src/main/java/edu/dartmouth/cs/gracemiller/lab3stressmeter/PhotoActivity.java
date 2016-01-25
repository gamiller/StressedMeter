package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// handles the pop-up activity for collecting information
public class PhotoActivity extends Activity {

    int mStressScore, mPicID;
    Button mCancelButton, mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // receive data from main activity
        Intent mData = getIntent();
        mStressScore = mData.getIntExtra("stress_score", 0);
        mPicID = mData.getIntExtra("drawable_id", 0);

        // set up image view
        ImageView image;
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(mPicID);

        // grab buttons
        mCancelButton = (Button) findViewById(R.id.cancel_btn_id);
        mSubmitButton = (Button) findViewById(R.id.submit_btn_id);

    }

    // return to main activity when pressed
    public void handleCancel(View v) {
        finish();
    }

    // exit application when pressed
    public void handleSubmit(View v) {

        // grab current time
        int mTimeSec = (int) System.currentTimeMillis() / 1000;

        // write time and stress value to file
        writeFile(mTimeSec);

        //exit app
        Intent exit_intent = new Intent(this,MainActivity.class);
        exit_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        exit_intent.putExtra("exit", true);
        startActivity(exit_intent);
    }

    // write values to a file
    private void writeFile(int time) {

        // get file path
        File stressData = new File(getFilesDir(),getString(R.string.photo_path));

        // if file exists
        if (stressData.exists() == false) {
            try {
                stressData.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // try writing time and stress to a file
            try {
                FileWriter mWriter = new FileWriter(stressData);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(44);
                mBuffWriter.write(mStressScore);
                mBuffWriter.write(47);
                mBuffWriter.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

        } else {

            // if file exists append to file
            try {
                FileWriter mWriter = new FileWriter(stressData,true);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(44);
                mBuffWriter.write(mStressScore);
                mBuffWriter.write(47);
                mBuffWriter.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

}