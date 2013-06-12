package com.geeksong.agricolascorer.managers;

import com.geeksong.agricolascorer.control.OnValueChangeListener;
import com.geeksong.agricolascorer.model.Score;

public abstract class ScoreManager implements OnValueChangeListener {
	private Score score;
	
	public ScoreManager(Score score) {
		this.score = score;
	}
	
	public Score getScore() {
		return this.score;
	}
	
	public abstract int getValueForNumberPicker(Score score, int id);
}
