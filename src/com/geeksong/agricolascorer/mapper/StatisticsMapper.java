package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.StatisticSearch;

public class StatisticsMapper {
	private Database db;
	
	private static StatisticsMapper instance = new StatisticsMapper();
	public static StatisticsMapper getInstance() {
		return instance;
	}
	
    private StatisticsMapper() {
    	this.db = Database.getInstance();
    }
    
    private Date getDate(String sql) {
    	SQLiteDatabase sqlDb = db.getReadableDatabase();
    	
    	String query = String.format(sql, Database.KEY_DATE, Database.TABLE_GAMES);
    	Cursor dateCursor = sqlDb.rawQuery(query, null);
    	
    	Date date = new Date(0L);
    	if(dateCursor.moveToNext())
    		date = new Date(dateCursor.getLong(0));
    	
    	return date;
    }
    
    public Date getEarliestGameDate() {
    	return getDate("SELECT MIN(%s) FROM %s");
    }
    
    public Date getLatestGameDate() {
    	return getDate("SELECT MAX(%s) FROM %s");
    }
    
    public ArrayList<PlayerStatistics> getStatisticsForSearch(StatisticSearch search) {
    	SQLiteDatabase sqlDb = db.getReadableDatabase();
    	
    	String query = String.format("SELECT game.%s, score.%s, player.%s " +
    			"FROM %s as score " +
    			"JOIN %s as game on score.%s=game.%s " +
    			"JOIN %s as player on score.%s=player.%s ",
    			Database.KEY_DATE, Database.KEY_FINALSCORE, Database.KEY_NAME, 
    			Database.TABLE_SCORES,
    			Database.TABLE_GAMES, Database.KEY_GAMEID, Database.KEY_ID,
    			Database.TABLE_RECENTPLAYERS, Database.KEY_PLAYERID, Database.KEY_ID);
    	    	
   		query += String.format("WHERE player.%s in (", Database.KEY_NAME);
   		String prefix = "";
   		for(String playerName : search.getSelectedPlayers()) {
   			query += (prefix + "'" + playerName + "'");
   			prefix = ", ";    					
   		}
   		query += ")";
   		
   		query += String.format(" AND game.playedDate BETWEEN %d AND %d", search.getStartDate().getTime(), search.getEndDate().getTime());
    	
    	Cursor scoreCursor = sqlDb.rawQuery(query, null);
    	return getPlayerStatisticsFromCursor(scoreCursor);
    }
    
    private ArrayList<PlayerStatistics> getPlayerStatisticsFromCursor(Cursor scoreCursor) {
    	ArrayList<PlayerStatistics> playerScores = new ArrayList<PlayerStatistics>();
    	while(scoreCursor.moveToNext()) {
    		String name = scoreCursor.getString(2);
    		PlayerStatistics stats = getPlayerStats(playerScores, name);
    		
    		long dateTicks = scoreCursor.getLong(0);
    		int score = scoreCursor.getInt(1);
    		stats.addScore(dateTicks, score);
    	}
    	
    	return playerScores;
    }
    
    private PlayerStatistics getPlayerStats(ArrayList<PlayerStatistics> playerScores, String name) {
    	for(PlayerStatistics stats : playerScores) {
    		if(stats.getName().equals(name))
    			return stats;
    	}
    	
    	PlayerStatistics stats = new PlayerStatistics(name);
    	playerScores.add(stats);
    	return stats;
    }
}
