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
	ContentValues toDatabase(Score score, long gameId);
	Score toScore(Cursor cursor, Player player);
	List<Game> getGamesList(Database db);
	void deleteGame(Database db, Game game);
	int getGameCount(Database db, int playerId);
	List<PlayerStatistics> getPlayerStatistics(Database db, StatisticSearch search);
}
