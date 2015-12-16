package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.StatisticSearch;

public abstract class BaseScoreMapper implements ScoreMapper {
    protected abstract Cursor getGamesCursor(Database db);
    protected abstract int getTotalScoreFromCursor(Cursor cursor);
    protected abstract Cursor getScoreCursor(SQLiteDatabase sqlDb, StatisticSearch search);
	
	public List<Game> getGamesList(Database db) {
        Cursor cursor = getGamesCursor(db);
        Hashtable<Integer, Game> games = new Hashtable<Integer, Game>();
        
        while(cursor.moveToNext()) {
        	int gameId = cursor.getInt(0);
        	
        	if(!games.containsKey(gameId))
        		games.put(gameId, new Game());
        	
        	Game game = games.get(gameId);
        	game.setId(gameId);
        	game.setDateAsTicks(cursor.getLong(1));
        	game.setGameType(GameType.values()[cursor.getInt(2)]);
        	
        	Player player = new Player(cursor.getString(3));
        	player.setId(cursor.getInt(4));

        	game.addScore(this.toScore(cursor, player));
        }
        
        return Collections.list(games.elements());
	}
	
	public List<PlayerStatistics> getPlayerStatistics(Database db, StatisticSearch search) {
    	SQLiteDatabase sqlDb = db.getReadableDatabase();
    	
    	Cursor scoreCursor = getScoreCursor(sqlDb, search);
    	
    	return getPlayerStatisticsFromCursor(scoreCursor);
	}
	
    private ArrayList<PlayerStatistics> getPlayerStatisticsFromCursor(Cursor scoreCursor) {
    	Hashtable<Integer, PlayerStatistics> players = new Hashtable<Integer, PlayerStatistics>();
    	
    	while(scoreCursor.moveToNext()) {
    		String playerName = scoreCursor.getString(1);
    		int playerId = scoreCursor.getInt(2);
    		PlayerStatistics stats;
    		
    		if(!players.containsKey(playerId)) {
    			stats = new PlayerStatistics(playerName);
    			players.put(playerId, stats);
    		}
    		else {
    			 stats = players.get(playerId);
    		}
    			   		
    		long dateTicks = scoreCursor.getLong(0);
    		int score = getTotalScoreFromCursor(scoreCursor);
    		stats.addScore(dateTicks, score);
    	}
    	
    	return new ArrayList<PlayerStatistics>(players.values());
    }
    
    protected String getPlayerSearchSql(StatisticSearch search) {
    	String query = String.format(Locale.US, "WHERE player.%s in (", Database.KEY_NAME);
   		String prefix = "";
   		for(String playerName : search.getSelectedPlayers()) {
   			query += (prefix + "'" + playerName + "'");
   			prefix = ", ";    					
   		}
   		query += ") ";
   		return query;
    }
    
    protected String getDateSearchSql(StatisticSearch search) {
    	return String.format(Locale.US, " AND game.playedDate BETWEEN %d AND %d ", search.getStartDate().getTime(), search.getEndDate().getTime());
    }
    
    protected String getGameTypeSearchSql(StatisticSearch search) {
    	return String.format(Locale.US, " AND game.%s=%d ", Database.KEY_GAME_TYPE, search.getGameType().ordinal());
    }
}
