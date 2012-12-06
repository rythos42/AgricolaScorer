package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.mapper.SavedGameMapper;
import com.geeksong.agricolascorer.model.StatisticFilter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterStatisticsActivity extends Activity implements OnItemSelectedListener, OnCheckedChangeListener {
	private SavedGameMapper mapper;
	private StatisticFilter filter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_statistics);
        
        ActionBarHelper.setActionBarTitle(this, R.string.filter);
        
        mapper = new SavedGameMapper();
        filter = StatisticFilter.getInstance();
        
        Spinner playerCountSpinner = (Spinner) findViewById(R.id.playerCount);
        ArrayAdapter<Integer> playerCountAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner, mapper.getAvailableSavedGameSizes());
        playerCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        playerCountSpinner.setAdapter(playerCountAdapter);
        playerCountSpinner.setOnItemSelectedListener(this);
        
        ListView playersListView = (ListView) findViewById(R.id.playerList);
        SelectablePlayerAdapter selectablePlayerAdapter = PlayerMapper.getInstance().getSelectablePlayersListAdapter();
        selectablePlayerAdapter.setOnCheckedChangeListener(this);
        playersListView.setAdapter(selectablePlayerAdapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_statistics, menu);
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

	public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
		TextView spinnerTextView = (TextView) view.findViewById(R.id.spinnerText);
		int playerCount = Integer.parseInt(spinnerTextView.getText().toString());
		filter.setPlayerCount(playerCount);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		filter.clearPlayerCount();	
	}

	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		CheckedTextView textView = (CheckedTextView) view;
		filter.togglePlayer(textView.getText().toString());
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		filter.togglePlayer(buttonView.getText().toString());
	}
}
