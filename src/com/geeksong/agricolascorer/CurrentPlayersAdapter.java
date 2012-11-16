package com.geeksong.agricolascorer;

import java.util.ArrayList;

import com.geeksong.agricolascorer.model.Player;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CurrentPlayersAdapter extends ArrayAdapter<Player> {
	private ArrayList<Player> players;
	private Context context;
	
	public CurrentPlayersAdapter(Context context, int textViewResourceId, ArrayList<Player> players) {
		super(context, textViewResourceId, players);
		
		this.context = context;
		this.players = players;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    if (v == null)
	    	v = ((Activity)context).getLayoutInflater().inflate(R.layout.current_players_list_item, null);
	
	    Player player = players.get(position);
	    if (player != null) {
	            TextView playerNameView = (TextView) v.findViewById(R.id.name);
	            playerNameView.setText(player.getName());
	    }
	    return v;
    }
}