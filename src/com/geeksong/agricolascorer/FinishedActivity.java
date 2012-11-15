package com.geeksong.agricolascorer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FinishedActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finished, menu);
        return true;
    }
}
