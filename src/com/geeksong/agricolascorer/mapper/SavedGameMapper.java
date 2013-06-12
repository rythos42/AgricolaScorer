package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Date;

import com.geeksong.agricolascorer.managers.GameTypeManager;
import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Score;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class SavedGameMapper {
	private Database db;
	
    public SavedGameMapper() {
    	this.db = Database.getInstance();
    }
        
    public void save(ArrayList<Score> scores, GameType gameType) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues gameValues = new ContentValues();
        gameValues.put(Database.KEY_DATE, new Date().getTime());
        gameValues.put(Database.KEY_GAME_TYPE, gameType.ordinal());
        long gameId = sqlDb.insert(Database.TABLE_GAMES, null, gameValues);
        
        ScoreMapper scoreMapper = GameTypeManager.createScoreMapper();

        for(Score score : scores) {
        	sqlDb.insert(GameTypeManager.getScoreTableName(), null, scoreMapper.toDatabase(score, gameId));
        }
        sqlDb.close();
    }
    
    // Used to edit games
    public void save(Game game) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        int gameId = game.getId();
        ContentValues gameValues = new ContentValues();
        gameValues.put(Database.KEY_DATE, game.getDate().getTime());
        
        sqlDb.update(Database.TABLE_GAMES, gameValues, Database.KEY_ID + " = ?", new String[] { Integer.toString(gameId) });
        ScoreMapper scoreMapper = GameTypeManager.createScoreMapper();
        
        for(int i = 0; i < game.getScoreCount(); i++ ) {
        	Score score = game.getScore(i);
        	ContentValues scoreValues = scoreMapper.toDatabase(score, gameId);
        	sqlDb.update(GameTypeManager.getScoreTableName(), scoreValues, Database.KEY_ID + " = ?", new String[] { Integer.toString(score.getId()) });
        }
        sqlDb.close();
    }
}
