package com.geeksong.agricolascorer.model;

public interface Score {
	public int getId();
	public void setId(int id);
	public int getTotalScore();
	public Player getPlayer();
	public boolean isOnlyTotalScore();
	public boolean isEmpty();
}
