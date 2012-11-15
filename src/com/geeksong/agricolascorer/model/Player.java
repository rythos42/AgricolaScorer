<<<<<<< HEAD
package com.geeksong.agricolascorer.model;

public class Player {
	private int id = -1;
	private String name;
	private int gameCount;
	
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
	public void incrementGameCount() { gameCount++; }
	public void setGameCount(int gameCount) { this.gameCount = gameCount; }
}
=======
package com.geeksong.agricolascorer.model;

public class Player {
	private int id = -1;
	private String name;
	private int gameCount;
	
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
	public void incrementGameCount() { gameCount++; }
}
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
