package com.geeksong.agricolascorer.managers;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;
import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.control.SegmentedUnitScoreView;
import com.geeksong.agricolascorer.model.AgricolaScore;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.RoomType;
import com.geeksong.agricolascorer.model.Score;

public class AgricolaScoreManager extends ScoreManager {
	private final TextView totalScoreView;
	
	public AgricolaScoreManager(AgricolaScore score, TextView totalScoreView) {
		super(score);

		this.totalScoreView = totalScoreView;
		
		totalScoreView.setText(String.format("%d", score.getTotalScore()));
	}

    public void onSegmentedScoreChange(SegmentedUnitScoreView unitScoreView, int checkedId) {
    	try {
            RadioButton checked = (RadioButton) unitScoreView.findViewById(checkedId);
	    	CharSequence text = checked.getText();
	    	AgricolaScore score = (AgricolaScore) getScore();
	    	
	    	switch(unitScoreView.getId()) {
		    	case R.id.fields:
		    		score.setFieldScore(getScoreForFields(text));
		    		break;
		    	case R.id.pastures:
		    		score.setPastureScore(getScoreForPastures(text));
		    		break;
		    	case R.id.grains:
		    		score.setGrainScore(getScoreForGrains(text));
		    		break;
		    	case R.id.vegetables:
		    		score.setVegetableScore(getScoreForVegetables(text));
		    		break;
		    	case R.id.sheep:
		    		score.setSheepScore(getScoreForSheep(text));
		    		break;
		    	case R.id.wild_boar:
		    		score.setBoarScore(getScoreForWildBoar(text));
		    		break;
		    	case R.id.cattle:
		    		score.setCattleScore(getScoreForCattle(text));
		    		break;
		    	case R.id.room_type:
		    		RoomType type = RoomType.Wood;
		    		switch(checked.getId()) {
		    			case R.id.woodButton: type = RoomType.Wood; break;
		    			case R.id.clayButton: type = RoomType.Clay; break;
		    			case R.id.stoneButton: type = RoomType.Stone; break;
		    		}
		    		score.setRoomType(type);
		    		score.setRoomsScore(getScoreForRooms(score.getRoomCount(), score.getRoomType()));
		    		break;
		    	case R.id.family_members:
		    		score.setTotalFamilyCount(Integer.valueOf(text.toString()));
		    		score.setFamilyMemberScore(getScoreForFamilyMembers(score.getTotalFamilyCount(), score.getInBedFamilyCount()));
		    		break;
	    	}
			
			totalScoreView.setText(String.format("%d", score.getTotalScore()));
    	} catch(Exception e) {
    		Log.e("AgricolaScorer", e.getMessage());
    	}
    }
    
	public void onPickerScoreChange(PickerUnitScoreView unitScoreView, int newVal) {
		try {
	    	AgricolaScore score = (AgricolaScore) getScore();
			
	    	switch(unitScoreView.getId()) {
		    	case R.id.unused_spaces_picker:
		    		score.setUnusedSpacesScore(getScoreForUnusedSpaces(newVal));
		    		break;
		    	case R.id.fenced_stables_picker:
		    		score.setFencedStablesScore(getScoreForFencedStables(newVal));
		    		break;
		    	case R.id.rooms_picker:
		    		score.setRoomCount(newVal);
		    		score.setRoomsScore(getScoreForRooms(newVal, score.getRoomType()));
		    		break;
		    	case R.id.points_for_cards_picker:
		    		score.setPointsForCards(getScoreForPointsForCards(newVal));
		    		break;
		    	case R.id.bonus_points_picker:
		    		score.setBonusPoints(getScoreForBonusPoints(newVal));
		    		break;
		    	case R.id.begging_cards_picker:
		    		score.setBeggingCardsScore(getScoreForBeggingCards(newVal));
		    		break;
		    	case R.id.horses_picker:
		    		score.setHorsesScore(getScoreForHorses(newVal));
		    		break;
		    	case R.id.in_bed_family_picker:
		    		score.setInBedFamilyCount(newVal);
		    		score.setFamilyMemberScore(getScoreForFamilyMembers(score.getTotalFamilyCount(), score.getInBedFamilyCount()));
	    	}
			totalScoreView.setText(String.format("%d", score.getTotalScore()));
    	} catch(Exception e) {
    		Log.e("AgricolaScorer", e.getMessage());
    	}
	}
	
