package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

    //private OnFragmentInteractionListener mListener;

    public GridViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GridViewFragment newInstance() {
        GridViewFragment fragment = new GridViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid_view, container, false);

            //super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);

            Random rand = new Random();
            GRID_NUM = rand.nextInt((3 - 1) + 1) + 1;

            GridView gridview = (GridView) view.findViewById(R.id.gridview);
            final ImageAdapter myAdapter = new ImageAdapter(getActivity());
            gridview.setAdapter(myAdapter);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    //Toast.makeText().show();

                    int stressScore = getStressScore(position);

                    Intent myIntent = new Intent(getActivity(), PhotoActivity.class);
                    myIntent.putExtra("drawable_id", PSM.getGridById(GRID_NUM)[position]);
                    myIntent.putExtra("stress_score",stressScore);
//                Bundle bundle = new Bundle();
//                bundle.putInt("imageid", PSM.getGridById(GRID_NUM)[position]);
//                myIntent.putExtras(bundle);
                    startActivityForResult(myIntent, 0);
                }
            });

//        Button nextPhotobtn = (Button) view.findViewById(R.id.moreimage_button);
//        nextPhotobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (GRID_NUM == 3) {
//                    GRID_NUM = 1;
//                } else {
//                    GRID_NUM++;
//                }
//                //setContentView(v);
//                GridView newgridview = (GridView) view.findViewById(R.id.gridview);
//                newgridview.setAdapter(myAdapter);
//
//                //return gridview;
//            }
//        });


        //setContentView(view);

//        GridViewFragment gridview = (GridViewFragment) view.findViewById(R.id.mygridview);
//        gridview.setAdapter(new ImageAdapter(this));

//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(HelloGridView.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
////
//

        //delete this is for compiling
        return view;
    }


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
                imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(PSM.getGridById(GRID_NUM)[position]);
            return imageView;
        }

//        // references to our images
//        private Integer[] mThumbIds = {
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7,
//                R.drawable.sample_0, R.drawable.sample_1,
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7,
//                R.drawable.sample_0, R.drawable.sample_1,
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7
//        };
    }


    //look up table function for the stress score
    private int getStressScore(int position) {
        int value = 0;
//        6,8,14,16

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
