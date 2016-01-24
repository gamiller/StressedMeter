package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.app.Activity;
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

        if (stressData.exists() == false) {
            try {
                stressData.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            FileWriter mWriter = new FileWriter(stressData);
            BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
            mBuffWriter.write(time);
            mBuffWriter.write(",");
            mBuffWriter.write(mStressScore);
            mBuffWriter.write(",");
            mBuffWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        //            try {
////                FileOutputStream writeStream = new FileOutputStream(stressData);
////                writeStream.write(time);
////                writeStream.write;
//                FileWriter mWriter = new FileWriter(stressData);
//                BufferedWriter mBuffWriter = new BufferedWriter(mWriter);
//                mBuffWriter.write(time);
//                mBuffWriter.write(",");
//                mBuffWriter.write(mStressScore);
//                mBuffWriter.write(";");
//                mBuffWriter.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//

        File test_file = new File(getString(R.string.csv_photo_path));


        if (test_file.exists()) {

            BufferedReader buffRead = null;

            try {
                String fpath = getString(R.string.csv_photo_path);

                buffRead = new BufferedReader(new FileReader(test_file));
                int mCurInt = 0;
                int flag = 0;
                while ((mCurInt = buffRead.read()) != -1) {

                    if (mCurInt != 44 && mCurInt != 59) {
                        if (flag == 0) {
                            //assign time
                            Log.d("photoAct", "time");
                        } else {
                            //assign stress score
                            Log.d("photoAct", "stress");
                        }
                    } else if (mCurInt == 44) {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

}