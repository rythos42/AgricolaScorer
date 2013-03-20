package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.StatisticFilter;

public class StatisticsMapper {
	private Database db;
	
    public StatisticsMapper() {
    	this.db = Database.getInstance();
    }
    
    public ArrayList<PlayerStatistics> getStatisticsForFilter(StatisticFilter filter) {
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
   		for(String playerName : filter.getSelectedPlayers()) {
   			query += (prefix + "'" + playerName + "'");
   			prefix = ", ";    					
   		}
   		query += ")";
    	
    	Cursor scoreCursor = sqlDb.rawQuery(query, null);
    	
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
