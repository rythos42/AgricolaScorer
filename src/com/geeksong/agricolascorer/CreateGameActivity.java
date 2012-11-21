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
import android.widget.Button;
import android.widget.TextView;

public class CreateGameActivity extends ListActivity {
    private final int MENU_REMOVE_FROM_GAME = 0;
    private final int MENU_RENAME_PLAYER = 1;
    
    private final int MAX_PLAYERS = 5;
    private final int MIN_PLAYERS = 1;
	
	public static final int AddPlayerResultCode = 1;
	
	private CurrentPlayersAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(FontManager.getInstance().getDominican());
        
        registerForContextMenu(getListView());
        
        adapter = new CurrentPlayersAdapter(this, R.layout.current_players_list_item, GameCache.getInstance().getPlayerList());
        setListAdapter(adapter);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == AddPlayerResultCode) {
         	adapter.notifyDataSetChanged();
         	
         	checkAddPlayerButtonVisibility();
         	checkStartGameButtonVisibility();
        }
    }
    
    private void checkAddPlayerButtonVisibility() {
    	boolean visible = GameCache.getInstance().getPlayerList().size() < MAX_PLAYERS;
  		Button addPlayerButton = (Button) findViewById(R.id.addPlayerButton);
   		addPlayerButton.setVisibility(visible? View.VISIBLE : View.GONE);
    }
    
    private void checkStartGameButtonVisibility() {
    	boolean visible = GameCache.getInstance().getPlayerList().size() >= MIN_PLAYERS;
  		Button startGameButton = (Button) findViewById(R.id.startGameButton);
  		startGameButton.setVisibility(visible? View.VISIBLE : View.GONE);
    }
    
    public void addPlayer(View source) {
    	Intent addPlayersIntent = new Intent(source.getContext(), AddPlayersActivity.class);
    	startActivityForResult(addPlayersIntent, AddPlayerResultCode);
    }
    
    public void startGame(View source) {
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	startActivity(startGameIntent);
    }

    public void showHistory(View source) {
    	Intent historyIntent = new Intent(this, HistoryActivity.class);
    	startActivity(historyIntent);
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
    			checkAddPlayerButtonVisibility();
    			checkStartGameButtonVisibility();
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
