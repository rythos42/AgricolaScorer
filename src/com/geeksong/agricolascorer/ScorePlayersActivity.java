package com.geeksong.agricolascorer;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScorePlayersActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_players);

        ArrayList<String> playerList = getIntent().getExtras().getStringArrayList(CreateGameActivity.PlayerList);
        
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        
        for(int i = 0; i < playerList.size(); i++ ) {
        	String playerName = playerList.get(i);
            TabSpec tab = tabs.newTabSpec(playerName);
            tab.setContent(R.id.scorePlayer);
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
