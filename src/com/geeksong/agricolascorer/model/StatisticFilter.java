package com.geeksong.agricolascorer.model;

import java.util.ArrayList;

public class StatisticFilter {
	private static StatisticFilter instance = new StatisticFilter();
	private StatisticFilter() {
	}
	public static StatisticFilter getInstance() {
		return instance;
	}	
	
	private static final int ALL_PLAYER_COUNT = -1;
	
	private int playerCount = ALL_PLAYER_COUNT;
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	public void setPlayerCount(int playerCount) { this.playerCount = playerCount; }
	public int getPlayerCount() { return this.playerCount; }
	public void clearPlayerCount() { this.playerCount = ALL_PLAYER_COUNT; }
	public boolean hasPlayerCount() { return this.playerCount != ALL_PLAYER_COUNT; }
	
	public ArrayList<String> getSelectedPlayers() { return playerNames; }
	public boolean hasSelectedPlayers() { return playerNames.size() != 0; }
	
	public void togglePlayer(String playerName) {
		if(playerNames.contains(playerName))
			playerNames.remove(playerName);
		else
			playerNames.add(playerName);
	}
	
}
