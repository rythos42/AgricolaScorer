package com.geeksong.agricolascorer;

import java.util.ArrayList;
import java.util.Hashtable;

import com.geeksong.agricolascorer.model.Score;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScorePlayersActivity extends Activity {
	private Hashtable<String, Score> scores = new Hashtable<String, Score>();
	
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
}
