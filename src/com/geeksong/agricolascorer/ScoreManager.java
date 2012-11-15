package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.model.RoomType;
import com.geeksong.agricolascorer.model.Score;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ScoreManager implements OnCheckedChangeListener {
	private Score score;
	private TextView totalScoreView;
	
	public ScoreManager(Score score, TextView totalScoreView) {
		this.score = score;
		this.totalScoreView = totalScoreView;
	}
	
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    	try {
	    	RadioButton checked = (RadioButton) group.findViewById(checkedId);
	    	CharSequence text = checked.getText();
	    	
	    	switch(group.getId()) {
		    	case R.id.fields:
		    		score.setFieldScore(ScoreManager.getScoreForFields(text));
		    		break;
		    	case R.id.pastures:
		    		score.setPastureScore(ScoreManager.getScoreForPastures(text));
		    		break;
		    	case R.id.grains:
		    		score.setGrainScore(ScoreManager.getScoreForGrains(text));
		    		break;
		    	case R.id.vegetables:
		    		score.setVegetableScore(ScoreManager.getScoreForVegetables(text));
		    		break;
		    	case R.id.sheep:
		    		score.setSheepScore(ScoreManager.getScoreForSheep(text));
		    		break;
		    	case R.id.wild_boar:
		    		score.setBoarScore(ScoreManager.getScoreForWildBoar(text));
		    		break;
		    	case R.id.cattle:
		    		score.setCattleScore(ScoreManager.getScoreForCattle(text));
		    		break;
	    	}
			
			totalScoreView.setText(Integer.toString(score.getTotalScore()));
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
	
	public static int getScoreForFamilyMembers(int familyMemberCount) {
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
