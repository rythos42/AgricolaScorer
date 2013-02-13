package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.control.NumberPicker;
import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class ScoreTabFactory implements TabHost.TabContentFactory {
	private Activity tabHost;
	
	public ScoreTabFactory(Activity tabHost) {
		this.tabHost = tabHost;
	}
	
	public View createTabContent(String playerName) {
		GameCache game = GameCache.getInstance();
		View scorePlayer = tabHost.getLayoutInflater().inflate(R.layout.score_player, null);
		Score score = game.getScoreByPlayerName(playerName);
		
		TextView totalScoreView = (TextView) scorePlayer.findViewById(R.id.score);
		ScoreManager manager = new ScoreManager(score, totalScoreView);
		
		addCheckedChangeListener(scorePlayer, score, manager, R.id.fields);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.pastures);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.grains);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.vegetables);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.sheep);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.wild_boar);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.cattle);

		addCheckedChangeListener(scorePlayer, score, manager, R.id.room_type);
		addCheckedChangeListener(scorePlayer, score, manager, R.id.family_members);
		
		addValueChangeListener(scorePlayer, score, manager, R.id.unused_spaces_picker);
		addValueChangeListener(scorePlayer, score, manager, R.id.fenced_stables_picker);
		addValueChangeListener(scorePlayer, score, manager, R.id.rooms_picker);
		addValueChangeListener(scorePlayer, score, manager, R.id.points_for_cards_picker);
		addValueChangeListener(scorePlayer, score, manager, R.id.bonus_points_picker);
		addValueChangeListener(scorePlayer, score, manager, R.id.begging_cards_picker);
		
		if(game.isFarmersOfTheMoor()) {
			LinearLayout farmersScoringPanel = (LinearLayout) scorePlayer.findViewById(R.id.farmersScoringPanel);
			farmersScoringPanel.setVisibility(View.VISIBLE);
			
			addValueChangeListener(scorePlayer, score, manager, R.id.horses_picker);
			addValueChangeListener(scorePlayer, score, manager, R.id.in_bed_family_picker);
		}
		
		return scorePlayer;
	}
	
	private void addCheckedChangeListener(View scorePlayer, Score score, ScoreManager manager, int id) {
		RadioGroup group = (RadioGroup) scorePlayer.findViewById(id);
		group.setOnCheckedChangeListener(manager);
		
		if(!score.isEmpty()) {
			int groupIndex = manager.getIndexForRadioButton(score, id);
			((RadioButton) group.getChildAt(groupIndex)).setChecked(true);
		} else {
			if(id == R.id.family_members)
				((RadioButton) group.getChildAt(1)).setChecked(true);
			else
				((RadioButton) group.getChildAt(0)).setChecked(true);
		}
	}
	
	private void addValueChangeListener(View scorePlayer, Score score, ScoreManager manager, int id) {
		NumberPicker picker = (NumberPicker) scorePlayer.findViewById(id);
		picker.setMinimum(0);
		picker.setOnValueChangedListener(manager);
		
		if(!score.isEmpty()) {
			int value = manager.getValueForNumberPicker(score, id);
			picker.setValue(value);
		} else {
			if(id == R.id.rooms_picker)
				picker.setValue(2);
		}
	}
}
