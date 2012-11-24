package com.geeksong.agricolascorer.model;

public class Player {
	private int id = -1;
	private String name;
	private int gameCount;
	private boolean isInGame;
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player(int id, String name, int gameCount) {
		this.id = id;
		this.name = name;
		this.gameCount = gameCount;
	}

	public boolean hasId() { return id != -1; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public int getGameCount() { return gameCount; }
	public void setGameCount(int gameCount) { this.gameCount = gameCount; }
	
	public boolean isInGame() { return isInGame; }
	public void setIsInGame(boolean isInGame) { this.isInGame = isInGame; }
}
