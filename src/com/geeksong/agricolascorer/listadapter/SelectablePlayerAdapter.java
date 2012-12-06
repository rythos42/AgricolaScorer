package com.geeksong.agricolascorer.listadapter;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.mapper.Database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectablePlayerAdapter extends SimpleCursorAdapter {
	private OnCheckedChangeListener onCheckChange;
	
	public SelectablePlayerAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckChange) {
		this.onCheckChange = onCheckChange;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		
		Cursor playerCursor = (Cursor) this.getItem(position);
		String playerName = playerCursor.getString(playerCursor.getColumnIndex(Database.KEY_NAME));

		CheckBox playerCheckBox = (CheckBox) v.findViewById(R.id.playerCheckBox);
		playerCheckBox.setText(playerName);
		playerCheckBox.setOnCheckedChangeListener(onCheckChange);
	
		return v;
	}

}
