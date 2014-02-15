package com.geeksong.agricolascorer.control.scoretabfactory;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.control.NumberPicker;
import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.control.SegmentedUnitScoreView;
import com.geeksong.agricolascorer.managers.AgricolaScoreManager;
import com.geeksong.agricolascorer.model.AgricolaScore;
import com.geeksong.agricolascorer.model.Score;

public class AgricolaScoreTabFactory extends BaseScoreTabFactory {
	private Activity tabHost;
	
	public AgricolaScoreTabFactory(Activity tabHost) {
		this.tabHost = tabHost;
	}
	
	public View createTabContent(String playerName) {
		GameCache game = GameCache.getInstance();
		View scorePlayer = tabHost.getLayoutInflater().inflate(R.layout.agricola_score_player, null);
		AgricolaScore score = (AgricolaScore) game.getScoreByPlayerName(playerName);
		
		TextView totalScoreView = (TextView) scorePlayer.findViewById(R.id.score);
		AgricolaScoreManager manager = new AgricolaScoreManager(score, totalScoreView);

		attachListeners(scorePlayer, manager);
		
		return scorePlayer;
	}
	
	public void attachListeners(View scorePlayer, AgricolaScoreManager manager) {
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
	}
	
	protected void addCheckedChangeListener(View scorePlayer, final AgricolaScoreManager manager, int id) {
		final SegmentedUnitScoreView unitScoreView = (SegmentedUnitScoreView) scorePlayer.findViewById(id);
		RadioGroup group = unitScoreView.getRadioGroup();
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                manager.onSegmentedScoreChange(unitScoreView, checkedId);
                try {
                    unitScoreView.updateScore(manager.getUnitScore(manager.getScore(), unitScoreView));
                } catch (Exception e) {
                    Log.e("com.geeksong.agricolascorer", e.getMessage());
                }
            }
        });
		
		AgricolaScore score = (AgricolaScore) manager.getScore();
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
	
	protected void addValueChangeListener(View scorePlayer, final AgricolaScoreManager manager, int id) {
		super.addValueChangeListener(scorePlayer, manager, id);

		final PickerUnitScoreView unitScoreView = (PickerUnitScoreView) scorePlayer.findViewById(id);
		NumberPicker picker = unitScoreView.getNumberPicker();

		Score score = manager.getScore();
		if(score.isEmpty()) {
			if(id == R.id.rooms_picker)
				picker.setValue(2);
		}
	}
}
