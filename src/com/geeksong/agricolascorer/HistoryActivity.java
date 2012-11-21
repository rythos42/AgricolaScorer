package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.listadapter.GameHistoryAdapter;
import com.geeksong.agricolascorer.mapper.GameHistoryMapper;
import com.geeksong.agricolascorer.model.Game;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class HistoryActivity extends Activity {
	private static final int MENU_DELETE_GAME = 0;
	
	private GameHistoryMapper historyMapper;
	private GameHistoryAdapter historyAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        historyMapper = new GameHistoryMapper(this);
        historyAdapter = historyMapper.getListAdapter();
        
        checkNoHistoryLabelVisibility();

        ExpandableListView list = (ExpandableListView) this.findViewById(R.id.historyList);
        list.setAdapter(historyAdapter);
        
        registerForContextMenu(list);
    }
    
    private void checkNoHistoryLabelVisibility() {
    	if(!historyAdapter.isEmpty())
    		return;
    	
    	TextView noHistoryLabel = (TextView) this.findViewById(R.id.noHistoryLabel);
    	noHistoryLabel.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	menu.add(Menu.NONE, MENU_DELETE_GAME, Menu.NONE, R.string.delete_game);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
    	int position = (int) info.id;
    	Game game = (Game) historyAdapter.getGroup(position);
    	
    	switch(item.getItemId()) {
    		case MENU_DELETE_GAME:
    			historyMapper.deleteGame(game);
    			historyAdapter.deleteGame(game);
    			checkNoHistoryLabelVisibility();
    			return true;
    		default:
    	    	return super.onContextItemSelected(item);
    	}
    }
}
