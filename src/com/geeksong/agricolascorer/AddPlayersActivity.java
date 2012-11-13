package com.geeksong.agricolascorer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddPlayersActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_players, menu);
        return true;
    }
}
