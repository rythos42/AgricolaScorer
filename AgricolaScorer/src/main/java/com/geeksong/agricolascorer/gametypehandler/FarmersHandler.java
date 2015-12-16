package com.geeksong.agricolascorer.gametypehandler;

import com.geeksong.agricolascorer.gametypehandler.AgricolaHandler;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.AgricolaScore;

public  class FarmersHandler extends AgricolaHandler {
    public static final int[] SCORE_LABEL_IDS = new int[AgricolaHandler.SCORE_LABEL_IDS.length + 2];
    static {
        System.arraycopy(AgricolaHandler.SCORE_LABEL_IDS, 0, SCORE_LABEL_IDS, 0,
                AgricolaHandler.SCORE_LABEL_IDS.length);
        SCORE_LABEL_IDS[AgricolaHandler.SCORE_LABEL_IDS.length] = R.string.horses_label;
        SCORE_LABEL_IDS[AgricolaHandler.SCORE_LABEL_IDS.length + 1] = R.string.in_bed_family_label;
    }

    @Override
    public int[] getScoreLabelIds() {
        return SCORE_LABEL_IDS;
    }

    @Override
    public int getUnitScoreForRow(AgricolaScore score, int scoreIndex) {
        switch (scoreIndex) {
            case 14:
                return score.getHorsesScore();
            case 15:
                return score.getInBedFamilyScore();
            default:
                return super.getUnitScoreForRow(score, scoreIndex);
        }
    }
}
