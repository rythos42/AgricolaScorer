package com.geeksong.agricolascorer.mapper;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

public class PlayerMapper {
    private SimpleCursorAdapter listAdapter;
    
    private Context context;
    private Database db;
 
    public PlayerMapper(Context context) {
    	this.context = context;
    	this.db = Database.getInstance();
    }
    
    public Player addPlayer(String name) {
        String selectQuery = "SELECT * FROM " + Database.TABLE_RECENTPLAYERS + " WHERE " + Database.KEY_NAME + " = '" + name + "'";
        SQLiteDatabase sqlDb = db.getReadableDatabase();
        Cursor playerCursor = sqlDb.rawQuery(selectQuery, null);
        
        Player addedPlayer = new Player(name);
        
        if(playerCursor.moveToNext()) {
        	addedPlayer.setId(playerCursor.getInt(0));
        	addedPlayer.setGameCount(playerCursor.getInt(2));
        }
        sqlDb.close();
        
        addPlayer(addedPlayer);
        
        return addedPlayer;
    }
    
    public void addPlayer(Player player) {
    	if(player.hasId()) {
    		player.incrementGameCount();
    		updatePlayer(player);
    	} else {
    		insertPlayer(player);
    	}
    	listAdapter.notifyDataSetChanged();
    }
    
    public SimpleCursorAdapter getListAdapter() {
    	if(listAdapter != null)
    		return listAdapter;
    	
        Cursor c = getTopPlayersCursor(5);

        String[] from = new String[] { Database.KEY_NAME };
        int[] to = new int[] { R.id.name };
        
        listAdapter = new SimpleCursorAdapter(this.context, R.layout.recent_player_list_item, c, from, to, 0);
        return listAdapter;
    }
    
    private void insertPlayer(Player player) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
        values.put(Database.KEY_GAMECOUNT, player.getGameCount());
     
        int id = (int) sqlDb.insert(Database.TABLE_RECENTPLAYERS, null, values);
        player.setId(id);
        sqlDb.close();
    }
    
    public int updatePlayer(Player player) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(Database.KEY_NAME, player.getName());
        values.put(Database.KEY_GAMECOUNT, player.getGameCount());
     
        return sqlDb.update(Database.TABLE_RECENTPLAYERS, values, Database.KEY_ID + " = ?", new String[] { String.valueOf(player.getId()) });
    }
    
    private Cursor getTopPlayersCursor(int x) {
        String selectQuery = "SELECT " + Database.KEY_ID + " as _id, * FROM " + Database.TABLE_RECENTPLAYERS + " LIMIT 5";
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        return sqlDb.rawQuery(selectQuery, null);
    }
}
