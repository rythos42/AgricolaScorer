package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.control.DialogResult;
import com.geeksong.agricolascorer.control.InputDialog;
import com.geeksong.agricolascorer.control.OnClickListener;
import com.geeksong.agricolascorer.listadapter.CurrentPlayersAdapter;
import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Player;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.TextView;

public class CreateGameActivity extends ListActivity {
    private final int MENU_REMOVE_FROM_GAME = 0;
    private final int MENU_RENAME_PLAYER = 1;

	private static final int ADD_PLAYER_REQUEST = 1;
	public static final String AddedPlayerBundleKey = "AddedPlayer";
	
	private CurrentPlayersAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        
        ActionBarHelper.setActionBarTitle(this, R.string.app_name);

        registerForContextMenu(getListView());
        
        adapter = new CurrentPlayersAdapter(this, GameCache.getInstance().getPlayerList());
        setListAdapter(adapter);
        GameCache.getInstance().setCurrentPlayersAdapter(adapter);  // see method comment for details

        checkButtonsVisibility();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == RESULT_OK) {
        	String playerName = data.getStringExtra(CreateGameActivity.AddedPlayerBundleKey);
        	if(!playerName.equals("") && !GameCache.getInstance().isPlayerInGame(playerName)) {
        		Player addedPlayer = PlayerMapper.getInstance().addPlayer(playerName);
        		GameCache.getInstance().addPlayer(addedPlayer);
             	adapter.notifyDataSetChanged();
             	
             	checkButtonsVisibility();
        	}
        }
    }
    
    private void checkButtonsVisibility() {
     	checkAddPlayerButtonVisibility();
     	checkScoreGameButtonVisibility();
    }
    
    private void checkAddPlayerButtonVisibility() {
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    	boolean limitNumberOfPlayers = sharedPref.getBoolean(SettingsActivity.Pref_LimitNumberOfPlayers, true);

		int MAX_PLAYERS = 5;
		boolean visible = !limitNumberOfPlayers || GameCache.getInstance().getPlayerList().size() < MAX_PLAYERS;
  		Button addPlayerButton = (Button) findViewById(R.id.addPlayerButton);
   		addPlayerButton.setVisibility(visible? View.VISIBLE : View.GONE);
    }
    
    private void checkScoreGameButtonVisibility() {
		int MIN_PLAYERS = 1;
		int visible = GameCache.getInstance().getPlayerList().size() >= MIN_PLAYERS ? View.VISIBLE : View.GONE;
    	
    	
  		Button scoreAgricola = (Button) findViewById(R.id.scoreAgricolaButton);
  		scoreAgricola.setVisibility(visible);
  		
  		Button scoreFarmers = (Button) findViewById(R.id.scoreFarmersButton);
  		scoreFarmers.setVisibility(visible);
  		
  		Button scoreAllCreatures = (Button) findViewById(R.id.scoreAllCreaturesButton);
  		scoreAllCreatures.setVisibility(visible);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	checkButtonsVisibility();
    }
    
    public void addPlayer(View source) {
    	Intent addPlayersIntent = new Intent(source.getContext(), AddPlayersActivity.class);
    	startActivityForResult(addPlayersIntent, ADD_PLAYER_REQUEST);
    }
    
    private void clearScoreIfChanging(GameType newGameType) {
    	GameCache game = GameCache.getInstance();
    	
    	if((newGameType == GameType.Agricola || newGameType == GameType.Farmers) && game.getGameType() == GameType.AllCreatures)
    		game.clearScores();
    	
    	if(newGameType == GameType.AllCreatures && (game.getGameType() == GameType.Agricola || game.getGameType() == GameType.Farmers))
    		game.clearScores();
    }
    
    public void scoreAgricola(View source) {
  		clearScoreIfChanging(GameType.Agricola);
  		GameCache.getInstance().setGameType(GameType.Agricola);
    	
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	startActivity(startGameIntent);
    }
    
    public void scoreFarmers(View source) {
  		clearScoreIfChanging(GameType.Farmers);
		GameCache.getInstance().setGameType(GameType.Farmers);
		
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	startActivity(startGameIntent);
    }
    
    public void scoreAllCreatures(View source) {
  		clearScoreIfChanging(GameType.AllCreatures);
    	GameCache.getInstance().setGameType(GameType.AllCreatures);
    	
    	Intent startGameIntent = new Intent(source.getContext(), ScorePlayersActivity.class);
    	startActivity(startGameIntent);
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
    			checkScoreGameButtonVisibility();
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
    	final Context thisContext = this;
    	
    	dialog.setOnClickListener(new OnClickListener() {
			public void onClick(DialogResult result, String newPlayerName) {
		    	if(result == DialogResult.OK && !newPlayerName.equals("")) {
		    		PlayerMapper mapper = PlayerMapper.getInstance();
		    		if(mapper.playerExists(newPlayerName)) {
						new AlertDialog.Builder(thisContext)
							.setTitle(R.string.cannot_rename)
							.setMessage(R.string.player_already_exists)
							.setPositiveButton(R.string.ok, null)
							.show();
						return;
		    		}

		    		Player renamedPlayer = GameCache.getInstance().getPlayer(playerPosition);
					renamedPlayer.setName(newPlayerName);
		    		mapper.updatePlayer(renamedPlayer);
	    			adapter.notifyDataSetChanged();
		    	}
			}
		});
    	
    	dialog.show();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_game, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.statistics:
    	    	Intent statisticsIntent = new Intent(this, StatisticsActivity.class);
    	    	startActivity(statisticsIntent);
    			break;
    		case R.id.history:
    	    	Intent historyIntent = new Intent(this, HistoryActivity.class);
    	    	startActivity(historyIntent);
    			break;
    		case R.id.about:
    			String aboutText = getResources().getString(R.string.about_text);
    			SpannableString string = new SpannableString(aboutText);
    			Linkify.addLinks(string, Linkify.ALL);
    			
    			AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle(R.string.about)
					.setMessage(string)
					.setPositiveButton(R.string.ok, null)
					.show();
    			
    			((TextView)dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    			break;
    		case R.id.randomize:
    			Intent randomizeIntent = new Intent(this, RandomizationActivity.class);
    			startActivity(randomizeIntent);
    			break;
    		case R.id.settings:
    			Intent settingsActivity = new Intent(this, SettingsActivity.class);
    			startActivity(settingsActivity);
    	}
        return true;
    }
}
