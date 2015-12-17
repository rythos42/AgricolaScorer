package com.geeksong.agricolascorer.listadapter;

import java.util.List;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.Player;
import com.geeksong.agricolascorer.model.StatisticSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectablePlayerAdapter extends ArrayAdapter<Player> {
	private OnCheckedChangeListener onCheckChange;
	private final int layout;
	
	public SelectablePlayerAdapter(Context context, List<Player> players) {
		super(context, R.layout.select_player_list_item, R.id.playerName, players);
		this.layout = R.layout.select_player_list_item;
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckChange) {
		this.onCheckChange = onCheckChange;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
	    Player player = getItem(position);
	    String playerName = player.getName();
	    CheckBox playerCheckBox;

	    if(v == null) {
        	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	v = inflater.inflate(this.layout, null);
        	playerCheckBox = (CheckBox) v.findViewById(R.id.playerCheckBox);
        	playerCheckBox.setText(playerName);
        } else {
        	playerCheckBox = (CheckBox) v.findViewById(R.id.playerCheckBox);
        	playerCheckBox.setOnCheckedChangeListener(null);
        }
    	
		playerCheckBox.setChecked(StatisticSearch.getInstance().isPlayerSelected(playerName));
		playerCheckBox.setOnCheckedChangeListener(onCheckChange);
    	  
		return v;
    }
}
