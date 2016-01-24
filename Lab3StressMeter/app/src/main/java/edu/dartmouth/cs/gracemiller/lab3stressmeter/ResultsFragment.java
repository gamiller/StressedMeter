package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<tableData> mTableList = new ArrayList<tableData>();

    Map<Integer,Integer> mstressMap = new HashMap<Integer,Integer>();
    TableLayout mStressTable;

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
        View v = inflater.inflate(R.layout.fragment_results, container, false);

        LoadData();

        mStressTable = (TableLayout) v.findViewById(R.id.table_id);

        populateTable();


        return v;

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
                tableData entry = new tableData(0,0);
                char sepOne = 'a';
                char sepTwo = 'b';


                while ((mCurInt = buffRead.read()) != -1) {
                    if (mCurInt == (int) sepOne) {
                        Log.d("REsults", "true ");
                    }


                    if (mCurInt != 44 && mCurInt != 47) {
                        if (flag == 0) {
                            //assign time
                            mTimeKey = mCurInt;
                            Log.d("REsults", "time " + mTimeKey);

                        } else {
                            //assign stress score
                            mStressValue = mCurInt;

                            Log.d("Results", "stress " + mStressValue);
                            //adding values to a map for plotting
                            entry.setTime(mTimeKey);
                            entry.setStress(mStressValue);


                            mTableList.add(entry);
//                            mstressMap.put(mTimeKey,mStressValue);
                        }
                    } else if (mCurInt == 44) {
                        Log.d("comma","comma");
                        flag = 0;
                    } else {
                        Log.d("semi","semi");

                        flag = 1;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // need to iterate through the map and populate the table
    private void populateTable() {

        Log.d("size","" + mTableList.size());

        for (int i = 0; i< mTableList.size(); i++) {
            TableRow row = new TableRow(getActivity());
            row.setId(100+i);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView timeText = new TextView(getActivity());
            timeText.setId(200+i);
            TextView stressText = new TextView(getActivity());
            timeText.setId(300 + i);

            timeText.setText("" + mTableList.get(i).getTime());
            stressText.setText("" + mTableList.get(i).getStress());



            Log.d("here", "" + mTableList.get(i).getTime() + " " + mTableList.get(i).getStress());

            row.addView(timeText);
            row.addView(stressText);
            mStressTable.addView(row,i);

//            break;

        }

    }

    public class tableData {

        int mTime;
        int mStress;

        public tableData(int time, int stress){
        }

        public void setTime(int time) {
            mTime = time;
        }

        public void setStress(int stress) {
            mStress = stress;
        }

        public int getTime() {
            return mTime;
        }

        public int getStress() {
            return mStress;
        }

    }

}
