package com.geeksong.agricolascorer.mapper;

import java.util.List;

import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.PlayerStatistics;
import com.geeksong.agricolascorer.model.Score;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.content.ContentValues;
import android.database.Cursor;

public interface ScoreMapper {
	public ContentValues toDatabase(Score score, long gameId);
	public Score toScore(Cursor cursor, Player player);
	public List<Game> getGamesList(Database db);	
	public void deleteGame(Database db, Game game);
	public int getGameCount(Database db, int playerId);
	public List<PlayerStatistics> getPlayerStatistics(Database db, StatisticSearch search);
}
