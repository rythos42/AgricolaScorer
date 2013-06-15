package com.geeksong.agricolascorer.listadapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.formatter.GameCountFormatter;
import com.geeksong.agricolascorer.model.Player;

public class AddPlayerAdapter extends ArrayAdapter<Player> {
	public AddPlayerAdapter(Context context, int layout, int textViewResourceId, List<Player> players) {
		super(context, layout, textViewResourceId, players);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		
		Player player = getItem(position);
		boolean isInGame = GameCache.getInstance().isPlayerInGame(player.getName());
		TextView hintLabel = (TextView) v.findViewById(R.id.hintLabel);
		
		if(isInGame) {
			String inGameLabel = getContext().getResources().getString(R.string.in_game_label);
			hintLabel.setText(inGameLabel);
		} else {
			int gameCount = player.getGameCount();
			hintLabel.setText(GameCountFormatter.format(gameCount, getContext()));
		}
		
		return v;
	}
}
