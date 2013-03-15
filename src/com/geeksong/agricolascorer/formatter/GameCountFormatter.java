package com.geeksong.agricolascorer.formatter;

import android.content.Context;

import com.geeksong.agricolascorer.R;

public class GameCountFormatter {
	public static String format(int count, Context context) {
        return context.getResources().getQuantityString(R.plurals.games_count, count, count);
	}
}
