package com.geeksong.agricolascorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.geeksong.agricolascorer.gametypehandler.AgricolaHandler;
import com.geeksong.agricolascorer.gametypehandler.AllCreaturesHandler;
import com.geeksong.agricolascorer.gametypehandler.FarmersHandler;
import com.geeksong.agricolascorer.gametypehandler.IGameTypeHandler;
import com.geeksong.agricolascorer.mapper.SavedGameMapper;
import com.geeksong.agricolascorer.model.GameType;
import com.geeksong.agricolascorer.model.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// SuppressWarnings: for generic around Score, because Score is not parameterized outside this class
@SuppressWarnings("unchecked")
public class FinishedActivity extends Activity {
    private static final Map<GameType, IGameTypeHandler> GAME_TYPE_HANDLERS = new HashMap<>();
    static {
        GAME_TYPE_HANDLERS.put(GameType.Agricola, new AgricolaHandler());
        GAME_TYPE_HANDLERS.put(GameType.Farmers, new FarmersHandler());
        GAME_TYPE_HANDLERS.put(GameType.AllCreatures, new AllCreaturesHandler());
    }

    private SavedGameMapper mapper;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);

        mapper = new SavedGameMapper();

        ArrayList<Score> scores = GameCache.getInstance().getScoreList();
        GameType gameType = GameCache.getInstance().getGameType();
        IGameTypeHandler gameTypeHandler = GAME_TYPE_HANDLERS.get(gameType);

        createHeaderRow(scores);
        createBodyRows(gameTypeHandler, scores);
        createFooterRow(scores);
    }

    private void createHeaderRow(ArrayList<Score> scores) {
        TableRow tableRow = (TableRow) findViewById(R.id.finishedTableHeader);
        for (Score score : scores) {
            tableRow.addView(createPlayerNameCell(score), createTableCellLayout());
        }
    }

    private void createBodyRows(IGameTypeHandler gameTypeHandler, ArrayList<Score> scores) {
        TableLayout table = (TableLayout) findViewById(R.id.finishedTable);

        int[] scoreLabelIds = gameTypeHandler.getScoreLabelIds();
        for (int i = 0; i < scoreLabelIds.length; i++) {
            int scoreLabelId = scoreLabelIds[i];
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.finished_row, null);
            ((TextView) tableRow.getChildAt(0)).setText(scoreLabelId);

            // Detect min/max
            int maxUnitScore = Integer.MIN_VALUE;
            int minUnitScore = Integer.MAX_VALUE;
            for (Score score : scores) {
                int unitScore = gameTypeHandler.getUnitScoreForRow(score, i);
                if (unitScore > maxUnitScore)
                    maxUnitScore = unitScore;
                if (unitScore < minUnitScore)
                    minUnitScore = unitScore;
            }

            for (Score score : scores) {
                tableRow.addView(createUnitScoreCell(gameTypeHandler, i, score, minUnitScore, maxUnitScore),
                        createTableCellLayout());
            }

            table.addView(tableRow, table.getChildCount() - 1); // Before footer
        }
    }

    private void createFooterRow(ArrayList<Score> scores) {
        TableRow tableRow;
        tableRow = (TableRow) findViewById(R.id.finishedTableFooter);

        // Detect min/max
        int maxTotalScore = Integer.MIN_VALUE;
        int minTotalScore = Integer.MAX_VALUE;
        for (Score score : scores) {
            int totalScore = score.getTotalScore();
            if (totalScore > maxTotalScore)
                maxTotalScore = totalScore;
            if (totalScore < minTotalScore)
                minTotalScore = totalScore;
        }

        for (Score score : scores) {
            tableRow.addView(createTotalScoreCell(score, minTotalScore, maxTotalScore),
                    createTableCellLayout());
        }
    }

    private TableRow.LayoutParams createTableCellLayout() {
        return new TableRow.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    }

    private View createPlayerNameCell(Score score) {
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.finished_cell_player, null);
        textView.setText(score.getPlayer().getName());

        return textView;
    }

    private View createUnitScoreCell(IGameTypeHandler gameTypeHandler, int scoreIndex, Score score, int minUnitScore, int maxUnitScore) {
        int unitScore = gameTypeHandler.getUnitScoreForRow(score, scoreIndex);

        int layout;
        if ((unitScore == maxUnitScore) && (unitScore != minUnitScore))
            layout = R.layout.finished_cell_unit_score_max;
        else if ((unitScore == minUnitScore) && (unitScore != maxUnitScore))
            layout = R.layout.finished_cell_unit_score_min;
        else
            layout = R.layout.finished_cell_unit_score;

        TextView textView = (TextView) getLayoutInflater().inflate(layout, null);
        textView.setText(String.valueOf(unitScore));

        return textView;
    }

    private View createTotalScoreCell(Score score, int minTotalScore, int maxTotalScore) {
        int totalScore = score.getTotalScore();
        int layout;
        if ((totalScore == maxTotalScore) && (totalScore != minTotalScore))
            layout = R.layout.finished_cell_total_score_max;
        else if ((totalScore == minTotalScore) && (totalScore != maxTotalScore))
            layout = R.layout.finished_cell_total_score_min;
        else
            layout = R.layout.finished_cell_total_score;

        TextView textView = (TextView) getLayoutInflater().inflate(layout, null);
        textView.setText(String.valueOf(totalScore));

        return textView;
    }

    public void saveGame(View source) {
        GameCache cache = GameCache.getInstance();
        if (cache.isFromDatabase())
            mapper.save(cache.getGame());
        else
            mapper.save(cache.getScoreList(), GameCache.getInstance().getGameType());

        startAgain(source);
    }

    @SuppressWarnings("WeakerAccess")
    public void startAgain(View source) {
        GameCache.getInstance().clearGame();

        Intent createGameIntent = new Intent(source.getContext(), CreateGameActivity.class);
        startActivity(createGameIntent);
    }
}
