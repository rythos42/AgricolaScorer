package com.geeksong.agricolascorer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date date;
	private ArrayList<Score> scores = new ArrayList<Score>();
	private GameType gameType;
	
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
	
	public GameType getGameType() { return gameType; }
	public void setGameType(GameType gameType) { this.gameType = gameType; }
}
