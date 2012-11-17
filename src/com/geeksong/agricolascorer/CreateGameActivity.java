package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.control.DialogResult;
import com.geeksong.agricolascorer.control.InputDialog;
import com.geeksong.agricolascorer.control.OnClickListener;
import com.geeksong.agricolascorer.listadapter.CurrentPlayersAdapter;
import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.model.Player;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CreateGameActivity extends ListActivity {
    private final int MENU_REMOVE_FROM_GAME = 0;
    private final int MENU_RENAME_PLAYER = 1;
	
	public static final int AddPlayerResultCode = 1;
	
	private CurrentPlayersAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        
        registerForContextMenu(getListView());
        
        adapter = new CurrentPlayersAdapter(this, R.layout.current_players_list_item, GameCache.getInstance().getPlayerList());
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
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	menu.add(Menu.NONE, MENU_REMOVE_FROM_GAME, Menu.NONE, R.string.remove_from_game);
    	menu.add(Menu.NONE, MENU_RENAME_PLAYER, Menu.NONE, R.string.rename_player);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch(item.getItemId()) {
    		case MENU_REMOVE_FROM_GAME:
    			GameCache.getInstance().removePlayer((int) info.id);
    			adapter.notifyDataSetChanged();
    			return true;
    		case MENU_RENAME_PLAYER:
    			renamePlayer((int) info.id);
    			return true;
    		default:
    	    	return super.onContextItemSelected(item);
    	}
    }
    
    private void renamePlayer(int position) {
    	InputDialog dialog = new InputDialog(this);
    	
    	dialog.setTitle(R.string.rename);
    	dialog.setMessage(R.string.rename_player);
    	
    	final int playerPosition = position;
    	final ListActivity thisContext = this;
    	
    	dialog.setOnClickListener(new OnClickListener() {
			public void onClick(InputDialog dialog, DialogResult result, String input) {
		    	if(result == DialogResult.OK && !input.equals("")) {
					Player renamedPlayer = GameCache.getInstance().renamePlayer(playerPosition, input);
					new PlayerMapper(thisContext).updatePlayer(renamedPlayer);
	    			adapter.notifyDataSetChanged();
		    	}
			}
		});
    	
    	dialog.show();
    }
}
