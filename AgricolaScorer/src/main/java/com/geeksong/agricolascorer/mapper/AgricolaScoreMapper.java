package com.geeksong.agricolascorer.mapper;

import java.util.Locale;

import com.geeksong.agricolascorer.model.AgricolaScore;
import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.RoomType;
import com.geeksong.agricolascorer.model.Score;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AgricolaScoreMapper extends BaseScoreMapper {
	public ContentValues toDatabase(Score baseScore, long gameId) {
        ContentValues scoreValues = new ContentValues();
        AgricolaScore score = (AgricolaScore) baseScore;
    	
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

	public Score toScore(Cursor cursor, Player player) {
    	AgricolaScore score = new AgricolaScore(player);
    	score.setId(cursor.getInt(6));
    	score.setFieldScore(cursor.getInt(7));
    	score.setPastureScore(cursor.getInt(8));
    	score.setGrainScore(cursor.getInt(9));
    	score.setVegetableScore(cursor.getInt(10));
    	score.setSheepScore(cursor.getInt(11));
    	score.setBoarScore(cursor.getInt(12));
    	score.setCattleScore(cursor.getInt(13));
    	score.setUnusedSpacesScore(cursor.getInt(14));
    	score.setFencedStablesScore(cursor.getInt(15));
    	score.setRoomsScore(cursor.getInt(16));
    	score.setFamilyMemberScore(cursor.getInt(17));
    	score.setPointsForCards(cursor.getInt(18));
    	score.setBonusPoints(cursor.getInt(19));
    	score.setBeggingCardsScore(cursor.getInt(20));
    	score.setRoomType(RoomType.values()[cursor.getInt(21)]);
    	score.setRoomCount(cursor.getInt(22));
    	score.setHorsesScore(cursor.getInt(23));
    	score.setInBedFamilyCount(cursor.getInt(24));
    	score.setTotalFamilyCount(cursor.getInt(25));
    	
    	if(score.getTotalScore() == 0)
    		score.setTotalScore(cursor.getInt(5));
    	
    	return score;
	}
	
	@Override
    protected Cursor getGamesCursor(Database db) {
    	String selectQuery = String.format(Locale.US, "SELECT games.%s as _id, games.%s, games.%s, players.%s, players.%s, " +
    			"scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, " +
    			"scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, " +
    			"scores.%s, scores.%s " +
    			"FROM %s as scores " +
    			"JOIN %s as players on players.%s = scores.%s " +
    			"JOIN %s as games on games.%s = scores.%s " +
    			"WHERE games.%s=%d or games.%s=%d or games.%s is null ",    			
    			Database.KEY_ID, Database.KEY_DATE, Database.KEY_GAME_TYPE, Database.KEY_NAME, Database.KEY_ID,
    			Database.KEY_FINALSCORE, Database.KEY_ID, Database.KEY_FIELDSCORE, Database.KEY_PASTURESCORE, Database.KEY_GRAINSCORE, Database.KEY_VEGETABLESCORE, 
					Database.KEY_SHEEPSCORE, Database.KEY_WILDBOARSCORE, Database.KEY_CATTLESCORE, Database.KEY_UNUSEDSPACESSCORE, 
    			Database.KEY_FENCEDSTABLESSCORE, Database.KEY_ROOMSSCORE, Database.KEY_FAMILYMEMBERSCORE, Database.KEY_POINTSFORCARDS, 
					Database.KEY_BONUSPOINTS, Database.KEY_BEGGINGCARDSSCORE, Database.KEY_ROOMTYPE, Database.KEY_ROOMCOUNT, Database.KEY_HORSESCORE,
    			Database.KEY_INBEDFAMILYCOUNT, Database.KEY_TOTALFAMILYCOUNT,
    			Database.TABLE_SCORES,
    			Database.TABLE_RECENTPLAYERS, Database.KEY_ID, Database.KEY_PLAYERID,
    			Database.TABLE_GAMES, Database.KEY_ID, Database.KEY_GAMEID,
    			Database.KEY_GAME_TYPE, GameType.Agricola.ordinal(), Database.KEY_GAME_TYPE, GameType.Farmers.ordinal(), Database.KEY_GAME_TYPE);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        return sqlDb.rawQuery(selectQuery, null);
    }

	public void deleteGame(Database db, Game game) {
    	SQLiteDatabase sqlDb = db.getWritableDatabase();
    	String gameId = Integer.toString(game.getId());
    	
    	sqlDb.delete(Database.TABLE_SCORES, Database.KEY_GAMEID + " = ?", new String[] { gameId });    	
    	sqlDb.delete(Database.TABLE_GAMES, Database.KEY_ID + " = ?", new String[] { gameId });
	}

	public int getGameCount(Database db, int playerId) {
    	SQLiteDatabase sqlDb = db.getReadableDatabase();
		String selectGames = String.format(Locale.US, "SELECT count(*) as number " +
				"FROM %s as scores " +
				"JOIN %s as players on players.%s = scores.%s " +
				"WHERE players.%s = %d " +
				"GROUP BY players.%s ",
				Database.TABLE_SCORES,
				Database.TABLE_RECENTPLAYERS, Database.KEY_ID, Database.KEY_PLAYERID,
				Database.KEY_ID, playerId,
				Database.KEY_ID);
		Cursor gamesCursor = sqlDb.rawQuery(selectGames, null);
		
		boolean hasGame = gamesCursor.moveToNext();
		return hasGame ? gamesCursor.getInt(0) : 0;
	}
	
	@Override
	protected Cursor getScoreCursor(SQLiteDatabase sqlDb, StatisticSearch search) {
    	String query = String.format(Locale.US, "SELECT game.%s, player.%s, player.%s, score.%s " +
    			"FROM %s as score " +
    			"JOIN %s as game on score.%s=game.%s " +
    			"JOIN %s as player on score.%s=player.%s ",
    			Database.KEY_DATE, Database.KEY_NAME, Database.KEY_ID, Database.KEY_FINALSCORE,
    			Database.TABLE_SCORES,
    			Database.TABLE_GAMES, Database.KEY_GAMEID, Database.KEY_ID,
    			Database.TABLE_RECENTPLAYERS, Database.KEY_PLAYERID, Database.KEY_ID);
    	    	
    	query += getPlayerSearchSql(search);
   		query += getDateSearchSql(search);
   		query += getGameTypeSearchSql(search);
    	
    	return sqlDb.rawQuery(query, null);
    }
	
	@Override
	protected int getTotalScoreFromCursor(Cursor scoreCursor) {
		return scoreCursor.getInt(3);
	}
}
