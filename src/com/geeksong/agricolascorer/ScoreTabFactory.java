package com.geeksong.agricolascorer;

import java.util.Dictionary;

import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.view.View;
import android.widget.TabHost;

public class ScoreTabFactory implements TabHost.TabContentFactory {
	private Dictionary<String, Score> scores;
	private Activity tabHost;
	
	public ScoreTabFactory(Activity tabHost, Dictionary<String, Score> scores) {
		this.scores = scores;
		this.tabHost = tabHost;
	}
	
	public View createTabContent(String tag) {
		View scorePlayer = tabHost.getLayoutInflater().inflate(R.layout.score_player, null);
		scores.put(tag, new Score());
		
		return scorePlayer;
	}
}
