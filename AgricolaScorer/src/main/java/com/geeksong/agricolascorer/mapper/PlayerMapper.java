package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.geeksong.agricolascorer.listadapter.AddPlayerAdapter;
import com.geeksong.agricolascorer.listadapter.SelectablePlayerAdapter;
import com.geeksong.agricolascorer.managers.GameTypeManager;
import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlayerMapper {
    private final Context context;
    private final Database db;
    
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
        		String.format(Locale.US, "SELECT " +
                            "(SELECT %s from %s as players WHERE players.%s='%s'), " +
                            "(SELECT COUNT(*) FROM %s as scores JOIN %s as players on players.id=scores.playerId WHERE players.%s='%s') + " +
                            "(SELECT COUNT(*) FROM %s as scores JOIN %s as players on players.id=scores.playerId WHERE players.%s='%s')",
                        Database.KEY_ID, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, name,
                        Database.TABLE_SCORES, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, name,
                        Database.TABLE_ALL_CREATURES_SCORES, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, name
                );
        SQLiteDatabase sqlDb = db.getReadableDatabase();
        Cursor playerCursor = sqlDb.rawQuery(selectQuery, null);
        
        Player addedPlayer = new Player(name);
        
        if(playerCursor.moveToNext()) {
        	addedPlayer.setId(playerCursor.getInt(0));
        	addedPlayer.setGameCount(playerCursor.getInt(1));
        }
        playerCursor.close();
        sqlDb.close();
        
        addPlayer(addedPlayer);
        
        return addedPlayer;
    }
    
    private void addPlayer(Player player) {
    	if(player.hasId())
    		updatePlayer(player);
    	else
    		insertPlayer(player);
    }
    
    public AddPlayerAdapter getTopPlayersListAdapter() {
        List<Player> playerList = getPlayers(true);
        return new AddPlayerAdapter(this.context, playerList);
    }
    
    public AddPlayerAdapter getPlayersListAdapter() {
        List<Player> playerList = getPlayers(true);
        return new AddPlayerAdapter(this.context, playerList);
    }
    
    public ArrayList<String> getPlayerNames() {
    	ArrayList<String> playerNames = new ArrayList<>();
    	for(Player player : getPlayers(false)) {
    		playerNames.add(player.getName());
    	}
    	return playerNames;
    }
    
    public SelectablePlayerAdapter getSelectablePlayersListAdapter() {
        List<Player> playerList = getPlayers(false);
        return new SelectablePlayerAdapter(this.context, playerList);
    }
    
    private void insertPlayer(Player player) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
     
        int id = (int) sqlDb.insert(Database.TABLE_RECENTPLAYERS, null, values);
        player.setId(id);
        sqlDb.close();
    }
    
    public void updatePlayer(Player player) {
    	if(playerExists(player.getName()))
    		return;
    	
        SQLiteDatabase sqlDb = db.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
     
        sqlDb.update(Database.TABLE_RECENTPLAYERS, values, Database.KEY_ID + " = ?", new String[] { String.valueOf(player.getId()) });
    }
    
    private List<Player> getPlayers(boolean requireGame) {
        SQLiteDatabase sqlDb = db.getReadableDatabase();
        
        String selectPlayers = String.format(Locale.US, "SELECT player.%s as _id, player.%s FROM %s as player",
        		Database.KEY_ID, Database.KEY_NAME, Database.TABLE_RECENTPLAYERS);
        Cursor playersCursor = sqlDb.rawQuery(selectPlayers, null);
        
        List<Player> playerList = new ArrayList<>();
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

        playersCursor.close();
        return playerList;
    }
    
    public boolean playerExists(String playerName) {
    	String selectQuery = String.format(Locale.US, "SELECT %s FROM %s WHERE %s='%s'", Database.KEY_ID, Database.TABLE_RECENTPLAYERS, Database.KEY_NAME, playerName);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        Cursor players = sqlDb.rawQuery(selectQuery, null);
        boolean playerExists = players.moveToNext();
        players.close();
        return playerExists;
    }
}