	private int includeNegativeOne(int score) {
		if(score == -1)
			return 0;
		return score;
	}
	
	public int getIndexForRadioButton(AgricolaScore score, int id) {
    	switch(id) {
	    	case R.id.fields: return includeNegativeOne(score.getFieldScore());
	    	case R.id.pastures: return includeNegativeOne(score.getPastureScore());
	    	case R.id.grains: return includeNegativeOne(score.getGrainScore());
	    	case R.id.vegetables: return includeNegativeOne(score.getVegetableScore());
	    	case R.id.sheep: return includeNegativeOne(score.getSheepScore());
	    	case R.id.wild_boar: return includeNegativeOne(score.getBoarScore());
	    	case R.id.cattle: return includeNegativeOne(score.getCattleScore());
    	}
    	
    	if(id == R.id.room_type) {
    		switch(score.getRoomType()) {
    			case Wood: return 0;
    			case Clay: return 1;
    			case Stone: return 2;
    		}
    	}
    	
    	if(id == R.id.family_members) {
    		// Family count wasn't stored before Farmers support, calculate if we don't have any in-bed family
    		if(score.getInBedFamilyCount() != 0)
    			return score.getTotalFamilyCount() - 1;
    		
    		return (score.getFamilyMemberScore() / 3) - 1;
    	}

    	return 0;
	}
	
	public int getValueForNumberPicker(Score baseScore, int id) {
		AgricolaScore score = (AgricolaScore) baseScore;
		
		switch(id) {
	    	case R.id.unused_spaces_picker: return score.getUnusedSpacesScore() * -1;
	    	case R.id.fenced_stables_picker: return score.getFencedStablesScore();
	    	case R.id.rooms_picker: return score.getRoomCount();
	    	case R.id.points_for_cards_picker: return score.getPointsForCards();
	    	case R.id.bonus_points_picker: return score.getBonusPoints();
	    	case R.id.begging_cards_picker: return score.getBeggingCardsScore() / -3;
	    	case R.id.horses_picker: return includeNegativeOne(score.getHorsesScore());
	    	case R.id.in_bed_family_picker: return score.getInBedFamilyCount();
		}
		return 0;
	}

	public static int getScoreForFields(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int fieldCount = Integer.parseInt(firstCharacter);
		switch(fieldCount) {
			case 0: return -1;
			case 2: return 1;
			case 3: return 2;
			case 4: return 3;
			case 5: return 4;
		}
		throw new Exception("Bad score for fields: " + text);
	}
	
	public static int getScoreForPastures(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int pastureCount = Integer.parseInt(firstCharacter);
		switch(pastureCount) {
			case 0: return -1;
			case 1: return 1;
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
		}
		throw new Exception("Bad score for pastures: " + text);
	}

	public static int getScoreForGrains(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int grainCount = Integer.parseInt(firstCharacter);
		switch(grainCount) {
			case 0: return -1;
			case 1: return 1;
			case 4: return 2;
			case 6: return 3;
			case 8: return 4;
		}
		throw new Exception("Bad score for grain: " + text);
	}

	public static int getScoreForVegetables(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int vegetableCount = Integer.parseInt(firstCharacter);
		switch(vegetableCount) {
			case 0: return -1;
			case 1: return 1;
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
		}
		throw new Exception("Bad score for vegetables: " + text);
	}

	public static int getScoreForSheep(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int sheepCount = Integer.parseInt(firstCharacter);
		switch(sheepCount) {
			case 0: return -1;
			case 1: return 1;
			case 4: return 2;
			case 6: return 3;
			case 8: return 4;
		}
		throw new Exception("Bad score for sheep: " + text);
	}
	
	public static int getScoreForWildBoar(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int wildBoarCount = Integer.parseInt(firstCharacter);
		switch(wildBoarCount) {
			case 0: return -1;
			case 1: return 1;
			case 3: return 2;
			case 5: return 3;
			case 7: return 4;
		}
		throw new Exception("Bad score for wild boar: " + text);
	}
	
