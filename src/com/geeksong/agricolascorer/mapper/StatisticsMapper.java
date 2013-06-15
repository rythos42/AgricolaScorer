package com.geeksong.agricolascorer.mapper;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geeksong.agricolascorer.managers.GameTypeManager;
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
    	
    	String query = String.format(Locale.US, sql, Database.KEY_DATE, Database.TABLE_GAMES);
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
    
    public List<PlayerStatistics> getStatisticsForSearch(StatisticSearch search) {
    	ScoreMapper mapper = GameTypeManager.createScoreMapper(search.getGameType());
    	
    	return mapper.getPlayerStatistics(db, search);
    }
}
