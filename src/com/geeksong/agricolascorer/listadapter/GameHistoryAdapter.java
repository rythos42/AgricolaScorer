package com.geeksong.agricolascorer.listadapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.geeksong.agricolascorer.R;
import com.geeksong.agricolascorer.model.Game;
import com.geeksong.agricolascorer.model.Score;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class GameHistoryAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<Game> games;
	
	public GameHistoryAdapter(Context context, ArrayList<Game> games) {
		this.context = context;
		this.games = games;
	}
	
	public void deleteGame(Game game) {
		games.remove(game);
		notifyDataSetChanged();
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		Game game = games.get(groupPosition);
		return game.getScore(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ((Activity)context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.game_history_player_list_item, null);
		}
		
		Score score = (Score) getChild(groupPosition, childPosition);
		
		TextView personNameView = (TextView) convertView.findViewById(R.id.name);
		personNameView.setText(score.getPlayer().getName());

		TextView personScoreView = (TextView) convertView.findViewById(R.id.score);
		personScoreView.setText(Integer.toString(score.getTotalScore()));

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return games.get(groupPosition).getScoreCount();
	}

	public Object getGroup(int groupPosition) {
		return games.get(groupPosition);
	}

	public int getGroupCount() {
		return games.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		Game game = (Game) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ((Activity)context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.game_history_list_item, null);
		}
		
		TextView gameDateView = (TextView) convertView.findViewById(R.id.gameDate);
		Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
		gameDateView.setText(formatter.format(game.getDate()));

		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}