package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.List;

import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecentPlayersMapper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AgricolaScorer";
 
    private static final String TABLE_RECENTPLAYERS = "RecentPlayers";
 
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    private static final String KEY_GAMECOUNT = "gameCount";
 
    public RecentPlayersMapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECENTPLAYERS_TABLE = "CREATE TABLE " + TABLE_RECENTPLAYERS + "(" +
        		KEY_ID + " INTEGER PRIMARY KEY," +
        		KEY_NAME + " TEXT" +
                KEY_GAMECOUNT + " INTEGER" +
				")";
        db.execSQL(CREATE_RECENTPLAYERS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// woah nelly.
    }
    
    public void addPlayer(Player player) {
    	if(player.hasId()) {
    		player.incrementGameCount();
    		updatePlayer(player);
    	} else {
    		insertPlayer(player);
    	} 	
    }
    
    private void insertPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_GAMECOUNT, player.getGameCount());
     
        db.insert(TABLE_RECENTPLAYERS, null, values);
        db.close();
    }
    
    private int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_GAMECOUNT, player.getGameCount());
     
        return db.update(TABLE_RECENTPLAYERS, values, KEY_ID + " = ?", new String[] { String.valueOf(player.getId()) });
    }
    
    public List<Player> getTopPlayers(int x) {
        List<Player> playerList = new ArrayList<Player>();

        String selectQuery = "SELECT  * FROM " + TABLE_RECENTPLAYERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
            	int id = Integer.parseInt(cursor.getString(0));
            	String name = cursor.getString(1);
            	int gameCount = cursor.getInt(2);

            	playerList.add(new Player(id, name, gameCount));
            } while (cursor.moveToNext());
        }
     
        return playerList;
    }
}
