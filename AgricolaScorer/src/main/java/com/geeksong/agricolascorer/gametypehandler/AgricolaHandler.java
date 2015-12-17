package com.geeksong.agricolascorer.gametypehandler;

import com.geeksong.agricolascorer.gametypehandler.IGameTypeHandler;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.AgricolaScore;

public class AgricolaHandler implements IGameTypeHandler<AgricolaScore> {
    static final int[] SCORE_LABEL_IDS = new int[]{
            R.string.fields_label,
            R.string.pastures_label,
            R.string.grain_label,
            R.string.vegetables_label,
            R.string.sheep_label,
            R.string.wild_boar_label,
            R.string.cattle_label,
            R.string.family_members_label,
            R.string.rooms_label,
            R.string.unused_spaces_label,
            R.string.points_for_cards_label,
            R.string.fenced_stables_label,
            R.string.bonus_points_label,
            R.string.begging_cards_label
    };

    public int[] getScoreLabelIds() {
        return SCORE_LABEL_IDS;
    }

    public int getUnitScoreForRow(AgricolaScore score, int scoreIndex) {
        switch (scoreIndex) {
            case 0:
                return score.getFieldScore();
            case 1:
                return score.getPastureScore();
            case 2:
                return score.getGrainScore();
            case 3:
                return score.getVegetableScore();
            case 4:
                return score.getSheepScore();
            case 5:
                return score.getBoarScore();
            case 6:
                return score.getCattleScore();
            case 7:
                return score.getFamilyScoreWithoutInBedFamily();
            case 8:
                return score.getRoomsScore();
            case 9:
                return score.getUnusedSpacesScore();
            case 10:
                return score.getPointsForCards();
            case 11:
                return score.getFencedStablesScore();
            case 12:
                return score.getBonusPoints();
            case 13:
                return score.getBeggingCardsScore();
            default:
                throw new IllegalArgumentException("No score defined at row " + scoreIndex + "!?");
        }
    }
}
