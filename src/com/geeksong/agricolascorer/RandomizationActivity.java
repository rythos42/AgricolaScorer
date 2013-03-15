package com.geeksong.agricolascorer;

import java.util.ArrayList;
import java.util.Random;

import com.geeksong.agricolascorer.mapper.PlayerMapper;
import com.geeksong.agricolascorer.model.Player;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

public class RandomizationActivity extends Activity {
	private Random random = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomization);
		
        ActionBarHelper.setActionBarTitle(this, R.string.title_activity_randomize);
	}
	
	public void randomizePlayer(View source) {
		String playerName = GameCache.getInstance().hasPlayers() ? randomizeFromGame() : randomizeFromDatabase();
		
		new AlertDialog.Builder(this)
			.setTitle(R.string.randomize_player)
			.setMessage(playerName)
			.setPositiveButton(R.string.ok, null)
			.show();
	}
	
	private String randomizeFromGame() {
		ArrayList<Player> playerList = GameCache.getInstance().getPlayerList();
		int randomPlayerIndex = random.nextInt(playerList.size());
		return playerList.get(randomPlayerIndex).getName();
	}
	
	private String randomizeFromDatabase() {
		ArrayList<String> playerList = PlayerMapper.getInstance().getPlayerNames();
		int randomPlayerIndex = random.nextInt(playerList.size());
		return playerList.get(randomPlayerIndex);
	}
}
