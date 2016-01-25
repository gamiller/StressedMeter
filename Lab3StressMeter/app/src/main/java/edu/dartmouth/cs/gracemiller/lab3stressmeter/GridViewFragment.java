package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link GridViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridViewFragment extends Fragment {
    public int GRID_NUM = 1;
    ImageAdapter myAdapter;
    GridView gridview;

    public GridViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid_view, container, false);

        //generate random number in order to load random gridview
        Random rand = new Random();
        GRID_NUM = rand.nextInt((3 - 1) + 1) + 1;

        //load the photo gridview implementing PSM
        gridview = (GridView) view.findViewById(R.id.gridview);
        myAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(myAdapter);

        //when a photo is clicked
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //stop the nosie and vibrations
                AlarmClass.mediaPlayer.stop();
                AlarmClass.vibrator.cancel();

                //get the stress score for that position
                int stressScore = getStressScore(position);

                //pass an intent with the photo id to PhotoActivity class to load
                //the cancel/submit view
                Intent myIntent = new Intent(getActivity(), PhotoActivity.class);
                myIntent.putExtra("drawable_id", PSM.getGridById(GRID_NUM)[position]);
                myIntent.putExtra("stress_score",stressScore);

                startActivityForResult(myIntent, 0);
            }
        });

        //button to get new photos in the gridview
        Button nextPhotobtn = (Button) view.findViewById(R.id.moreimage_button);
        nextPhotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate the next grid number
                if (GRID_NUM == 3) {
                    GRID_NUM = 1;
                } else {
                    GRID_NUM++;
                }

                gridview.setAdapter(myAdapter);
            }
        });


        return view;
    }


    //ImageAdapter model based off of the android.com gridview instructions
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return PSM.getGrid1().length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }
        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                //create photos large enough for the space
                imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
                //if images need to be cropped, crop toward the center
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //put padding around the photos
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            //get the pictures for that gridview using PSM
            imageView.setImageResource(PSM.getGridById(GRID_NUM)[position]);
            return imageView;
        }

    }


    //look up table function for the stress score
    private int getStressScore(int position) {
        int value = 0;
        //the stress score correlates to the position of the photo
        if (position == 0) {
            value = 6;
        } else if (position == 1) {
            value = 8;
        } else if (position == 2) {
            value = 14;
        } else if (position == 3) {
            value = 16;
        } else if (position == 4) {
            value = 5;
        } else if (position == 5) {
            value = 7;
        } else if (position == 6) {
            value = 13;
        } else if (position == 7) {
            value = 15;
        } else if (position == 8) {
            value = 2;
        } else if (position == 9) {
            value = 4;
        } else if (position == 10) {
            value = 10;
        } else if (position == 11) {
            value = 12;
        } else if (position == 12) {
            value = 1;
        } else if (position == 13) {
            value = 3;
        } else if (position == 14) {
            value = 9;
        } else {
            value = 11;
        }

        return value;

    }



}
