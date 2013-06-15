package com.geeksong.agricolascorer.model;

import java.util.ArrayList;
import java.util.Date;

public class StatisticSearch {
	private static StatisticSearch instance = new StatisticSearch();
	private StatisticSearch() {
	}
	public static StatisticSearch getInstance() {
		return instance;
	}	
	
	private ArrayList<String> playerNames = new ArrayList<String>();
	private Date endDate = new Date(0);
	private Date startDate = new Date(0);
	private GameType gameType = GameType.Agricola;

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
	
	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; }
	public boolean hasEndDate() { return endDate.getTime() != 0L; }
	public void clearEndDate() { endDate = new Date(0L); }
	
	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate;	}
	public boolean hasStartDate() { return startDate.getTime() != 0L; }
	public void clearStartDate() { startDate = new Date(0L); }
	
	public GameType getGameType() { return gameType; }
	public void setGameType(GameType gameType) { this.gameType = gameType; }
}
