package com.geeksong.agricolascorer.model;

import java.util.ArrayList;
import java.util.Date;

public class Game {
	private int id;
	private Date date;
	private ArrayList<Score> scores = new ArrayList<Score>();
	private boolean farmersOfTheMoor;
	
	public void setId(int id) { this.id = id; }
	public int getId() { return this.id; }
	
	public void setDateAsTicks(long ticks) {
		date = new Date(ticks);
	}
	
	public void setDate(Date date) {
		this.date = date;
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
	
	public boolean hasScoresWithOnlyTotalScore() {
		for(Score score : scores) {
			if(score.isOnlyTotalScore())
				return true;
		}
		return false;
	}
	
	public void setFarmersOfTheMoor(boolean farmersOfTheMoor) { this.farmersOfTheMoor = farmersOfTheMoor; }
	public boolean isFarmersOfTheMoor() { return this.farmersOfTheMoor; }
}
