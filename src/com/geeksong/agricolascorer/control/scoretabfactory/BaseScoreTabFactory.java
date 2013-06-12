package com.geeksong.agricolascorer.control.scoretabfactory;

import com.geeksong.agricolascorer.control.NumberPicker;
import com.geeksong.agricolascorer.managers.ScoreManager;
import com.geeksong.agricolascorer.model.Score;

import android.view.View;
import android.widget.TabHost;

public abstract class BaseScoreTabFactory implements TabHost.TabContentFactory {
	protected void addValueChangeListener(View scorePlayer, ScoreManager manager, int id) {
		NumberPicker picker = (NumberPicker) scorePlayer.findViewById(id);
		picker.setMinimum(0);
		picker.setOnValueChangedListener(manager);
		
		Score score = manager.getScore();
		if(!score.isEmpty()) {
			int value = manager.getValueForNumberPicker(score, id);
			picker.setValue(value);
		} 
	}	
}
