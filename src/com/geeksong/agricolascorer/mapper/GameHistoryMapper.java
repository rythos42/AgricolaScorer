package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import com.geeksong.agricolascorer.listadapter.GameHistoryAdapter;
import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.RoomType;
import com.geeksong.agricolascorer.model.Score;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameHistoryMapper {    
    private Context context;
    private Database db;
 
    public GameHistoryMapper(Context context) {
    	this.context = context;
    	this.db = Database.getInstance();
    }
	
    public GameHistoryAdapter getListAdapter() {
        Cursor c = getGames();
        
        Hashtable<Integer, Game> games = new Hashtable<Integer, Game>();
        
        while(c.moveToNext()) {
        	int gameId = c.getInt(0);
        	
        	if(!games.containsKey(gameId))
        		games.put(gameId, new Game());
        	
        	Game game = games.get(gameId);
        	game.setId(gameId);
        	game.setDateAsTicks(c.getLong(1));
        	
        	Player player = new Player(c.getString(2));
        	Score score = new Score(player);
        	
        	score.setFieldScore(c.getInt(4));
        	score.setPastureScore(c.getInt(5));
        	score.setGrainScore(c.getInt(6));
        	score.setVegetableScore(c.getInt(7));
        	score.setSheepScore(c.getInt(8));
        	score.setBoarScore(c.getInt(9));
        	score.setCattleScore(c.getInt(10));
        	score.setUnusedSpacesScore(c.getInt(11));
        	score.setFencedStablesScore(c.getInt(12));
        	score.setRoomsScore(c.getInt(13));
        	score.setFamilyMemberScore(c.getInt(14));
        	score.setPointsForCards(c.getInt(15));
        	score.setBonusPoints(c.getInt(16));
        	score.setRoomType(RoomType.values()[c.getInt(17)]);
        	score.setRoomCount(c.getInt(18));
        	score.setIsFromDatabase(true);
        	
        	if(score.getTotalScore() == 0)
        		score.setTotalScore(c.getInt(3));
        	
        	game.addScore(score);
        }
        
        ArrayList<Game> gameList = Collections.list(games.elements());
        return new GameHistoryAdapter(this.context, gameList);
    }
    
    private Cursor getGames() {
    	String selectQuery = String.format("SELECT games.%s as _id, games.%s, players.%s, scores.%s, " +
    			"scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, " +
    			"scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s, scores.%s " +
    			"FROM %s as scores " +
    			"JOIN %s as players on players.%s = scores.%s " +
    			"JOIN %s as games on games.%s = scores.%s", 
    			Database.KEY_ID, Database.KEY_DATE, Database.KEY_NAME, Database.KEY_FINALSCORE,
    			Database.KEY_FIELDSCORE, Database.KEY_PASTURESCORE, Database.KEY_GRAINSCORE, Database.KEY_VEGETABLESCORE, Database.KEY_SHEEPSCORE, 
    				Database.KEY_WILDBOARSCORE, Database.KEY_CATTLESCORE, Database.KEY_UNUSEDSPACESSCORE, 
    			Database.KEY_FENCEDSTABLESSCORE, Database.KEY_ROOMSSCORE, Database.KEY_FAMILYMEMBERSCORE, Database.KEY_POINTSFORCARDS, 
    				Database.KEY_BONUSPOINTS, Database.KEY_BEGGINGCARDSSCORE, Database.KEY_ROOMTYPE, Database.KEY_ROOMCOUNT,
    			Database.TABLE_SCORES,
    			Database.TABLE_RECENTPLAYERS, Database.KEY_ID, Database.KEY_PLAYERID,
    			Database.TABLE_GAMES, Database.KEY_ID, Database.KEY_GAMEID);
        SQLiteDatabase sqlDb = db.getWritableDatabase();
        return sqlDb.rawQuery(selectQuery, null);
    }
    
    public void deleteGame(Game game) {
    	SQLiteDatabase sqlDb = db.getWritableDatabase();
    	String gameId = Integer.toString(game.getId());
    	
    	sqlDb.delete(Database.TABLE_SCORES, Database.KEY_GAMEID + " = ?", new String[] { gameId });    	
    	sqlDb.delete(Database.TABLE_GAMES, Database.KEY_ID + " = ?", new String[] { gameId });
    }
}
