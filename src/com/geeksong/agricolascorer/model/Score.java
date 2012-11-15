package com.geeksong.agricolascorer.model;

<<<<<<< HEAD
public class Score {
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
	private RoomType roomType;
	private int familyMemberScore;
	private int pointsForCards;
	private int bonusPoints;
	private int beggingCardsScore;

	public void setFieldScore(int fieldScore) {
		this.fieldScore = fieldScore;
	}

	public void setPastureScore(int pastureScore) {
		this.pastureScore = pastureScore;
	}

	public void setGrainScore(int grainScore) {
		this.grainScore = grainScore;
	}

	public void setVegetableScore(int vegetableScore) {
		this.vegetableScore = vegetableScore;
	}

	public void setSheepScore(int sheepScore) {
		this.sheepScore = sheepScore;
	}

	public void setBoarScore(int boarScore) {
		this.boarScore = boarScore;
	}

	public void setCattleScore(int cattleScore) {
		this.cattleScore = cattleScore;
	}

	public void setUnusedSpacesScore(int unusedSpacesScore) {
		this.unusedSpacesScore = unusedSpacesScore;
	}

	public void setFencedStablesScore(int fencedStablesScore) {
		this.fencedStablesScore = fencedStablesScore;
	}

	public void setRoomsScore(int roomsScore) {
		this.roomsScore = roomsScore;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setFamilyMemberScore(int familyMemberScore) {
		this.familyMemberScore = familyMemberScore;
	}

	public void setPointsForCards(int pointsForCards) {
		this.pointsForCards = pointsForCards;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public void setBeggingCardsScore(int beggingCardsScore) {
		this.beggingCardsScore = beggingCardsScore;
	}

	public int getTotalScore() {
		return this.fieldScore;
=======
import com.geeksong.agricolascorer.RoomType;

public class Score {
	private int fieldCount;
	private int pastureCount;
	private int grainCount;
	private int vegetableCount;
	private int sheepCount;
	private int boarCount;
	private int cattleCount;
	private int unusedSpacesCount;
	private int fencedStablesCount;
	private int roomsCount;
	private RoomType roomType;
	private int familyMemberCount;
	private int pointsForCards;
	private int bonusPoints;
	private int beggingCardsCount;
		
	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public void setPastureCount(int pastureCount) {
		this.pastureCount = pastureCount;
	}

	public void setGrainCount(int grainCount) {
		this.grainCount = grainCount;
	}

	public void setVegetableCount(int vegetableCount) {
		this.vegetableCount = vegetableCount;
	}

	public void setSheepCount(int sheepCount) {
		this.sheepCount = sheepCount;
	}

	public void setBoarCount(int boarCount) {
		this.boarCount = boarCount;
	}

	public void setCattleCount(int cattleCount) {
		this.cattleCount = cattleCount;
	}

	public void setUnusedSpacesCount(int unusedSpacesCount) {
		this.unusedSpacesCount = unusedSpacesCount;
	}

	public void setFencedStablesCount(int fencedStablesCount) {
		this.fencedStablesCount = fencedStablesCount;
	}

	public void setRoomsCount(int roomsCount) {
		this.roomsCount = roomsCount;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setFamilyMemberCount(int familyMemberCount) {
		this.familyMemberCount = familyMemberCount;
	}

	public void setPointsForCards(int pointsForCards) {
		this.pointsForCards = pointsForCards;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public void setBeggingCardsCount(int beggingCardsCount) {
		this.beggingCardsCount = beggingCardsCount;
	}
	
	public int getTotalScore() {
		return 10;
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
	}
}
