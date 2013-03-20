package com.geeksong.agricolascorer.model;

import java.util.ArrayList;

public class StatisticFilter {
	private static StatisticFilter instance = new StatisticFilter();
	private StatisticFilter() {
	}
	public static StatisticFilter getInstance() {
		return instance;
	}	
	
	private ArrayList<String> playerNames = new ArrayList<String>();

	public ArrayList<String> getSelectedPlayers() { return playerNames; }
	public boolean hasSelectedPlayers() { return playerNames.size() != 0; }
	
	public void togglePlayer(String playerName) {
		if(playerNames.contains(playerName))
			playerNames.remove(playerName);
		else
			playerNames.add(playerName);
	}
	
	public boolean isPlayerSelected(String playerName) {
		return playerNames.contains(playerName);
	}	
}
