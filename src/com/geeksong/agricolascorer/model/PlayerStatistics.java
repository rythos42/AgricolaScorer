package com.geeksong.agricolascorer.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayerStatistics {
	private String name;
	private ArrayList<Double> dates = new ArrayList<Double>();
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	
	public PlayerStatistics(String name) {
		this.name = name;
	}
	
	public void addScore(long dateTicks, int score) {
		Date date = new Date(dateTicks);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		double less = calendar.getTimeInMillis() / 1000;
		
		dates.add(less);
		scores.add(score);
	}
	
	public ArrayList<Double> getDates() { return this.dates; }
	public ArrayList<Integer> getScores() { return this.scores; }
	
	public String getName() { return this.name; }
}
