<<<<<<< HEAD
package com.geeksong.agricolascorer.mapper;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class RecentPlayersMapper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "AgricolaScorer";
 
    private static final String TABLE_RECENTPLAYERS = "RecentPlayers";
 
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    private static final String KEY_GAMECOUNT = "gameCount";
    
    private SimpleCursorAdapter listAdapter;
    
    private Context context;
 
    public RecentPlayersMapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        this.context = context;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECENTPLAYERS_TABLE = "CREATE TABLE " + TABLE_RECENTPLAYERS + "(" +
        		KEY_ID + " INTEGER PRIMARY KEY," +
        		KEY_NAME + " TEXT, " +
                KEY_GAMECOUNT + " INTEGER" +
				")";
        db.execSQL(CREATE_RECENTPLAYERS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENTPLAYERS);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addPlayer(String name) {
        String selectQuery = "SELECT * FROM " + TABLE_RECENTPLAYERS + " WHERE " + KEY_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playerCursor = db.rawQuery(selectQuery, null);
        
        Player addedPlayer = new Player(name);
        
        if(playerCursor.moveToNext()) {
        	addedPlayer.setId(playerCursor.getInt(0));
        	addedPlayer.setGameCount(playerCursor.getInt(2));
        }
        
        addPlayer(addedPlayer);
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

        String[] from = new String[] { KEY_NAME };
        int[] to = new int[] { R.id.name };
        
        listAdapter = new SimpleCursorAdapter(this.context, R.layout.recent_player_list_item, c, from, to, 0);
        return listAdapter;
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
    
    private Cursor getTopPlayersCursor(int x) {
        String selectQuery = "SELECT id as _id, * FROM " + TABLE_RECENTPLAYERS + " LIMIT 5";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
=======
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
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
