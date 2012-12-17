package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Date;

import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.Score;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SavedGameMapper {
	private Database db;
	
    public SavedGameMapper() {
    	this.db = Database.getInstance();
    }
    
    private ContentValues getScoreValues(Score score, long gameId) {
        ContentValues scoreValues = new ContentValues();
    	
        scoreValues.put(Database.KEY_FINALSCORE, score.getTotalScore());
        scoreValues.put(Database.KEY_FIELDSCORE, score.getFieldScore());
        scoreValues.put(Database.KEY_PASTURESCORE, score.getPastureScore());
        scoreValues.put(Database.KEY_GRAINSCORE, score.getGrainScore());
        scoreValues.put(Database.KEY_VEGETABLESCORE, score.getVegetableScore());
        scoreValues.put(Database.KEY_SHEEPSCORE, score.getSheepScore());
        scoreValues.put(Database.KEY_WILDBOARSCORE, score.getBoarScore());
        scoreValues.put(Database.KEY_CATTLESCORE, score.getCattleScore());
        scoreValues.put(Database.KEY_UNUSEDSPACESSCORE, score.getUnusedSpacesScore());
        scoreValues.put(Database.KEY_FENCEDSTABLESSCORE, score.getFencedStablesScore());
        scoreValues.put(Database.KEY_ROOMSSCORE, score.getRoomsScore());
        scoreValues.put(Database.KEY_FAMILYMEMBERSCORE, score.getFamilyMemberScore());
        scoreValues.put(Database.KEY_POINTSFORCARDS, score.getPointsForCards());
        scoreValues.put(Database.KEY_BONUSPOINTS, score.getBonusPoints());
        scoreValues.put(Database.KEY_BEGGINGCARDSSCORE, score.getBeggingCardsScore());
        scoreValues.put(Database.KEY_ROOMTYPE, score.getRoomType().ordinal());
        scoreValues.put(Database.KEY_ROOMCOUNT, score.getRoomCount());
        scoreValues.put(Database.KEY_PLAYERID, score.getPlayer().getId());
        scoreValues.put(Database.KEY_GAMEID, gameId);
        scoreValues.put(Database.KEY_HORSESCORE, score.getHorsesScore());
        scoreValues.put(Database.KEY_INBEDFAMILYCOUNT, score.getInBedFamilyCount());
        scoreValues.put(Database.KEY_TOTALFAMILYCOUNT, score.getTotalFamilyCount());
        
        return scoreValues;
    }
    
    public void save(ArrayList<Score> scores, boolean isFarmersOfTheMoor) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues gameValues = new ContentValues();
        gameValues.put(Database.KEY_DATE, new Date().getTime());
        gameValues.put(Database.KEY_FARMERS, isFarmersOfTheMoor ? 1: 0);
        long gameId = sqlDb.insert(Database.TABLE_GAMES, null, gameValues);

        for(Score score : scores) {
        	sqlDb.insert(Database.TABLE_SCORES, null, getScoreValues(score, gameId));
        }
        sqlDb.close();
    }
    
    public void save(Game game) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        int gameId = game.getId();
        ContentValues gameValues = new ContentValues();
        gameValues.put(Database.KEY_DATE, game.getDate().getTime());
        
        sqlDb.update(Database.TABLE_GAMES, gameValues, Database.KEY_ID + " = ?", new String[] { Integer.toString(gameId) });

        for(int i = 0; i < game.getScoreCount(); i++ ) {
        	Score score = game.getScore(i);
        	ContentValues scoreValues = getScoreValues(score, gameId);
        	sqlDb.update(Database.TABLE_SCORES, scoreValues, Database.KEY_ID + " = ?", new String[] { Integer.toString(score.getId()) });
        }
        sqlDb.close();
    }
    
    public ArrayList<Integer> getAvailableSavedGameSizes() {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        String query = String.format("SELECT DISTINCT COUNT(%s) as playerCount " +
        		"FROM %s as game " +
        		"JOIN %s as score on game.%s=score.%s " +
        		"GROUP BY score.%s " +
        		"ORDER BY playerCount",
        		Database.KEY_PLAYERID,
        		Database.TABLE_GAMES,
        		Database.TABLE_SCORES, Database.KEY_ID, Database.KEY_GAMEID,
        		Database.KEY_GAMEID);
        
        Cursor c = sqlDb.rawQuery(query, null);
        
        ArrayList<Integer> gameSizes = new ArrayList<Integer>();
        while(c.moveToNext())
        	gameSizes.add(c.getInt(0));
        
        return gameSizes;
    }
}
