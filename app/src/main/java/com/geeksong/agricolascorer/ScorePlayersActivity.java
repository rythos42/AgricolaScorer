package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.managers.GameTypeManager;
import com.geeksong.agricolascorer.model.Player;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ScorePlayersActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_players);
        
        //Intent intent = getIntent();
        //String action = intent.getAction();
        //String type = intent.getType();

        //if (Intent.ACTION_SEND.equals(action) && type != null && "*/*".equals(type)) {
        //	try {
	    //		byte[] gameBytes = intent.getByteArrayExtra(Intent.EXTRA_STREAM);
	    //		Game game = GameSerializationMapper.deserialize(gameBytes);
	    //		GameCache.getInstance().setGame(game);
        //	} catch(Exception e) {
        		//TODO: dialog for exception
        //	}
        //} 
                
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        
        TabHost.TabContentFactory factory = GameTypeManager.getTabContentFactory(this);
        LayoutInflater inflater = getLayoutInflater();
        
        GameCache cache = GameCache.getInstance();
        ArrayList<Player> playerList = cache.getPlayerList();
        for(int i = 0; i < playerList.size(); i++ ) {
        	Player player = playerList.get(i);
        	String playerName = player.getName();
        	
        	if(!cache.hasScoreForPlayer(playerName))
        		cache.getScoreList().add(GameTypeManager.createScore(player));
        	
            TabSpec tab = tabs.newTabSpec(playerName);
            
            View tabButtonView = inflater.inflate(R.layout.score_tab_button, null);
            TextView tabText = (TextView) tabButtonView.findViewById(R.id.tabText);
            tabText.setText(playerName);
            
            tab.setContent(factory);
            tab.setIndicator(tabButtonView);
            tabs.addTab(tab);
        }
    }
    
    @Override
    public void onBackPressed(){
        finish();
    }
    
	@SuppressWarnings("UnusedParameters")
    public void finishScoring(View source) {
		Intent finishGameIntent = new Intent(this, FinishedActivity.class);
		startActivity(finishGameIntent);
	}
}
