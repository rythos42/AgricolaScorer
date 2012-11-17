package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import com.geeksong.agricolascorer.listadapter.GameHistoryAdapter;
import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.Score;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameHistoryMapper {    
    private Context context;
    private Database db;
 
    public GameHistoryMapper(Context context, Database db) {
    	this.context = context;
    	this.db = db;
    }
	
    public GameHistoryAdapter getListAdapter() {
        Cursor c = getGames();
        
        Hashtable<Integer, Game> games = new Hashtable<Integer, Game>();
        
        while(c.moveToNext()) {
        	int gameId = c.getInt(0);
        	
        	if(!games.containsKey(gameId))
        		games.put(gameId, new Game());
        	
        	Game game = games.get(gameId);
        	game.setDateAsTicks(c.getLong(1));
        	
        	Player player = new Player(c.getString(2));
        	Score score = new Score();
        	score.setPlayer(player);
        	score.setTotalScore(c.getInt(3));
        	game.addScore(score);
        }
        
        ArrayList<Game> gameList = Collections.list(games.elements());
        return new GameHistoryAdapter(this.context, gameList);
    }
    
    private Cursor getGames() {
    	String selectQuery = String.format("SELECT games.%s as _id, games.%s, players.%s, scores.%s " +
    			"FROM %s as scores " +
    			"JOIN %s as players on players.%s = scores.%s " +
    			"JOIN %s as games on games.%s = scores.%s", 
    			Database.KEY_ID, Database.KEY_DATE, Database.KEY_NAME, Database.KEY_FINALSCORE,
    			Database.TABLE_SCORES,
    			Database.TABLE_RECENTPLAYERS, Database.KEY_ID, Database.KEY_PLAYERID,
    			Database.TABLE_GAMES, Database.KEY_ID, Database.KEY_GAMEID);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        return sqlDb.rawQuery(selectQuery, null);
    }
}
