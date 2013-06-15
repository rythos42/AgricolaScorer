package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.listadapter.AddPlayerAdapter;
import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.managers.GameTypeManager;
import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlayerMapper {
    private AddPlayerAdapter topPlayersListAdapter;
    private AddPlayerAdapter playersListAdapter;
    private SelectablePlayerAdapter selectPlayersListAdapter;
    
    private Context context;
    private Database db;
    
    private static PlayerMapper instance;
    public static void initialize(Context context) {
    	instance = new PlayerMapper(context);
    }
    
    public static PlayerMapper getInstance() {
    	return instance;
    }
 
    private PlayerMapper(Context context) {
    	this.context = context;
    	this.db = Database.getInstance();
    }
    
    public Player addPlayer(String name) {
        String selectQuery =
        		String.format(Locale.US, "SELECT player.%s, COUNT(score.%s) " +
        				"FROM %s as player " +
        				"LEFT JOIN %s as score on player.%s=score.%s " +
        				"WHERE player.%s='%s' " +
        				"GROUP BY score.%s",
        				Database.KEY_ID, Database.KEY_PLAYERID,
        				Database.TABLE_RECENTPLAYERS,
        				Database.TABLE_SCORES, Database.KEY_ID, Database.KEY_PLAYERID,
        				Database.KEY_NAME, name,
        				Database.KEY_PLAYERID);
        SQLiteDatabase sqlDb = db.getReadableDatabase();
        Cursor playerCursor = sqlDb.rawQuery(selectQuery, null);
        
        Player addedPlayer = new Player(name);
        
        if(playerCursor.moveToNext()) {
        	addedPlayer.setId(playerCursor.getInt(0));
        	addedPlayer.setGameCount(playerCursor.getInt(1));
        }
        sqlDb.close();
        
        addPlayer(addedPlayer);
        
        return addedPlayer;
    }
    
    public void addPlayer(Player player) {
    	if(player.hasId()) {
    		updatePlayer(player);
    	} else {
    		insertPlayer(player);
    		
    		if(playersListAdapter != null)
    			playersListAdapter.notifyDataSetChanged();
    	}
    	
    	if(topPlayersListAdapter != null)
    		topPlayersListAdapter.notifyDataSetChanged();
    	if(selectPlayersListAdapter != null)
    		selectPlayersListAdapter.notifyDataSetChanged();
    }
    
    public AddPlayerAdapter getTopPlayersListAdapter() {
    	if(topPlayersListAdapter != null)
    		return topPlayersListAdapter;
    	
        List<Player> playerList = getPlayers(true);
        topPlayersListAdapter = new AddPlayerAdapter(this.context, R.layout.recent_player_list_item, R.id.name, playerList);
        return topPlayersListAdapter;
    }
    
    public AddPlayerAdapter getPlayersListAdapter() {
    	if(playersListAdapter != null)
    		return playersListAdapter;
    	       
        List<Player> playerList = getPlayers(true);
        playersListAdapter = new AddPlayerAdapter(this.context, R.layout.recent_player_list_item, R.id.name, playerList);
        return playersListAdapter;
    }
    
    public ArrayList<String> getPlayerNames() {
    	ArrayList<String> playerNames = new ArrayList<String>();
    	for(Player player : getPlayers(false)) {
    		playerNames.add(player.getName());
    	}
    	return playerNames;    	
    }
    
    public SelectablePlayerAdapter getSelectablePlayersListAdapter() {
    	if(selectPlayersListAdapter != null)
    		return selectPlayersListAdapter;
    	
        List<Player> playerList = getPlayers(false);
        selectPlayersListAdapter = new SelectablePlayerAdapter(this.context, R.layout.select_player_list_item, R.id.playerName, playerList);
        return selectPlayersListAdapter;
    }
    
    private void insertPlayer(Player player) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
     
        int id = (int) sqlDb.insert(Database.TABLE_RECENTPLAYERS, null, values);
        player.setId(id);
        sqlDb.close();
    }
    
    public int updatePlayer(Player player) {
    	if(playerExists(player.getName()))
    		return -1;
    	
        SQLiteDatabase sqlDb = db.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
     
        return sqlDb.update(Database.TABLE_RECENTPLAYERS, values, Database.KEY_ID + " = ?", new String[] { String.valueOf(player.getId()) });
    }
    
    private List<Player> getPlayers(boolean requireGame) {
        SQLiteDatabase sqlDb = db.getReadableDatabase();
        
        String selectPlayers = String.format(Locale.US, "SELECT player.%s as _id, player.%s FROM %s as player",
        		Database.KEY_ID, Database.KEY_NAME, Database.TABLE_RECENTPLAYERS);
        Cursor playersCursor = sqlDb.rawQuery(selectPlayers, null);
        
        List<Player> playerList = new ArrayList<Player>();
        while(playersCursor.moveToNext()) {
			int playerId = playersCursor.getInt(0);
			int gameCount = 0;
			
			for(ScoreMapper mapper : GameTypeManager.getAllScoreMappers()) {
				gameCount += mapper.getGameCount(db, playerId);
			}

			if(requireGame && gameCount == 0)
				continue;

			Player player = new Player(playerId, playersCursor.getString(1), gameCount);
			playerList.add(player);
		}

        return playerList;
    }
    
    public boolean playerExists(String playerName) {
    	String selectQuery = String.format(Locale.US, "SELECT %s FROM %s WHERE %s='%s'", Database.KEY_ID, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, playerName);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        Cursor players = sqlDb.rawQuery(selectQuery, null);
        
        return players.moveToNext();
    }
}
