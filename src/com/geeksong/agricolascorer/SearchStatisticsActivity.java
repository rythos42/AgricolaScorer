package com.geeksong.agricolascorer;

import java.util.Calendar;
import java.util.Date;

import com.geeksong.agricolascorer.control.DatePickerFragment;
import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.mapper.StatisticsMapper;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class SearchStatisticsActivity extends Activity implements OnCheckedChangeListener {
	private StatisticSearch search;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_statistics);
        
        ActionBarHelper.setActionBarTitle(this, R.string.search);
        
        search = StatisticSearch.getInstance();
        
        StatisticsMapper mapper = StatisticsMapper.getInstance();
        setStartingStartDate(mapper);
        setStartingEndDate(mapper);
       
        ListView playersListView = (ListView) findViewById(R.id.playerList);
        SelectablePlayerAdapter selectablePlayerAdapter = PlayerMapper.getInstance().getSelectablePlayersListAdapter();
        selectablePlayerAdapter.setOnCheckedChangeListener(this);
        playersListView.setAdapter(selectablePlayerAdapter);
    }
    
    private void setStartingStartDate(StatisticsMapper mapper) {
        Date earliestGameDate = mapper.getEarliestGameDate(); 
        Date startDate = search.getStartDate();
        if(!startDate.equals(earliestGameDate)) {
	        if(!search.hasStartDate())
	        	search.setStartDate(earliestGameDate);
	        else
	        	putDateIntoButton(startDate, R.id.startDate);
        }
    }
    
    private void setStartingEndDate(StatisticsMapper mapper) {
        Date latestGameDate = mapper.getLatestGameDate();
        if(!search.getEndDate().equals(latestGameDate)) {
	        if(!search.hasEndDate()) 
	        	search.setEndDate(latestGameDate);
	        else
	        	putDateIntoButton(search.getEndDate(), R.id.endDate);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_statistics, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.ok:
    	    	Intent backToStatistics = new Intent();
    	    	setResult(RESULT_OK, backToStatistics);
    	    	finish();

    			break;
    	}
        return true;
    }

	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		CheckedTextView textView = (CheckedTextView) view;
		search.togglePlayer(textView.getText().toString());
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		search.togglePlayer(buttonView.getText().toString());
	}
	
	private Date putDateIntoButton(Date date, int buttonId) {
    	Calendar endDate = Calendar.getInstance();
    	endDate.setTime(date);
    	return putDateIntoButton(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH), buttonId);
	}
	
	private Date putDateIntoButton(int year, int month, int day, int buttonId) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day); 
		Date date = cal.getTime();
		
	    String formattedDate = DateFormat.getMediumDateFormat(this).format(date);
	    Button openStartDateDialogButton = (Button) findViewById(buttonId);
	    openStartDateDialogButton.setText(formattedDate);
	    
	    return date;
	}
	
	public void openStartDateDialog(View openStartDateButton) {
		DatePickerFragment startDatePickerFragment = new DatePickerFragment();
		startDatePickerFragment.setDefault(StatisticSearch.getInstance().getStartDate());
		startDatePickerFragment.setOnDateSetListener(new OnDateSetListener() {
			public void onDateSet(DatePicker datePicker, int year, int month, int day) { 
				Date startDate = putDateIntoButton(year, month, day, R.id.startDate); 
			    StatisticSearch.getInstance().setStartDate(startDate);
			}
		});
		startDatePickerFragment.show(getFragmentManager(), "startDatePicker");
	}
	
	public void openEndDateDialog(View view) {
		DatePickerFragment endDatePickerFragment = new DatePickerFragment();
		endDatePickerFragment.setDefault(StatisticSearch.getInstance().getEndDate());
		endDatePickerFragment.setOnDateSetListener(new OnDateSetListener() {
			public void onDateSet(DatePicker datePicker, int year, int month, int day) { 
				Date endDate = putDateIntoButton(year, month, day, R.id.endDate); 
			    StatisticSearch.getInstance().setEndDate(endDate);
			}
		});
		endDatePickerFragment.show(getFragmentManager(), "endDatePicker");
	}
	
	public void clearStartDate(View view) {
		Date earliestDate = StatisticsMapper.getInstance().getEarliestGameDate();
		StatisticSearch.getInstance().setStartDate(earliestDate);
	
	    Button openStartDateDialogButton = (Button) findViewById(R.id.startDate);
	    openStartDateDialogButton.setText(R.string.search_by_start_date);
	}
	
	public void clearEndDate(View view) {
		Date latestDate = StatisticsMapper.getInstance().getLatestGameDate();
		StatisticSearch.getInstance().setEndDate(latestDate);
		
	    Button openEndDateDialogButton = (Button) findViewById(R.id.endDate);
	    openEndDateDialogButton.setText(R.string.search_by_end_date);
	}
}
