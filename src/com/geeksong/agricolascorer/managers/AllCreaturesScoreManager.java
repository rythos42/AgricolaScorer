package com.geeksong.agricolascorer.managers;

import android.util.Log;
import android.widget.TextView;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.control.PickerUnitScoreView;
import com.geeksong.agricolascorer.control.SegmentedUnitScoreView;
import com.geeksong.agricolascorer.model.AllCreaturesScore;
import com.geeksong.agricolascorer.model.Score;

public class AllCreaturesScoreManager extends ScoreManager {
	private TextView totalScoreView;
	
	public AllCreaturesScoreManager(AllCreaturesScore score, TextView totalScoreView) {
		super(score);

		this.totalScoreView = totalScoreView;
		
		totalScoreView.setText(Integer.toString(score.getTotalScore()));
	}
	
	public  void onPickerScoreChange(PickerUnitScoreView unitScoreView, int newVal) {
		try {
			AllCreaturesScore score = (AllCreaturesScore) getScore();
			
	    	switch(unitScoreView.getId()) {
		    	case R.id.sheep_picker:
		    		score.setSheepScore(getScoreForSheep(newVal));
		    		score.setSheepBonusScore(getBonusScoreForSheep(newVal));
		    		break;
		    	case R.id.wild_boar_picker:
		    		score.setWildBoarScore(getScoreForWildBoar(newVal));
		    		score.setWildBoarBonusScore(getBonusScoreForWildBoar(newVal));
		    		break;
		    	case R.id.cattle_picker:
		    		score.setCattleScore(getScoreForCattle(newVal));
		    		score.setCattleBonusScore(getBonusScoreForCattle(newVal));
		    		break;
		    	case R.id.horse_picker:
		    		score.setHorseScore(getScoreForHorse(newVal));
		    		score.setHorseBonusScore(getBonusScoreForHorse(newVal));
		    		break;
		    	case R.id.full_expansion_count_picker:
		    		score.setFullExpansionScore(getScoreForFullExpansion(newVal));
		    		break;
		    	case R.id.building_score_picker:
		    		score.setBuildingScore(getScoreForBuildings(newVal));
		    		break;
	    	}
			totalScoreView.setText(Integer.toString(getScore().getTotalScore()));
    	} catch(Exception e) {
    		Log.e("com.geeksong.agricolascorer", e.getMessage());
    	}
	}
	
	@Override
	public int getValueForNumberPicker(Score baseScore, int id) {
		AllCreaturesScore score = (AllCreaturesScore) baseScore;
		
		switch(id) {
			case R.id.sheep_picker: return score.getSheepScore();
			case R.id.wild_boar_picker: return score.getWildBoarScore();
			case R.id.cattle_picker: return score.getCattleScore();
			case R.id.horse_picker: return score.getHorseScore();
			case R.id.full_expansion_count_picker: return score.getFullExpansionScore() / 4;
			case R.id.building_score_picker: return score.getBuildingScore();
		}
		return 0;
	}
	
	public static int getScoreForSheep(int count) {
		return count;
	}
	
	public static int getBonusScoreForSheep(int count) {
		if(count <= 3)
			return -3;
		
		if(count >= 8 && count <= 10)
			return 1;
		if(count >= 11 && count <= 12)
			return 2;
		if(count >= 13)
			return count - 10;
		
		return 0;
	}
	
	public static int getScoreForWildBoar(int count) {
		return count;
	}
	
	public static int getBonusScoreForWildBoar(int count) {
		if(count <= 3)
			return -3;
		
		if(count >= 7 && count <= 8)
			return 1;
		if(count >= 9 && count <= 10)
			return 2;
		if(count >= 11)
			return count - 8;
		
		return 0;
	}
	
	public static int getScoreForCattle(int count) {
		return count;
	}
	
	public static int getBonusScoreForCattle(int count) {
		if(count <= 3)
			return -3;
		
		if(count >= 6 && count <= 7)
			return 1;
		if(count >= 8 && count <= 9)
			return 2;
		if(count >= 10)
			return count - 7;
		
		return 0;
	}
	
	public static int getScoreForHorse(int count) {
		return count;
	}
	
	public static int getBonusScoreForHorse(int count) {
		if(count <= 3)
			return -3;
		
		if(count >= 5 && count <= 6)
			return 1;
		if(count >= 7 && count <= 8)
			return 2;
		if(count >= 9)
			return count - 6;
		
		return 0;
	}
	
	public static int getScoreForFullExpansion(int count) {
		return count * 4;
	}
	
	public static int getScoreForBuildings(int score) {
		return score;
	}

    @Override
    public int getUnitScore(Score score, SegmentedUnitScoreView unitScoreView) throws Exception {
        throw new IllegalArgumentException("Unkown score view: " + unitScoreView.getId() + "!?");
    }

    @Override
    public int getUnitScore(Score score, PickerUnitScoreView unitScoreView) throws Exception {
        int value = unitScoreView.getNumberPicker().getValue();

        switch(unitScoreView.getId()) {
  		    	case R.id.sheep_picker:
  		    		return getScoreForSheep(value);
  		    	case R.id.wild_boar_picker:
                    return getScoreForWildBoar(value);
  		    	case R.id.cattle_picker:
                    return getScoreForCattle(value);
  		    	case R.id.horse_picker:
                    return getScoreForHorse(value);
  		    	case R.id.full_expansion_count_picker:
                    return getScoreForFullExpansion(value);
  		    	case R.id.building_score_picker:
                    return getScoreForBuildings(value);
                default:
                    throw new IllegalArgumentException("Unkown score view: " + unitScoreView + "!?");
  	    	}
    }
}
