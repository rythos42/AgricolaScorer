package com.geeksong.agricolascorer;

import com.geeksong.agricolascorer.mapper.Database;
import com.geeksong.agricolascorer.mapper.PlayerMapper;

import android.app.Application;
import android.content.Context;

public class AgricolaApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		Context context = getApplicationContext();
		
		Database.initialize(context);
		FontManager.initialize(getAssets());
		PlayerMapper.initialize(context);
	}
}
