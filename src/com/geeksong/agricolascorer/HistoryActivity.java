package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.mapper.GameHistoryMapper;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ExpandableListView;

public class HistoryActivity extends Activity {
	private GameHistoryMapper historyAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        historyAdapter = new GameHistoryMapper(this);
        ExpandableListView list = (ExpandableListView) this.findViewById(R.id.historyList);
        list.setAdapter(historyAdapter.getListAdapter());
    }
}
