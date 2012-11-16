package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FinalScoreAdapter extends ArrayAdapter<Score> {
	private ArrayList<Score> scores;
	private Context context;
	
	public FinalScoreAdapter(Context context, int textViewResourceId, ArrayList<Score> scores) {
		super(context, textViewResourceId, scores);
		
		this.context = context;
		this.scores = scores;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    if (v == null)
	    	v = ((Activity)context).getLayoutInflater().inflate(R.layout.finished_player_list_item, null);
	
	    Score o = scores.get(position);
	    if (o != null) {
	            TextView playerNameView = (TextView) v.findViewById(R.id.name);
	            playerNameView.setText(o.getPlayer().getName());
	            
	            TextView scoreView = (TextView) v.findViewById(R.id.score);
	            scoreView.setText(Integer.toString(o.getTotalScore()));
	    }
	    return v;
    }
}
