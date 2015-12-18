package com.geeksong.agricolascorer;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class ActionBarHelper {
    public static void setActionBarTitle(Activity activity, int textResource) throws NullPointerException {
        ActionBar actionBar = activity.getActionBar();
        View actionBarView = activity.getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView title = (TextView) actionBarView.findViewById(R.id.title);

        title.setTypeface(FontManager.getInstance().getDominican(), Typeface.NORMAL);
        title.setText(textResource);

        if(actionBar == null) {
            Log.wtf("AgricolaScorer", "Setting custom view for ActionBarHelper on an ActionBar that doesn't exist.");
            return;
        }
        actionBar.setCustomView(actionBarView);
    }
}
