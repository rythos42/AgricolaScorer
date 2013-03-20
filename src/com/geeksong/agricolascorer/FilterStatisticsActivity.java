package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.model.StatisticFilter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class FilterStatisticsActivity extends Activity implements OnCheckedChangeListener {
	private StatisticFilter filter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_statistics);
        
        ActionBarHelper.setActionBarTitle(this, R.string.filter);
        
        filter = StatisticFilter.getInstance();
       
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

	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		CheckedTextView textView = (CheckedTextView) view;
		filter.togglePlayer(textView.getText().toString());
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		filter.togglePlayer(buttonView.getText().toString());
	}
}
