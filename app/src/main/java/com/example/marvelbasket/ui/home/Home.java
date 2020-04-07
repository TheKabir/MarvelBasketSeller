package com.example.marvelbasket.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelbasket.Afterlogin;
import com.example.marvelbasket.MainActivity;
import com.example.marvelbasket.R;
import com.example.marvelbasket.bean.ProductCount;
import com.example.marvelbasket.bean.Seller;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private HomeViewModel mViewModel;
    private TextView tvElectonics, tvBook, tvGeneral, tvFashion;
    private PieChart pieChart;

    public static final String MY_PREF_NAME = "marvelbasketseller";

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_fragment, container, false);

        // Getting Attribues From XML
        tvElectonics = v.findViewById(R.id.electronics);
        tvFashion = v.findViewById(R.id.fashion);
        tvGeneral = v.findViewById(R.id.general);
        tvBook = v.findViewById(R.id.book);



        //PIECHART DISPLAY
        pieChart = v.findViewById(R.id.pc);
        pieChart.setUsePercentValues(true);

        List<PieEntry> value = new ArrayList<>();

        PieDataSet pieDataSet = new PieDataSet(value,"");
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);

        pieDataSet.setColors(new int[]{Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE});
        pieChart.setDescription(null);
        pieChart.setHoleRadius(35f);
        pieChart.setHoleColor(0);

        //Legend Edit
        Legend legend = pieChart.getLegend();
        legend.setTextSize(16f);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setTextColor(Color.WHITE);
        legend.setYOffset(legend.getYOffset() + 10);

        pieChart.setTransparentCircleRadius(25f);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(0f);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.animateXY(1400, 1400);
        pieChart.setNoDataTextColor(Color.WHITE);


//   BAR CHART DISPLAY
        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.8f;
        barSpace = 0f;
        groupSpace = 0.4f;
        int groupCount = 6;
        BarChart chart = (BarChart) v.findViewById(R.id.bc);
        ArrayList xVals = new ArrayList();

        xVals.add("Electronics");
        xVals.add("Books");
        xVals.add("General");
        xVals.add("Fashion");

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        BarDataSet set1, set2,set3;

        set1 = new BarDataSet(yVals1, "Approved");
        set1.setValueTextColor(Color.WHITE);
        set1.setValueTextSize(14f);
        set1.setColor(Color.GREEN);

        set2 = new BarDataSet(yVals2, "Stopped");
        set2.setValueTextColor(Color.WHITE);
        set2.setValueTextSize(14f);
        set2.setColor(Color.RED);

        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();

        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

//Y-axis
        chart.getAxisRight().setEnabled(false);
        chart.setTouchEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextSize(16f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setTextColor(Color.WHITE);
        l.setTextSize(16f);
        l.setYOffset(10f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }
}

