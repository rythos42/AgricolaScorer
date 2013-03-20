package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.listadapter.AddPlayerAdapter;
import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;

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
        		String.format("SELECT player.%s, COUNT(score.%s) " +
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
    
    public SimpleCursorAdapter getTopPlayersListAdapter() {
    	if(topPlayersListAdapter != null)
    		return topPlayersListAdapter;
    	
        Cursor c = getPlayersCursor(5);

        String[] from = new String[] { Database.KEY_NAME, Database.KEY_GAMECOUNT };
        int[] to = new int[] { R.id.name, R.id.hintLabel };
        
        topPlayersListAdapter = new AddPlayerAdapter(this.context, R.layout.recent_player_list_item, c, from, to, 0);
        return topPlayersListAdapter;
    }
    
    public SimpleCursorAdapter getPlayersListAdapter() {
    	if(playersListAdapter != null)
    		return playersListAdapter;
    	
        Cursor c = getPlayersCursor();

        String[] from = new String[] { Database.KEY_NAME, Database.KEY_GAMECOUNT };
        int[] to = new int[] { R.id.name, R.id.hintLabel };
        
        playersListAdapter = new AddPlayerAdapter(this.context, R.layout.recent_player_list_item, c, from, to, 0);
        return playersListAdapter;
    }
    
    public ArrayList<String> getPlayerNames() {
    	ArrayList<String> playerNames = new ArrayList<String>();
    	Cursor c = getPlayersCursor();
    	
    	while(c.moveToNext()) {
    		String playerName = c.getString(c.getColumnIndex(Database.KEY_NAME));
    		playerNames.add(playerName);
    	}
    	return playerNames;    	
    }
    
    public SelectablePlayerAdapter getSelectablePlayersListAdapter() {
    	if(selectPlayersListAdapter != null)
    		return selectPlayersListAdapter;
    	
    	boolean requireGame = true;
        Cursor c = getPlayersCursor(requireGame);

        String[] from = new String[] { Database.KEY_NAME };
        int[] to = new int[] { R.id.name };

        selectPlayersListAdapter = new SelectablePlayerAdapter(this.context, R.layout.select_player_list_item, c, from, to, 0);
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
    
    private Cursor getPlayersCursor() {
    	return getPlayersCursor(-1, false);
    }
    
    private Cursor getPlayersCursor(int top) {
    	return getPlayersCursor(top, false);
    }
    
    private Cursor getPlayersCursor(boolean requireGame) {
    	return getPlayersCursor(-1, requireGame);
    }
            
    private Cursor getPlayersCursor(int top, boolean requireGame) {
    	String selectQuery;
    	if(requireGame) {
    		// Select from score, then player, to ensure no players without a game.
    		selectQuery = String.format("SELECT player.%s as _id, player.%s, COUNT(score.%s) as %s " +
    				"FROM %s as score " +
    				"JOIN %s as player on score.%s=player.%s " +
    				"GROUP BY score.%s ",
    				Database.KEY_ID, Database.KEY_NAME, Database.KEY_PLAYERID, Database.KEY_GAMECOUNT,
    				Database.TABLE_SCORES,
    				Database.TABLE_RECENTPLAYERS, Database.KEY_PLAYERID, Database.KEY_ID,
    				Database.KEY_PLAYERID);
    	}
    	else {
    		// Select from player, then score, to get all players.
            selectQuery = String.format("SELECT player.%s as _id, player.*, COUNT(score.%s) as %s " + 
    				"FROM %s as player " +
    				"LEFT JOIN %s as score on player.%s=score.%s " +
    				" GROUP BY score.%s ",
            		Database.KEY_ID, Database.KEY_PLAYERID, Database.KEY_GAMECOUNT,
            		Database.TABLE_RECENTPLAYERS,
            		Database.TABLE_SCORES, Database.KEY_ID, Database.KEY_PLAYERID,
            		Database.KEY_PLAYERID);
    	}
               
        if(top > 0)
        	selectQuery += " LIMIT " + top;
        
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        return sqlDb.rawQuery(selectQuery, null);
    }
    
    public boolean playerExists(String playerName) {
    	String selectQuery = String.format("SELECT %s FROM %s WHERE %s='%s'", Database.KEY_ID, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, playerName);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        Cursor players = sqlDb.rawQuery(selectQuery, null);
        
        return players.moveToNext();
    }
}
