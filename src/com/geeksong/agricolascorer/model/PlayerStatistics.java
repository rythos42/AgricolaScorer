package com.geeksong.agricolascorer.model;

import java.util.ArrayList;
import java.util.Date;

public class PlayerStatistics {
	private String name;
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private ArrayList<Date> dates = new ArrayList<Date>();
	
	public PlayerStatistics(String name) {
		this.name = name;
	}
	
	public void addScore(long dateTicks, int score) {
		dates.add(new Date(dateTicks));
		scores.add(score);
	}
	
	public int getScoreCount() {
		return scores.size();
	}
	
	public Date getDate(int index) {
		return dates.get(index);
	}
	
	public int getScore(int index) {
		return scores.get(index);
	}
	
	public String getName() { return this.name; }
}
