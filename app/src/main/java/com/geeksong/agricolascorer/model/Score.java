package com.geeksong.agricolascorer.model;

public interface Score {
	int getId();
	void setId(int id);
	int getTotalScore();
	Player getPlayer();
	boolean isOnlyTotalScore();
	boolean isEmpty();
}
