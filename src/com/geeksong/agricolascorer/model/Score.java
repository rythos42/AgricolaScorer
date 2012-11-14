package com.geeksong.agricolascorer.model;

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
	}
}
