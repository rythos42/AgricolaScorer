package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.mapper.Database;

import android.app.Application;

public class AgricolaApplication extends Application {
	@Override
	public void onCreate() {
		Database.initialize(getApplicationContext());
		FontManager.initialize(getAssets());
	}
}
