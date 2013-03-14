package com.geeksong.agricolascorer.listadapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geeksong.agricolascorer.GameCache;
import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.formatter.GameCountFormatter;
import com.geeksong.agricolascorer.mapper.Database;

public class AddPlayerAdapter extends SimpleCursorAdapter {
	private Context context;
	
	public AddPlayerAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		
		Cursor playerCursor = (Cursor) this.getItem(position);
		String playerName = playerCursor.getString(playerCursor.getColumnIndex(Database.KEY_NAME));
		
		boolean isInGame = GameCache.getInstance().isPlayerInGame(playerName);
		TextView hintLabel = (TextView) v.findViewById(R.id.hintLabel);
		
		if(isInGame) {
			String inGameLabel = context.getResources().getString(R.string.in_game_label);
			hintLabel.setText(inGameLabel);
		} else {
			int gameCount = playerCursor.getInt(playerCursor.getColumnIndex(Database.KEY_GAMECOUNT));
			hintLabel.setText(GameCountFormatter.format(gameCount, context));
		}
		
		return v;
	}
}
