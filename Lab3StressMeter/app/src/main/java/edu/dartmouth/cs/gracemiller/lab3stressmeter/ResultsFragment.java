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

import org.w3c.dom.Text;

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
import lecho.lib.hellocharts.model.PointValue;
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
            AlarmClass.vibrator.cancel();
            AlarmClass.mediaPlayer.stop();

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

        //load data from file into list
        LoadData();

        // extract table from view
        mStressTable = (TableLayout) v.findViewById(R.id.table_id);

        //create the graph
        createGraph(v);

        //create the table
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

    private void createGraph(View v) {
        LineChartView chart = (LineChartView) v.findViewById(R.id.chart_id);
        //        xaxis.;

        List<PointValue> lineValues = new ArrayList<PointValue>();

        for(int i = 0; i<mTableList.size(); i++) {
            lineValues.add(new PointValue(i, mTableList.get(i).getStress()));
        }

        Line trendLine = new Line(lineValues).setFilled(true).setColor(Color.BLUE).setCubic(true);

        List<Line> lineList = new ArrayList<Line>();
        lineList.add(trendLine);

        Axis xaxis = new Axis();
        xaxis.setName("Instances").setAutoGenerated(true);

        Axis yaxis = new Axis();
        yaxis.setName("Stress Level").setAutoGenerated(true);

        LineChartData chartData = new LineChartData();
        chartData.setLines(lineList);
        chartData.setAxisXBottom(xaxis);
        chartData.setAxisYLeft(yaxis);

        chart.setLineChartData(chartData);

    }

    private void LoadData() {

        File mReadFile = new File(getActivity().getFilesDir(),getString(R.string.photo_path));
//        mReadFile.delete();


        if (mReadFile.exists()) {

            BufferedReader buffRead = null;

            try {
                buffRead = new BufferedReader(new FileReader(mReadFile));
                int mCurInt = 0;
                int flag = 1;
                int mTimeKey = 0;
                int mStressValue = 0;


                while ((mCurInt = buffRead.read()) != -1) {

                    Log.d("reader","" + mCurInt);


                    if (mCurInt == 44) {
                        flag = 0;
                    } else if (mCurInt == 47) {
                        flag = 1;
                    } else {
                        if (flag == 1) {
                            //assign time
                            mTimeKey = mCurInt;
                            Log.d("REsults", "time " + mTimeKey);

                        } else {
                            //assign stress score
                            mStressValue = mCurInt;

                            Log.d("Results", "stress " + mStressValue);
                            //adding values to a map for plotting
                            tableData entry = new tableData(0,0);
                            entry.setTime(mTimeKey);
                            entry.setStress(mStressValue);


                            mTableList.add(entry);
//                            mstressMap.put(mTimeKey,mStressValue);
                        }
                    }
                }
                buffRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // need to iterate through the map and populate the table
    private void populateTable() {

        TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(0);
        tr_head.setBackgroundColor(Color.GRAY);
//        tr_head.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView timeHeader = new TextView(getActivity());
//        timeHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
        TextView stressHeader = new TextView(getActivity());
//        stressHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));

        timeHeader.setText("Time");
        stressHeader.setText("Stress");

        tr_head.addView(timeHeader);
        tr_head.addView(stressHeader);

        mStressTable.addView(tr_head,0);

        Log.d("size","" + mTableList.size());

        for (int i = 0; i < mTableList.size(); i++) {
            TableRow row = new TableRow(getActivity());
            row.setId(100 + i);
//            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView timeText = new TextView(getActivity());
//            timeText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
            timeText.setId(200 + i);

            TextView stressText = new TextView(getActivity());
//            stressText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
            timeText.setId(300 + i);


            timeText.setText("" + mTableList.get(i).getTime());
            stressText.setText("" + mTableList.get(i).getStress());


            Log.d("here", "" + mTableList.get(i).getTime() + " " + mTableList.get(i).getStress());
            Log.d("i", "i = " + i);

            row.addView(timeText);
            row.addView(stressText);
            mStressTable.addView(row,i+1);
//            new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


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
