package com.geeksong.agricolascorer.mapper;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import android.database.Cursor;

import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Player;

public abstract class BaseScoreMapper implements ScoreMapper {
    protected abstract Cursor getGamesCursor(Database db);
	
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
}
