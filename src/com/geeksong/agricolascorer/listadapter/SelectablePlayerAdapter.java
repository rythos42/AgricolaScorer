package com.geeksong.agricolascorer.listadapter;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.mapper.Database;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectablePlayerAdapter extends SimpleCursorAdapter {
	private OnCheckedChangeListener onCheckChange;
	
	private int layout;
	
	public SelectablePlayerAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		this.layout = layout;
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckChange) {
		this.onCheckChange = onCheckChange;
	}
	
	@Override
    public View newView(Context context, Cursor playerCursor, ViewGroup parent) {
		String playerName = playerCursor.getString(playerCursor.getColumnIndex(Database.KEY_NAME));
        
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);
 
		CheckBox playerCheckBox = (CheckBox) v.findViewById(R.id.playerCheckBox);
		playerCheckBox.setText(playerName);
		playerCheckBox.setOnCheckedChangeListener(onCheckChange);
	
		return v;
	}
	
	@Override
	public void bindView(View v, Context context, Cursor c) {  
        CheckBox playerCheckBox = (CheckBox) v.findViewById(R.id.playerCheckBox);
        String playerName = playerCheckBox.getText().toString();
        
        // Calls check change listener, so remove it before setting the checked status.
        playerCheckBox.setOnCheckedChangeListener(null);
		playerCheckBox.setChecked(StatisticSearch.getInstance().isPlayerSelected(playerName));
		playerCheckBox.setOnCheckedChangeListener(onCheckChange);
	}
}
