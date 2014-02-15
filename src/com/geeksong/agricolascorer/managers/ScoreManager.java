package com.geeksong.agricolascorer.managers;

import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.control.SegmentedUnitScoreView;
import com.geeksong.agricolascorer.model.Score;

public abstract class ScoreManager {
	private Score score;
	
	public ScoreManager(Score score) {
		this.score = score;
	}
	
	public Score getScore() {
		return this.score;
	}
	
	public abstract int getValueForNumberPicker(Score score, int id);

    public abstract int getUnitScore(Score score, SegmentedUnitScoreView unitScoreView) throws Exception;

    public abstract int getUnitScore(Score score, PickerUnitScoreView unitScoreView) throws Exception;

    public abstract void onPickerScoreChange(PickerUnitScoreView unitScoreView, int newVal);
}
