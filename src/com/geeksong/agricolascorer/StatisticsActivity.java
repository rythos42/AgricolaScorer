package com.geeksong.agricolascorer;

import java.util.Arrays;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.geeksong.agricolascorer.mapper.StatisticsMapper;
import com.geeksong.agricolascorer.model.StatisticFilter;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatisticsActivity extends Activity {
	public static final int GET_STATISTIC_FILTER = 0;
	
	public static final String StatisticFilterRequest = "StatisticFilter";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        /*
        // initialize our XYPlot reference:
        XYPlot mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null);                                  // fill color (none)

        // add a new series' to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);

        // same as above:
        mySimpleXYPlot.addSeries(series2,
                new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), null));

        // reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);

        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.filter:
    	    	Intent filterIntent = new Intent(this, FilterStatisticsActivity.class);
    	    	startActivityForResult(filterIntent, GET_STATISTIC_FILTER);
    			break;
    	}
        return true;
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
    	super.onActivityResult(reqCode, resultCode, data);
    	
    	switch (reqCode) {
    		case GET_STATISTIC_FILTER:
    			new StatisticsMapper().getStatisticsForFilter(StatisticFilter.getInstance());
    			
    			// todo, do something with a return value!
    			break;    			
    	}
	}
}
