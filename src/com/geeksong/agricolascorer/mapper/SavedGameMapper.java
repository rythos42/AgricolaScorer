package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Date;

import com.geeksong.agricolascorer.model.Score;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class SavedGameMapper {
	private Database db;
	
    public SavedGameMapper() {
    	this.db = Database.getInstance();
    }
    
    public void save(ArrayList<Score> scores) {
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        
        ContentValues gameValues = new ContentValues();
        gameValues.put(Database.KEY_DATE, new Date().getTime());
        long gameId = sqlDb.insert(Database.TABLE_GAMES, null, gameValues);

        for(Score score : scores) {
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
            
            sqlDb.insert(Database.TABLE_SCORES, null, scoreValues);
        }
        sqlDb.close();
    }
}
