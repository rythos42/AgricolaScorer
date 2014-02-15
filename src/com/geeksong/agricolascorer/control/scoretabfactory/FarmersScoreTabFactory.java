package com.geeksong.agricolascorer.control.scoretabfactory;

import android.app.Activity;
import android.view.View;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.managers.AgricolaScoreManager;

public class FarmersScoreTabFactory extends AgricolaScoreTabFactory {
	public FarmersScoreTabFactory(Activity tabHost) {
		super(tabHost);
	}
	
	@Override
	public View createTabContent(String tag) {
		View scorePlayer = super.createTabContent(tag);
		View farmersScoringPanel = scorePlayer.findViewById(R.id.farmersScoringPanel);
		farmersScoringPanel.setVisibility(View.VISIBLE);
		
		return scorePlayer;
	}
	
	@Override
	public void attachListeners(View scorePlayer, AgricolaScoreManager manager) {
		super.attachListeners(scorePlayer, manager);
		
		addValueChangeListener(scorePlayer, manager, R.id.horses_picker);
		addValueChangeListener(scorePlayer, manager, R.id.in_bed_family_picker);
	}
}