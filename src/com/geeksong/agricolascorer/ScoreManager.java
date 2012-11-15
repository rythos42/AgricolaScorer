package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.model.RoomType;
import com.geeksong.agricolascorer.model.Score;

import android.util.Log;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ScoreManager implements OnCheckedChangeListener, OnValueChangeListener {
	private Score score;
	private TextView totalScoreView;
	
	public ScoreManager(Score score, TextView totalScoreView) {
		this.score = score;
		this.totalScoreView = totalScoreView;
		
		initializeScore(this.score);
		totalScoreView.setText(Integer.toString(score.getTotalScore()));
	}
	
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    	try {
	    	RadioButton checked = (RadioButton) group.findViewById(checkedId);
	    	CharSequence text = checked.getText();
	    	
	    	switch(group.getId()) {
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
		    		score.setRoomType(RoomType.valueOf(text.toString()));
		    		score.setRoomsScore(getScoreForRooms(score.getRoomCount(), score.getRoomType()));
		    		break;
		    	case R.id.family_members:
		    		score.setFamilyMemberScore(getScoreForFamilyMembers(text));
		    		break;
	    	}
			
			totalScoreView.setText(Integer.toString(score.getTotalScore()));
    	} catch(Exception e) {
    		Log.e("com.geeksong.agricolascorer", e.getMessage());
    	}
    }
    
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		try {
	    	switch(picker.getId()) {
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
	    	}
			totalScoreView.setText(Integer.toString(score.getTotalScore()));
    	} catch(Exception e) {
    		Log.e("com.geeksong.agricolascorer", e.getMessage());
    	}
	}
	
	private static void initializeScore(Score s) {
		try {
			s.setFieldScore(getScoreForFields("0"));
			s.setPastureScore(getScoreForPastures("0"));
			s.setGrainScore(getScoreForGrains("0"));
			s.setVegetableScore(getScoreForVegetables("0"));
			s.setSheepScore(getScoreForSheep("0"));
			s.setBoarScore(getScoreForWildBoar("0"));
			s.setCattleScore(getScoreForCattle("0"));
			s.setRoomType(RoomType.Wood);
			s.setFamilyMemberScore(getScoreForFamilyMembers("2"));
			s.setUnusedSpacesScore(getScoreForUnusedSpaces(0));
			s.setFencedStablesScore(getScoreForFencedStables(0));
			s.setRoomsScore(getScoreForRooms(2, s.getRoomType()));
			s.setPointsForCards(getScoreForPointsForCards(0));
			s.setBonusPoints(getScoreForBonusPoints(0));
			s.setBeggingCardsScore(getScoreForBeggingCards(0));
		} catch(Exception e) {
    		Log.e("com.geeksong.agricolascorer", e.getMessage());
    	}
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
	
	public static int getScoreForFamilyMembers(CharSequence text) {
		String firstCharacter = text.subSequence(0, 1).toString();
		int familyMemberCount = Integer.parseInt(firstCharacter);
		return familyMemberCount * 3;
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
}
