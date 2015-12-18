package com.geeksong.agricolascorer.model;

import java.io.Serializable;

import com.geeksong.agricolascorer.managers.AgricolaScoreManager;

import android.util.Log;

public class AgricolaScore implements Score, Serializable {
	private static final long serialVersionUID = 1L;
	
	private int fieldScore;
	private int pastureScore;
	private int grainScore;
	private int vegetableScore;
	private int sheepScore;
	private int boarScore;
	private int cattleScore;
	private int unusedSpacesScore;
	private int fencedStablesScore;
	private int roomsScore;
	private int familyMemberScore;
	private int pointsForCards;
	private int bonusPoints;
	private int beggingCardsScore;
	private int horsesScore;

	private RoomType roomType;
	private int roomCount;
	private int inBedFamilyCount;
	private int totalFamilyCount = 2;
	
	private Player player;
		
	private int totalScore = -1;
	
	private int id;
	private boolean isTotalScoreProvided = false;
	
	private static final int emptyScorePoints;
	
	static {
		AgricolaScore blankScore = new AgricolaScore(null);
		emptyScorePoints = blankScore.getTotalScore();
	}
	
	public AgricolaScore(Player player) {
		this.player = player;
		
		try {
			setFieldScore(AgricolaScoreManager.getScoreForFields("0"));
			setPastureScore(AgricolaScoreManager.getScoreForPastures("0"));
			setGrainScore(AgricolaScoreManager.getScoreForGrains("0"));
			setVegetableScore(AgricolaScoreManager.getScoreForVegetables("0"));
			setSheepScore(AgricolaScoreManager.getScoreForSheep("0"));
			setBoarScore(AgricolaScoreManager.getScoreForWildBoar("0"));
			setCattleScore(AgricolaScoreManager.getScoreForCattle("0"));
			setRoomType(RoomType.Wood);
			setFamilyMemberScore(AgricolaScoreManager.getScoreForFamilyMembers(2, 0));
			setUnusedSpacesScore(AgricolaScoreManager.getScoreForUnusedSpaces(0));
			setFencedStablesScore(AgricolaScoreManager.getScoreForFencedStables(0));
			setRoomsScore(AgricolaScoreManager.getScoreForRooms(2, getRoomType()));
			setPointsForCards(AgricolaScoreManager.getScoreForPointsForCards(0));
			setBonusPoints(AgricolaScoreManager.getScoreForBonusPoints(0));
			setBeggingCardsScore(AgricolaScoreManager.getScoreForBeggingCards(0));
			setHorsesScore(AgricolaScoreManager.getScoreForHorses(0));
		} catch(Exception e) {
    		Log.e("AgricolaScorer", e.getMessage());
    	}
	}
	
	public void setId(int id) { this.id = id; }
	public int getId() { return this.id; }
	
	public void setFieldScore(int fieldScore) { this.fieldScore = fieldScore; }
	public int getFieldScore() { return this.fieldScore; }

	public void setPastureScore(int pastureScore) { this.pastureScore = pastureScore; }
	public int getPastureScore() { return this.pastureScore; }

	public void setGrainScore(int grainScore) { this.grainScore = grainScore; }
	public int getGrainScore() { return this.grainScore; }

	public void setVegetableScore(int vegetableScore) { this.vegetableScore = vegetableScore; }
	public int getVegetableScore() { return this.vegetableScore; }

	public void setSheepScore(int sheepScore) { this.sheepScore = sheepScore; }
	public int getSheepScore() { return this.sheepScore; }

	public void setBoarScore(int boarScore) { this.boarScore = boarScore; }
	public int getBoarScore() { return this.boarScore; }

	public void setCattleScore(int cattleScore) { this.cattleScore = cattleScore; }
	public int getCattleScore() { return this.cattleScore; }

	public void setUnusedSpacesScore(int unusedSpacesScore) { this.unusedSpacesScore = unusedSpacesScore; }
	public int getUnusedSpacesScore() { return this.unusedSpacesScore; }

	public void setFencedStablesScore(int fencedStablesScore) { this.fencedStablesScore = fencedStablesScore; }
	public int getFencedStablesScore() { return this.fencedStablesScore; }

	public void setRoomsScore(int roomsScore) { this.roomsScore = roomsScore; }
	public int getRoomsScore() { return this.roomsScore; }

	public void setFamilyMemberScore(int familyMemberScore) { this.familyMemberScore = familyMemberScore; }
	public int getFamilyMemberScore() { return this.familyMemberScore; }

	public void setPointsForCards(int pointsForCards) { this.pointsForCards = pointsForCards; }
	public int getPointsForCards() { return this.pointsForCards; }

	public void setBonusPoints(int bonusPoints) { this.bonusPoints = bonusPoints; }
	public int getBonusPoints() { return this.bonusPoints; }

	public void setBeggingCardsScore(int beggingCardsScore) { this.beggingCardsScore = beggingCardsScore; }
	public int getBeggingCardsScore() { return this.beggingCardsScore; }
	
	public void setHorsesScore(int horsesScore) { this.horsesScore = horsesScore; }
	public int getHorsesScore() { return this.horsesScore; }
	
	
	public void setInBedFamilyCount(int inBedFamilyCount) { this.inBedFamilyCount = inBedFamilyCount; }
	public int getInBedFamilyCount() { return this.inBedFamilyCount; }
	public int getInBedFamilyScore() { return this.inBedFamilyCount * -2; }
	
	public void setTotalFamilyCount(int totalFamilyCount) { this.totalFamilyCount = totalFamilyCount; }
	public int getTotalFamilyCount() { return this.totalFamilyCount; }
	public int getFamilyScoreWithoutInBedFamily() { return this.totalFamilyCount * 3; }
		
	public int getRoomCount() { return this.roomCount; }
	public void setRoomCount(int roomCount) { this.roomCount = roomCount; }
	
	public RoomType getRoomType() { return this.roomType; }
	public void setRoomType(RoomType roomType) { this.roomType = roomType; }
	
	public Player getPlayer() { return this.player; }
	public void setPlayer(Player player) { this.player = player; }

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
		this.isTotalScoreProvided = true;
	}
	
	private int getCalculatedTotalScore() {
		return this.fieldScore + this.pastureScore + this.grainScore + this.vegetableScore + this.sheepScore + this.boarScore + this.cattleScore
				+ this.roomsScore + this.familyMemberScore + this.unusedSpacesScore + this.fencedStablesScore + this.pointsForCards 
				+ this.bonusPoints + this.beggingCardsScore + this.horsesScore;
	}
	
	public int getTotalScore() {
		if(this.isTotalScoreProvided)
			return this.totalScore;
		
		return getCalculatedTotalScore();
	}
	
	public boolean isEmpty() {
		return this.getTotalScore() == emptyScorePoints;
	}
	
	public boolean isOnlyTotalScore() {
		if(!this.isTotalScoreProvided)
			return false;
		
		int calculatedTotalScore = getCalculatedTotalScore();
		return calculatedTotalScore == emptyScorePoints || calculatedTotalScore == 0;
	}
}
