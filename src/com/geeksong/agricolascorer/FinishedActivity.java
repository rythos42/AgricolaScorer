package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.listadapter.FinalScoreAdapter;
import com.geeksong.agricolascorer.mapper.Database;
import com.geeksong.agricolascorer.mapper.SavedGameMapper;
import com.geeksong.agricolascorer.model.Score;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class FinishedActivity extends Activity {
	private SavedGameMapper mapper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        
        mapper = new SavedGameMapper(Database.getInstance());
        
        ArrayList<Score> scoreList = GameCache.getScoreList();
        FinalScoreAdapter adapter = new FinalScoreAdapter(this, R.layout.finished_player_list_item, scoreList);
        
        ListView finalScore = (ListView) findViewById(R.id.finishedList);
        finalScore.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finished, menu);
        return true;
    }
    
    public void saveGame(View source) {
    	mapper.save(GameCache.getScoreList());
    	
    	startAgain(source);
    }
    
    public void startAgain(View source) {
    	GameCache.clearGame();
    	
    	Intent createGameIntent = new Intent(source.getContext(), CreateGameActivity.class);
    	startActivity(createGameIntent);
    }
}
