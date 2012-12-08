package com.geeksong.agricolascorer;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.geeksong.agricolascorer.mapper.StatisticsMapper;
import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.StatisticFilter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

public class StatisticsActivity extends Activity {
	public static final int GET_STATISTIC_FILTER = 0;
	public static final String StatisticFilterRequest = "StatisticFilter";

    private int[] graphColours = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.WHITE};
    private int[] pointColours = {Color.rgb(139, 0, 0), Color.rgb(0, 0, 139), Color.rgb(0, 139, 0), Color.rgb(139, 0, 139), Color.rgb(220, 220, 220)};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        ActionBarHelper.setActionBarTitle(this, R.string.scores);

        XYPlot statsPlot = (XYPlot) findViewById(R.id.statsPlot);
        Resources res = getResources();
        statsPlot.setTitle(res.getString(R.string.scores));
        statsPlot.setDomainValueFormat(new PlotDateFormat());
        statsPlot.setDomainLabel(res.getString(R.string.date));
        statsPlot.setRangeLabel(res.getString(R.string.score));
        statsPlot.setRangeValueFormat(new DecimalFormat("0"));
        
        // reduce the number of range labels
        //mySimpleXYPlot.setTicksPerRangeLabel(3);

        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        statsPlot.disableAllMarkup();
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
    			ArrayList<PlayerStatistics> statsList = new StatisticsMapper().getStatisticsForFilter(StatisticFilter.getInstance());
    	        XYPlot statsPlot = (XYPlot) findViewById(R.id.statsPlot);
    	        statsPlot.clear();
    	        
    	        int playerCount = statsList.size();
    	        if(playerCount == 0)
    	        	return;
    	        
    	        //statsPlot.setDomainStep(XYStepMode.SUBDIVIDE, statsList.get(0).getDates().size());
    	        // BAD! Figure out what the values we have
    	        // this gets difficult, probably need to set particular days at the bottom fo the graph - specific values
    	        statsPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 5000);
    	        
    	        
    	        for(int i = 0; i < playerCount; i++ ) {
    	        	PlayerStatistics stat = statsList.get(i);
        	        XYSeries series = new SimpleXYSeries(stat.getDates(), stat.getScores(), stat.getName());
        	        
        	        statsPlot.addSeries(series, new LineAndPointFormatter(graphColours[i], pointColours[i], null));
    			}   			

    			break;    			
    	}
	}

    private class PlotDateFormat extends Format {
		private static final long serialVersionUID = 1L;
		
		private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd");
    	
    	@Override
    	public StringBuffer format(Object object, StringBuffer toAppendTo, FieldPosition pos) {
            Date date = new Date(((Number) object).longValue() * 1000);
            return dateFormat.format(date, toAppendTo, pos);
    	}

    	@Override
    	public Object parseObject(String string, ParsePosition position) {
    		return null;
    	}
    }
}
