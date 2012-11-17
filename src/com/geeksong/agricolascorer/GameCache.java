package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.Score;

public class GameCache {
	private static ArrayList<Score> scores = new ArrayList<Score>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void clearGame() {
		scores.clear();
		players.clear();
	}
	
	public static void addPlayer(Player player) {
		players.add(player);
	}
	
	public static void removePlayer(int index) {
		Player playerToRemove = players.get(index);
		
		Score scoreToRemove = null;
		for(Score score : scores) {
			if(score.getPlayer().getId() == playerToRemove.getId()) {
				scoreToRemove = score;
				break;
			}
		}
		if(scoreToRemove != null)
			scores.remove(scoreToRemove);
		
		players.remove(index);
	}
	
	public static Player renamePlayer(int index, String newName) {
		Player player = players.get(index);
		player.setName(newName);
		return player;
	}
	
	public static Player getPlayerByName(String playerName) {
		for(Player player : players) {
			if(player.getName().equals(playerName))
				return player;
		}
		return null;
	}
	
	public static ArrayList<Player> getPlayerList() { return players; }
	public static ArrayList<Score> getScoreList() { return scores; }
}
