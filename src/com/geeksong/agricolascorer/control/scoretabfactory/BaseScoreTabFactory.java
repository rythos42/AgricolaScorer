package com.geeksong.agricolascorer.control.scoretabfactory;

import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import com.geeksong.agricolascorer.control.NumberPicker;
import com.geeksong.agricolascorer.control.OnValueChangeListener;
import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.managers.ScoreManager;
import com.geeksong.agricolascorer.model.Score;

public abstract class BaseScoreTabFactory implements TabHost.TabContentFactory {
	protected void addValueChangeListener(View scorePlayer, final ScoreManager manager, int id) {
        final PickerUnitScoreView unitScoreView = (PickerUnitScoreView) scorePlayer.findViewById(id);
        NumberPicker picker = unitScoreView.getNumberPicker();

		picker.setMinimum(0);
		
	    picker.setOnValueChangedListener(new OnValueChangeListener() {
	    	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
	    		try {
	            	manager.onPickerScoreChange(unitScoreView, newVal);
	            	unitScoreView.updateScore(manager.getUnitScore(manager.getScore(), unitScoreView));
	            } catch (Exception e) {
            		Log.e("com.geeksong.agricolascorer", e.getMessage());
	            }
	    	}
	    });

		Score score = manager.getScore();
		if(!score.isEmpty()) {
			int value = manager.getValueForNumberPicker(score, id);
			picker.setValue(value);
		} 
	}	
}
