package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.listadapter.FinalScoreAdapter;
import com.geeksong.agricolascorer.mapper.SavedGameMapper;
import com.geeksong.agricolascorer.model.Score;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class FinishedActivity extends Activity {
	private SavedGameMapper mapper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        
        mapper = new SavedGameMapper();
        
        ArrayList<Score> scoreList = GameCache.getInstance().getScoreList();
        FinalScoreAdapter adapter = new FinalScoreAdapter(this, R.layout.finished_player_list_item, scoreList);
        
        ListView finalScore = (ListView) findViewById(R.id.finishedList);
        finalScore.setAdapter(adapter);
    }
    
    public void saveGame(View source) {
    	GameCache cache = GameCache.getInstance();
    	if(cache.isFromDatabase())
    		mapper.save(cache.getGame());
    	else
    		mapper.save(cache.getScoreList(), GameCache.getInstance().getGameType());
    	
    	startAgain(source);
    }
    
    public void startAgain(View source) {
    	GameCache.getInstance().clearGame();
    	
    	Intent createGameIntent = new Intent(source.getContext(), CreateGameActivity.class);
    	startActivity(createGameIntent);
    }
}