	public static int getScoreForCattle(CharSequence text) throws Exception {
		String firstCharacter = text.subSequence(0, 1).toString();
		int cattleCount = Integer.parseInt(firstCharacter);
		switch(cattleCount) {
			case 0: return -1;
			case 1: return 1;
			case 2: return 2;
			case 4: return 3;
			case 6: return 4;
		}
		throw new Exception("Bad score for cattle: " + text);
	}
	
	public static int getScoreForUnusedSpaces(int unusedSpacesCount) {
		return -1 * unusedSpacesCount;
	}
	
	public static int getScoreForFencedStables(int fencedStablesCount) {
		return fencedStablesCount;
	}
	
	public static int getScoreForRooms(int roomCount, RoomType type) throws Exception {
		switch(type) {
			case Wood:
				return 0;
			case Clay:
				return roomCount;
			case Stone:
				return roomCount * 2;
		}
		throw new Exception("Bad score for rooms: ");
	}
	
	public static int getScoreForFamilyMembers(int totalFamilyCount, int inBedFamilyCount) {
		int healthyFamilyMemberCount = totalFamilyCount - inBedFamilyCount;
		return (healthyFamilyMemberCount * 3) + inBedFamilyCount;
	}
	
	public static int getScoreForPointsForCards(int pointsForCards) {
		return pointsForCards;
	}
	
	public static int getScoreForBonusPoints(int bonusPoints) {
		return bonusPoints;
	}
	
	public static int getScoreForBeggingCards(int beggingCardCount) {
		return beggingCardCount * -3;
	}
	
	public static int getScoreForHorses(int horseCount) {
		if(GameCache.getInstance().getGameType() != GameType.Farmers)
			return 0;
		
		if(horseCount == 0)
			return -1;
		
		return horseCount;
	}

    @Override
    public int getUnitScore(Score score, SegmentedUnitScoreView unitScoreView) {
        AgricolaScore agricolaScore = (AgricolaScore) score;

        switch(unitScoreView.getId()) {
  		    	case R.id.fields:
  		    		return agricolaScore.getFieldScore();
  		    	case R.id.pastures:
                    return agricolaScore.getPastureScore();
  		    	case R.id.grains:
                    return agricolaScore.getGrainScore();
  		    	case R.id.vegetables:
                    return agricolaScore.getVegetableScore();
  		    	case R.id.sheep:
                    return agricolaScore.getSheepScore();
  		    	case R.id.wild_boar:
                    return agricolaScore.getBoarScore();
  		    	case R.id.cattle:
                    return agricolaScore.getCattleScore();
  		    	case R.id.room_type:
                    return agricolaScore.getRoomsScore();
  		    	case R.id.family_members:
                    return agricolaScore.getFamilyScoreWithoutInBedFamily();
                default:
                    throw new IllegalArgumentException("Unknown score view: " + unitScoreView + "!?");
  	    	}
    }

    @Override
    public int getUnitScore(Score score, PickerUnitScoreView unitScoreView) {
        AgricolaScore agricolaScore = (AgricolaScore) score;

        switch(unitScoreView.getId()) {
  		    	case R.id.unused_spaces_picker:
                    return agricolaScore.getUnusedSpacesScore();
  		    	case R.id.fenced_stables_picker:
                    return agricolaScore.getFencedStablesScore();
  		    	case R.id.rooms_picker:
                    return agricolaScore.getRoomsScore();
  		    	case R.id.points_for_cards_picker:
                    return agricolaScore.getPointsForCards();
  		    	case R.id.bonus_points_picker:
                    return agricolaScore.getBonusPoints();
  		    	case R.id.begging_cards_picker:
                    return agricolaScore.getBeggingCardsScore();
  		    	case R.id.horses_picker:
                    return agricolaScore.getHorsesScore();
  		    	case R.id.in_bed_family_picker:
                    return agricolaScore.getInBedFamilyScore();
                default:
                    throw new IllegalArgumentException("Unknown score view: " + unitScoreView + "!?");
  	    	}
    }
}
