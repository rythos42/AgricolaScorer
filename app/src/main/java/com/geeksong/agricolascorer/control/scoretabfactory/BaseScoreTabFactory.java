package com.geeksong.agricolascorer.control.scoretabfactory;

import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import com.geeksong.agricolascorer.control.NumberPicker;
import com.geeksong.agricolascorer.control.OnValueChangeListener;
import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.managers.ScoreManager;
import com.geeksong.agricolascorer.model.Score;

abstract class BaseScoreTabFactory implements TabHost.TabContentFactory {
	void addValueChangeListener(View scorePlayer, final ScoreManager manager, int id) {
        final PickerUnitScoreView unitScoreView = (PickerUnitScoreView) scorePlayer.findViewById(id);
        NumberPicker picker = unitScoreView.getNumberPicker();

	    picker.setOnValueChangedListener(new OnValueChangeListener() {
	    	public void onValueChange(int newVal) {
            	manager.onPickerScoreChange(unitScoreView, newVal);
            	updateScoreDisplay(manager, unitScoreView);
	    	}
	    });

    	updateScoreDisplay(manager, unitScoreView);
	    
		Score score = manager.getScore();
		if(!score.isEmpty()) {
			int value = manager.getValueForNumberPicker(score, id);
			picker.setValue(value);
		} 
	}
	
	private void updateScoreDisplay(ScoreManager manager, PickerUnitScoreView unitScoreView) {
		try {
        	unitScoreView.updateScore(manager.getUnitScore(manager.getScore(), unitScoreView));
        } catch (Exception e) {
    		Log.e("AgricolaScorer", e.getMessage());
        }
	}
}
