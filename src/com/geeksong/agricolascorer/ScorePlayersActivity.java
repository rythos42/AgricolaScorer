package com.geeksong.agricolascorer;

import java.util.ArrayList;
import java.util.Hashtable;

import com.geeksong.agricolascorer.model.Score;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScorePlayersActivity extends Activity {
	private ArrayList<Score> scores = new ArrayList<Score>();
	private static final String Scores = "Scores";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_players);

        ArrayList<String> playerList = getIntent().getExtras().getStringArrayList(CreateGameActivity.PlayerList);
        
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        
        ScoreTabFactory factory = new ScoreTabFactory(this, scores);
        
        for(int i = 0; i < playerList.size(); i++ ) {
        	String playerName = playerList.get(i);
            TabSpec tab = tabs.newTabSpec(playerName);
            
            tab.setContent(factory);
            tab.setIndicator(playerName);
            tabs.addTab(tab);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_score_players, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.finishButton:
    	    	Intent finishGameIntent = new Intent(this, FinishedActivity.class);
    	    	
    	    	finishGameIntent.putParcelableArrayListExtra(Scores, this.scores);
    	    	startActivity(finishGameIntent);
    			break;
    	}
    	return true;
    }
}
