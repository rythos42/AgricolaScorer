package com.geeksong.agricolascorer;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class CreateGameActivity extends ListActivity {
	public static final int AddPlayerResultCode = 1;
	public static final String PlayerList = "playerList";
	
	ArrayList<String> playerList = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerList);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_game, menu);
        return true;
    }
    
    public void addPlayer(View source) {
    	Intent addPlayersIntent = new Intent(source.getContext(), AddPlayersActivity.class);
    	startActivityForResult(addPlayersIntent, AddPlayerResultCode);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == AddPlayerResultCode){
        	String playerName = (String) data.getExtras().get("playerName");
         	playerList.add(playerName);
         	adapter.notifyDataSetChanged();
        }
    }
    
    public void startGame(View source) {
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	
    	startGameIntent.putStringArrayListExtra(PlayerList, playerList);
    	startActivity(startGameIntent);
    }
}
