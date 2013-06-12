package com.geeksong.agricolascorer.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.geeksong.agricolascorer.listadapter.GameHistoryAdapter;
import com.geeksong.agricolascorer.managers.GameTypeManager;
import com.geeksong.agricolascorer.model.Game;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GameHistoryMapper {    
    private Context context;
    private Database db;
 
    public GameHistoryMapper(Context context) {
    	this.context = context;
    	this.db = Database.getInstance();
    }
	
    public GameHistoryAdapter getListAdapter() {
    	List<Game> gamesList = new ArrayList<Game>();
    	for(ScoreMapper mapper : GameTypeManager.getAllScoreMappers()) {
    		gamesList.addAll(mapper.getGamesList(db));
    	}
    	
        Collections.sort(gamesList, new Comparator<Game>() {
			public int compare(Game lhs, Game rhs) {
				return rhs.getDate().compareTo(lhs.getDate());
			}
        });
        return new GameHistoryAdapter(this.context, gamesList);
    }
    
    public void deleteGame(Game game) {
    	ScoreMapper mapper = GameTypeManager.createScoreMapper(game.getGameType());
    	mapper.deleteGame(db, game);
    }
    
    public void updateGameDate(Game game) {
    	SQLiteDatabase sqlDb = db.getWritableDatabase();
    	String gameId = Integer.toString(game.getId());
    	
    	ContentValues values = new ContentValues();
    	values.put(Database.KEY_DATE, game.getDate().getTime());
    	
    	sqlDb.update(Database.TABLE_GAMES, values, Database.KEY_ID + " = ?", new String[] { gameId });
    }
}
