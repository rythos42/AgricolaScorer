package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.mapper.PlayerMapper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayerListActivity extends Activity implements OnItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        
        ListView list = (ListView) this.findViewById(R.id.playerList);
        list.setOnItemClickListener(this);
        list.setAdapter(PlayerMapper.getInstance().getPlayersListAdapter());
    }
    
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView item = (TextView) view.findViewById(R.id.name);
		String playerName = item.getText().toString().trim();
		
    	Intent backToAddPlayer = new Intent();
    	backToAddPlayer.putExtra(CreateGameActivity.AddedPlayerBundleKey, playerName);
    	setResult(RESULT_OK, backToAddPlayer);
    	finish();
	}
}
