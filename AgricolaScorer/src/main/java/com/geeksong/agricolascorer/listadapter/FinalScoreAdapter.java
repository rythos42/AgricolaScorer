package com.geeksong.agricolascorer.listadapter;

import java.util.List;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class FinalScoreAdapter extends ArrayAdapter<Score> {
	public FinalScoreAdapter(Context context, int textViewResourceId, List<Score> scores) {
		super(context, textViewResourceId, scores);
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    if (v == null)
	    	v = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.finished_player_list_item, null);
	    
	    Score o = getItem(position);
	    if (o != null) {
	            TextView playerNameView = (TextView) v.findViewById(R.id.name);
	            playerNameView.setText(o.getPlayer().getName());
	            
	            TextView scoreView = (TextView) v.findViewById(R.id.score);
	            scoreView.setText(String.format("%d", o.getTotalScore()));
	    }
	    return v;
    }
}
