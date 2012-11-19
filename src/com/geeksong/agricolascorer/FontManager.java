package com.geeksong.agricolascorer;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontManager {
	private static FontManager instance;
	public static FontManager getInstance() {
		return instance;
	}
	
	public static void initialize(AssetManager assets) {
		instance = new FontManager(assets);
	}
	
	private AssetManager assets;
	private Typeface dominican;
	
	private FontManager(AssetManager assets) {
		this.assets = assets;
	}
	
	public Typeface getDominican() {
		if(dominican == null)
			dominican = Typeface.createFromAsset(assets, "DOMINICA.TTF");
		return dominican;
	}
}
