package com.geeksong.agricolascorer.gametypehandler;

import com.geeksong.agricolascorer.model.Score;

public interface IGameTypeHandler<T extends Score> {
    int[] getScoreLabelIds();
    int getUnitScoreForRow(T score, int rowNum);
}
