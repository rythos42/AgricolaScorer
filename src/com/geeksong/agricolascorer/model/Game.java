package com.geeksong.agricolascorer.model;

import java.util.ArrayList;
import java.util.Date;

public class Game {
	private Date date;
	private ArrayList<Score> scores = new ArrayList<Score>();
	
	public void setDateAsTicks(long ticks) {
		date = new Date(ticks);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void addScore(Score score) {
		this.scores.add(score);
	}
	
	public Score getScore(int position) {
		return scores.get(position);
	}
	
	public int getScoreCount() {
		return scores.size();
	}
}
