package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.listadapter.CurrentPlayersAdapter;
import com.geeksong.agricolascorer.mapper.Database;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CreateGameActivity extends ListActivity {
	public static final int AddPlayerResultCode = 1;
	public static final String PlayerList = "playerList";
	
	CurrentPlayersAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        
        Database.initialize(this);
        
        adapter = new CurrentPlayersAdapter(this, R.layout.current_players_list_item, GameCache.getPlayerList());
        setListAdapter(adapter);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == AddPlayerResultCode)
         	adapter.notifyDataSetChanged();
    }
    
    public void addPlayer(View source) {
    	Intent addPlayersIntent = new Intent(source.getContext(), AddPlayersActivity.class);
    	startActivityForResult(addPlayersIntent, AddPlayerResultCode);
    }
    
    public void startGame(View source) {
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	startActivity(startGameIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_game, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.historyButton:
    	    	Intent historyIntent = new Intent(this, HistoryActivity.class);
    	    	startActivity(historyIntent);
    			break;
    	}
    	return true;
    }
}
