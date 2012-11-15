package com.geeksong.agricolascorer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
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

	private RoomType roomType;
	private int roomCount;
	
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
	
	public int getRoomCount() {
		return this.roomCount;
	}
	
	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}
	
	public RoomType getRoomType() {
		return this.roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public int getTotalScore() {
		return this.fieldScore + this.pastureScore + this.grainScore + this.vegetableScore + this.sheepScore + this.boarScore + this.cattleScore
				+ this.roomsScore + this.familyMemberScore + this.unusedSpacesScore + this.fencedStablesScore + this.pointsForCards + this.bonusPoints + this.beggingCardsScore;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.fieldScore);
		out.writeInt(this.pastureScore);
		out.writeInt(this.grainScore);
		out.writeInt(this.vegetableScore);
		out.writeInt(this.sheepScore);
		out.writeInt(this.boarScore);
		out.writeInt(this.cattleScore);
		out.writeInt(this.roomsScore);
		out.writeInt(this.familyMemberScore);
		out.writeInt(this.unusedSpacesScore);
		out.writeInt(this.fencedStablesScore);
		out.writeInt(this.pointsForCards);
		out.writeInt(this.bonusPoints);
		out.writeInt(this.beggingCardsScore);
		out.writeInt(this.roomCount);
		out.writeValue(this.roomType);		
	}
	
	public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score> () {
		public Score createFromParcel(Parcel source) {
			Score score = new Score();
			
			score.setFieldScore(source.readInt());
			score.setPastureScore(source.readInt());
			score.setGrainScore(source.readInt());
			score.setVegetableScore(source.readInt());
			score.setSheepScore(source.readInt());
			score.setBoarScore(source.readInt());
			score.setCattleScore(source.readInt());
			score.setRoomsScore(source.readInt());
			score.setFamilyMemberScore(source.readInt());
			score.setUnusedSpacesScore(source.readInt());
			score.setFencedStablesScore(source.readInt());
			score.setPointsForCards(source.readInt());
			score.setBonusPoints(source.readInt());
			score.setBeggingCardsScore(source.readInt());
			
			score.setRoomCount(source.readInt());
			score.setRoomType((RoomType) source.readValue(ClassLoader.getSystemClassLoader()));
			
			return score;
		}

		public Score[] newArray(int size) {
			return new Score[size];
		}
	};
}
