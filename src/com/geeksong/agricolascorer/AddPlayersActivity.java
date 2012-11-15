package com.geeksong.agricolascorer;

<<<<<<< HEAD
=======
import com.geeksong.agricolascorer.mapper.RecentPlayersMapper;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

<<<<<<< HEAD
import com.geeksong.agricolascorer.mapper.RecentPlayersMapper;

public class AddPlayersActivity extends Activity implements OnItemClickListener {
=======
public class AddPlayersActivity extends Activity {
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
	private static final int PICK_CONTACT = 1;
	
	private RecentPlayersMapper recentPlayersMapper; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        
        recentPlayersMapper = new RecentPlayersMapper(this);
<<<<<<< HEAD
        ListView list = (ListView) this.findViewById(R.id.recentPlayersList);
        list.setOnItemClickListener(this);
        list.setAdapter(recentPlayersMapper.getListAdapter());
=======
            }
    
    private void fillData() {
    	// LoaderManager with CursorLoader?
    	/*
        // Get all of the notes from the database and create the item list
        Cursor c = recentPlayersMapper.getTopPlayers(5);

        String[] from = new String[] { RecentPlayersMapper.KEY_NAME };
        int[] to = new int[] { R.id.text1 };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, c, from, to);
        setListAdapter(notes);*/
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
    }
    
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView item = (TextView) view;
		
		putNameIntoInput(item.getText().toString().trim());
		addPlayerToGame(null);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_players, menu);
        return true;
    }
    
    private void putNameIntoInput(String name) {
    	EditText inputPlayerName = (EditText) findViewById(R.id.inputPlayerName);
    	inputPlayerName.setText(name);	
<<<<<<< HEAD
    }
    
    private String getInputName() {
    	EditText inputPlayerName = (EditText) findViewById(R.id.inputPlayerName);
    	return inputPlayerName.getText().toString();
    }
=======
    }    
>>>>>>> branch 'master' of https://github.com/rythos42/AgricolaScorer.git
    
    public void addPlayerToGame(View source) {
    	String name = getInputName();
    	
    	recentPlayersMapper.addPlayer(name);
    	
    	Intent backToCreateGame = new Intent();
    	backToCreateGame.putExtra("playerName", name);
    	setResult(CreateGameActivity.AddPlayerResultCode, backToCreateGame);
    	finish();
    }
    
    public void searchAddressBook(View source) {
    	Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    	startActivityForResult(intent, PICK_CONTACT);
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
    	super.onActivityResult(reqCode, resultCode, data);
	
    	switch (reqCode) {
    		case PICK_CONTACT:
    			if (resultCode == Activity.RESULT_OK) {
    				Uri contactData = data.getData();
    				Cursor c = getContentResolver().query(contactData, null, null, null, null);

    				if (c.moveToFirst()) {
    					String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    					putNameIntoInput(name);          
					}
    			}
			break;
    	}
	}
}
