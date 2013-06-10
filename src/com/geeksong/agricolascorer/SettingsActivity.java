package com.geeksong.agricolascorer;

import android.os.Bundle;
import android.app.Activity;

public class SettingsActivity extends Activity {
	public static String Pref_LimitNumberOfPlayers = "pref_limitNumberOfPlayers";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        ActionBarHelper.setActionBarTitle(this, R.string.settings);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
}
