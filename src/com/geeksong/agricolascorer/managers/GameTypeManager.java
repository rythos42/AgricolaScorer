package com.geeksong.agricolascorer.managers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.TabHost;

import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.control.scoretabfactory.AgricolaScoreTabFactory;
import com.geeksong.agricolascorer.control.scoretabfactory.AllCreaturesScoreTabFactory;
import com.geeksong.agricolascorer.control.scoretabfactory.FarmersScoreTabFactory;
import com.geeksong.agricolascorer.mapper.AgricolaScoreMapper;
import com.geeksong.agricolascorer.mapper.AllCreaturesScoreMapper;
import com.geeksong.agricolascorer.mapper.Database;
import com.geeksong.agricolascorer.mapper.ScoreMapper;
import com.geeksong.agricolascorer.model.AgricolaScore;
import com.geeksong.agricolascorer.model.AllCreaturesScore;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.Score;

public class GameTypeManager {
	public static TabHost.TabContentFactory getTabContentFactory(Activity context) {
		switch(GameCache.getInstance().getGameType()) {
			case Agricola:
				return new AgricolaScoreTabFactory(context);
			case Farmers:
				return new FarmersScoreTabFactory(context);
			case AllCreatures:
				return new AllCreaturesScoreTabFactory(context);
		}
		
		return null;
	}
	
	public static Score createScore(Player player) {
		switch(GameCache.getInstance().getGameType()) {
			case Agricola:
				return new AgricolaScore(player);
			case Farmers:
				return new AgricolaScore(player);
			case AllCreatures:
				return new AllCreaturesScore(player);
		}
		return null;
	}
	
	public static ScoreMapper createScoreMapper() {
		return createScoreMapper(GameCache.getInstance().getGameType());
	}
	
	public static ScoreMapper createScoreMapper(GameType gameType) {
		switch(gameType) {
			case Agricola:
				return new AgricolaScoreMapper();
			case Farmers:
				return new AgricolaScoreMapper();
			case AllCreatures:
				return new AllCreaturesScoreMapper();
		}
		return null;
	}
	
	public static String getScoreTableName() {
		switch(GameCache.getInstance().getGameType()) {
			case Agricola:
				return Database.TABLE_SCORES;
			case Farmers:
				return Database.TABLE_SCORES;
			case AllCreatures:
				return Database.TABLE_ALL_CREATURES_SCORES;
		}
		return null;
	}
	
	public static List<ScoreMapper> getAllScoreMappers() {
		List<ScoreMapper> list = new ArrayList<ScoreMapper>();
		for(GameType gameType : GameType.values()) {
			// Ignore Farmers, as it is (currently) the same ScoreMapper as for Agricola.
			if(gameType == GameType.Farmers)
				continue;
			
			list.add(createScoreMapper(gameType));
		}
		
		return list;
	}
}
