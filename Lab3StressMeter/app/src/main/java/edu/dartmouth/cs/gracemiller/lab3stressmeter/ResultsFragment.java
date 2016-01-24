package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {

//    List<AxisValue> values = new Arraylist<AxisValue>();
    Map<Integer,Integer> stressMap = new HashMap<Integer,Integer>();


    private OnFragmentInteractionListener mListener;

    public ResultsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(getActivity(), LineChartView.class);
//        startActivity(intent);

        LoadData();

//
//        LineChartView chart = new LineChartView(8);
//        LineChartData data = chart.getLineChartData();
//        chart.setLineChartData();
//        data.

//        Axis xaxis = new Axis();

//        List<AxisValue> axisValues = new ArrayList<AxisValue>();
//        axisValues.add(new AxisValue(0, "some textt".toCharArray()));
//        Axis xaxis = new Axis(axisValues);
//        data.setAxisXBottom(xaxis);


//
//        List<AxisValue> mXValues = xaxis.getValues();
//        AxisValue newAxisValue = new AxisValue();
//        newAxisValue = (AxisValue) 2;
////        mXValues.add(newAxisValue);
//
//
////        mXValues.add()
//        xaxis.setValues(mXValues);

//        Axis xaxis = new Axis();

//        data.setAxisXBottom(axisX);

//        ChartData.setAxisXBottom(Axis axisX);
//        ColumnChartData.setStacked(boolean isStacked);
//        Line.setStrokeWidth(int strokeWidthDp);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void LoadData() {

        File mReadFile = new File(getActivity().getFilesDir(),getString(R.string.photo_path));

        if (mReadFile.exists()) {

            BufferedReader buffRead = null;

            try {
                buffRead = new BufferedReader(new FileReader(mReadFile));
                int mCurInt = 0;
                int flag = 0;
                int mTimeKey = 0;
                int mStressValue = 0;


                while ((mCurInt = buffRead.read()) != -1) {

                    if (mCurInt != 44 && mCurInt != 59) {
                        if (flag == 0) {
                            //assign time
                            mTimeKey = mCurInt;
//                            Log.d("REsults", "time " + mTimeKey);
                        } else {
                            //assign stress score
                            mStressValue = mCurInt;
//                            Log.d("Results", "stress " + mStressValue);
                            //adding values to a map for plotting
                            stressMap.put(mTimeKey,mStressValue);
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
