package com.geeksong.agricolascorer;

import java.util.ArrayList;
import java.util.Dictionary;

import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class ScoreTabFactory implements TabHost.TabContentFactory {
	private ArrayList<Score> scores;
	private Activity tabHost;
	
	public ScoreTabFactory(Activity tabHost, ArrayList<Score> scores) {
		this.scores = scores;
		this.tabHost = tabHost;
	}
	
	public View createTabContent(String playerName) {
		View scorePlayer = tabHost.getLayoutInflater().inflate(R.layout.score_player, null);
		Score score = new Score();
		
		TextView totalScoreView = (TextView) scorePlayer.findViewById(R.id.score);
		ScoreManager manager = new ScoreManager(score, totalScoreView);
		
		addCheckedChangeListener(scorePlayer, manager, R.id.fields);
		addCheckedChangeListener(scorePlayer, manager, R.id.pastures);
		addCheckedChangeListener(scorePlayer, manager, R.id.grains);
		addCheckedChangeListener(scorePlayer, manager, R.id.vegetables);
		addCheckedChangeListener(scorePlayer, manager, R.id.sheep);
		addCheckedChangeListener(scorePlayer, manager, R.id.wild_boar);
		addCheckedChangeListener(scorePlayer, manager, R.id.cattle);

		addCheckedChangeListener(scorePlayer, manager, R.id.room_type);
		addCheckedChangeListener(scorePlayer, manager, R.id.family_members);
		
		addValueChangeListener(scorePlayer, manager, R.id.unused_spaces_picker);
		addValueChangeListener(scorePlayer, manager, R.id.fenced_stables_picker);
		addValueChangeListener(scorePlayer, manager, R.id.rooms_picker);
		addValueChangeListener(scorePlayer, manager, R.id.points_for_cards_picker);
		addValueChangeListener(scorePlayer, manager, R.id.bonus_points_picker);
		addValueChangeListener(scorePlayer, manager, R.id.begging_cards_picker);
		
		scores.add(score);
		
		return scorePlayer;
	}
	
	private void addCheckedChangeListener(View scorePlayer, ScoreManager manager, int id) {
		RadioGroup group = (RadioGroup) scorePlayer.findViewById(id);
		((RadioButton) group.getChildAt(0)).setChecked(true);
		group.setOnCheckedChangeListener(manager);
	}
	
	private void addValueChangeListener(View scorePlayer, ScoreManager manager, int id) {
		NumberPicker picker = (NumberPicker) scorePlayer.findViewById(id);
		picker.setMinValue(0);
		picker.setMaxValue(20);
		picker.setOnValueChangedListener(manager);
	}
}