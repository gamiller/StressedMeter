package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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
        mStressScore = mData.getIntExtra("stess_score", 0);
        mPicID = mData.getIntExtra("drawable_id", 0);

        ImageView image;
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(mPicID);

        //listener for cancel button
        mCancelButton = (Button) findViewById(R.id.cancel_btn_id);
        mSubmitButton = (Button) findViewById(R.id.submit_btn_id);

    }

    public void handleCancel() {
        finish();
    }

    public void handleSubmit() {

        //handle and save into a csv file

        //long or int?
        int mTimeSec = (int) System.currentTimeMillis() / 1000;
//        String ts = tsLong.toString();
        writeCSV(mTimeSec);


    }

    private void writeCSV(int time) {
        File stressData = new File(getString(R.string.csv_photo_path));

//                Environment.getExternalStorageDirectory().getAbsolutePath()+
//                File.separator + R.string.csv_photo_path);

        if (stressData.exists() == false) {
            try {
                stressData.mkdir();
                FileWriter mWriter = new FileWriter(stressData);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(",");
                mBuffWriter.write(mStressScore);
                mBuffWriter.write(",");
                mBuffWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
//                FileOutputStream writeStream = new FileOutputStream(stressData);
//                writeStream.write(time);
//                writeStream.write;
                FileWriter mWriter = new FileWriter(stressData);
                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
                mBuffWriter.write(time);
                mBuffWriter.write(",");
                mBuffWriter.write(mStressScore);
                mBuffWriter.write(";");
                mBuffWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//





//        if(file.exists()){
//            try {
//                FileWriter fileWriter  = new FileWriter(file);
//                BufferedWriter bfWriter = new BufferedWriter(fileWriter);
//                bfWriter.write("Text Data");
//                bfWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

}