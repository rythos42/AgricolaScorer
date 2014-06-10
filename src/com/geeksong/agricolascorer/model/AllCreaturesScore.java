package com.geeksong.agricolascorer.model;

import java.io.Serializable;

import android.util.Log;

import com.geeksong.agricolascorer.managers.AllCreaturesScoreManager;

public class AllCreaturesScore implements Score, Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Player player;
	
	private int sheepScore;
	private int sheepBonusScore;
	private int wildBoarScore;
	private int wildBoarBonusScore;
	private int cattleScore;
	private int cattleBonusScore;
	private int horseScore;
	private int horseBonusScore;
	private int fullExpansionScore;
	private int buildingScore;
	
	private static int emptyScorePoints;
	
	static {
		AllCreaturesScore blankScore = new AllCreaturesScore(null);
		emptyScorePoints = blankScore.getTotalScore();
	}
	
	public AllCreaturesScore(Player player) {
		this.player = player;
		
		try {
			setSheepScore(AllCreaturesScoreManager.getScoreForSheep(0));
			setSheepBonusScore(AllCreaturesScoreManager.getBonusScoreForSheep(0));
			setWildBoarScore(AllCreaturesScoreManager.getScoreForWildBoar(0));
			setWildBoarBonusScore(AllCreaturesScoreManager.getBonusScoreForWildBoar(0));
			setCattleScore(AllCreaturesScoreManager.getScoreForCattle(0));
			setCattleBonusScore(AllCreaturesScoreManager.getBonusScoreForCattle(0));
			setHorseScore(AllCreaturesScoreManager.getScoreForHorse(0));
			setHorseBonusScore(AllCreaturesScoreManager.getBonusScoreForHorse(0));
			setFullExpansionScore(AllCreaturesScoreManager.getScoreForFullExpansion(0));
			setBuildingScore(AllCreaturesScoreManager.getScoreForBuildings(0));
		} catch(Exception e) {
    		Log.e("com.geeksong.agricolascorer", e.getMessage());
    	}
	}
	
	public int getSheepScore() {
		return sheepScore;
	}

	public void setSheepScore(int sheepScore) {
		this.sheepScore = sheepScore;
	}

	public int getSheepBonusScore() {
		return sheepBonusScore;
	}

	public void setSheepBonusScore(int sheepBonusScore) {
		this.sheepBonusScore = sheepBonusScore;
	}

	public int getWildBoarScore() {
		return wildBoarScore;
	}

	public void setWildBoarScore(int wildBoarScore) {
		this.wildBoarScore = wildBoarScore;
	}

	public int getWildBoarBonusScore() {
		return wildBoarBonusScore;
	}

	public void setWildBoarBonusScore(int wildBoarBonusScore) {
		this.wildBoarBonusScore = wildBoarBonusScore;
	}

	public int getCattleScore() {
		return cattleScore;
	}

	public void setCattleScore(int cattleScore) {
		this.cattleScore = cattleScore;
	}

	public int getCattleBonusScore() {
		return cattleBonusScore;
	}

	public void setCattleBonusScore(int cattleBonusScore) {
		this.cattleBonusScore = cattleBonusScore;
	}

	public int getHorseScore() {
		return horseScore;
	}

	public void setHorseScore(int horseScore) {
		this.horseScore = horseScore;
	}

	public int getHorseBonusScore() {
		return horseBonusScore;
	}

	public void setHorseBonusScore(int horseBonusScore) {
		this.horseBonusScore = horseBonusScore;
	}

	public int getFullExpansionScore() {
		return fullExpansionScore;
	}

	public void setFullExpansionScore(int fullExpansionScore) {
		this.fullExpansionScore = fullExpansionScore;
	}

	public int getBuildingScore() {
		return buildingScore;
	}

	public void setBuildingScore(int buildingScore) {
		this.buildingScore = buildingScore;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTotalScore() {
		return sheepScore + sheepBonusScore + wildBoarScore + wildBoarBonusScore + cattleScore + cattleBonusScore + 
				horseScore + horseBonusScore + fullExpansionScore + buildingScore;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean isOnlyTotalScore() {
		// This is left-over from Agricola where I was importing "total only" games from another app
		return false;
	}
	
	public boolean isEmpty() {
		return this.getTotalScore() == emptyScorePoints;
	}
}
