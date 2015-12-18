package com.geeksong.agricolascorer.control.scoretabfactory;

import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.managers.AllCreaturesScoreManager;
import com.geeksong.agricolascorer.model.AllCreaturesScore;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class AllCreaturesScoreTabFactory extends BaseScoreTabFactory {
	private final Activity tabHost;
	
	public AllCreaturesScoreTabFactory(Activity tabHost) {
		this.tabHost = tabHost;
	}
	
	public View createTabContent(String playerName) {
		GameCache game = GameCache.getInstance();
		View scorePlayer = tabHost.getLayoutInflater().inflate(R.layout.allcreatures_score_player, null);
		AllCreaturesScore score = (AllCreaturesScore) game.getScoreByPlayerName(playerName);
		
		TextView totalScoreView = (TextView) scorePlayer.findViewById(R.id.score);
		AllCreaturesScoreManager manager = new AllCreaturesScoreManager(score, totalScoreView);

		addValueChangeListener(scorePlayer, manager, R.id.sheep_picker);
		addValueChangeListener(scorePlayer, manager, R.id.wild_boar_picker);
		addValueChangeListener(scorePlayer, manager, R.id.cattle_picker);
		addValueChangeListener(scorePlayer, manager, R.id.horse_picker);
		addValueChangeListener(scorePlayer, manager, R.id.full_expansion_count_picker);
		addValueChangeListener(scorePlayer, manager, R.id.building_score_picker);
		
		return scorePlayer;
	}
}
