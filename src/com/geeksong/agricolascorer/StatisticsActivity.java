package com.geeksong.agricolascorer;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.geeksong.agricolascorer.mapper.StatisticsMapper;
import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticsActivity extends Activity {
	public static final int GET_STATISTIC_SEARCH = 0;

	private int[] Colours = { Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.WHITE };
	
    private GraphicalView chart;
    private XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.search:
    	    	Intent searchIntent = new Intent(this, SearchStatisticsActivity.class);
    	    	startActivityForResult(searchIntent, GET_STATISTIC_SEARCH);
    			break;
    	}
        return true;
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
    	super.onActivityResult(reqCode, resultCode, data);
    	
    	switch (reqCode) {
    		case GET_STATISTIC_SEARCH:
    			clearData();    			
    			addData();
    			chart.repaint();
    			break;
    	}
    }
    
    private void clearData() {
		int size = dataSet.getSeriesCount();
		for (int i = 0; i < size ; i++) {
			dataSet.removeSeries(0);
		}
		renderer.removeAllRenderers();
    }
    
    private void createRenderers(int count) {
    	for(int i = 0; i < count; i++) {
    		XYSeriesRenderer renderer = new XYSeriesRenderer();
    		renderer.setColor(Colours[i]);
    		renderer.setPointStyle(PointStyle.CIRCLE);
    		this.renderer.addSeriesRenderer(renderer);
    	}
    }
    
    private void setVisibility(int playerCount) {
        TextView noDataLabel = (TextView) this.findViewById(R.id.noDataLabel);
        noDataLabel.setVisibility(playerCount == 0 ? View.VISIBLE : View.GONE);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        layout.setVisibility(playerCount == 0 ? View.GONE : View.VISIBLE);
    }

    private void addData() {
		ArrayList<PlayerStatistics> statsList = StatisticsMapper.getInstance().getStatisticsForSearch(StatisticSearch.getInstance());
        int playerCount = statsList.size();
        if(playerCount == 0)
        	return;
        
        setVisibility(playerCount);
        createRenderers(playerCount);
        
        for(int i = 0; i < playerCount; i++) {
        	PlayerStatistics stat = statsList.get(i);
        	TimeSeries series = new TimeSeries(stat.getName());
        	
        	for(int j = 0; j < stat.getScoreCount(); j++) {
        		series.add(stat.getDate(j), stat.getScore(j));
        	}
        	
        	dataSet.addSeries(series);
		}  
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        ActionBarHelper.setActionBarTitle(this, R.string.graph_of_scores);
        
        renderer.setPointSize(5f);
    }

    protected void onResume() {
        super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (chart == null) {
            addData();
            chart = ChartFactory.getTimeChartView(this, dataSet, renderer, "MM/dd");
            layout.addView(chart);
        } else {
            chart.repaint();
        }
    }
}
