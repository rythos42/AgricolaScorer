package com.geeksong.agricolascorer.gametypehandler;

import com.geeksong.agricolascorer.gametypehandler.IGameTypeHandler;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.AllCreaturesScore;

public class AllCreaturesHandler implements IGameTypeHandler<AllCreaturesScore> {
    public static final int[] SCORE_LABEL_IDS = new int[]{
            R.string.sheep_label,
            R.string.wild_boar_label,
            R.string.cattle_label,
            R.string.horses_label,
            R.string.full_expansion_count_label,
            R.string.building_score_label
    };

    public int[] getScoreLabelIds() {
        return SCORE_LABEL_IDS;
    }

    public int getUnitScoreForRow(AllCreaturesScore score, int scoreIndex) {
        switch (scoreIndex) {
            case 0:
                return score.getSheepScore() + score.getSheepBonusScore();
            case 1:
                return score.getWildBoarScore() + score.getWildBoarBonusScore();
            case 2:
                return score.getCattleScore() + score.getCattleBonusScore();
            case 3:
                return score.getHorseScore() + score.getHorseBonusScore();
            case 4:
                return score.getFullExpansionScore();
            case 5:
                return score.getBuildingScore();
            default:
                throw new IllegalArgumentException("No score defined at row " + scoreIndex + "!?");
        }
    }
}
