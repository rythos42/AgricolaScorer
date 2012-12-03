package com.geeksong.agricolascorer;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class ActionBarHelper {
    public static void setActionBarTitle(Activity activity, int textResource) {
        ActionBar actionBar = activity.getActionBar();
        View actionBarView = activity.getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView title = (TextView) actionBarView.findViewById(R.id.title);
        
        title.setTypeface(FontManager.getInstance().getDominican());
        title.setText(textResource);
        
        actionBar.setCustomView(actionBarView);
    }
}
