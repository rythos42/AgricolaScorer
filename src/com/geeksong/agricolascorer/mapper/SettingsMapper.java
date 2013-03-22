package com.geeksong.agricolascorer.mapper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingsMapper {
    private Database db;
    
    private boolean wasFarmers;
    
    private static SettingsMapper instance;
    public static SettingsMapper getInstance() {
    	if(instance == null)
    		instance = new SettingsMapper();
    	
    	return instance;
    }
 
    private SettingsMapper() {
    	this.db = Database.getInstance();
    	
    	// get the settings, or insert a row if there is no settings row
    	SQLiteDatabase sqlDb = db.getWritableDatabase();
    	Cursor cursor = sqlDb.rawQuery(String.format("SELECT %s FROM %s", Database.KEY_REMEMBER_FARMERS, Database.TABLE_SETTINGS), null);
    	if(cursor.moveToNext()) {
    		wasFarmers = cursor.getInt(0) != 0;
    	} else {
    		ContentValues values = new ContentValues();
    		values.put(Database.KEY_REMEMBER_FARMERS, 0);
    		sqlDb.insert(Database.TABLE_SETTINGS, null, values);
    	}
    }
    
    public boolean wasLastGameFarmers() {
    	return wasFarmers;
    }
    
    public void setLastGameWasFarmers(boolean isFarmers) {
    	wasFarmers = isFarmers;
    	
    	SQLiteDatabase sqlDb = db.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(Database.KEY_REMEMBER_FARMERS, wasFarmers ? 1 : 0);
    	sqlDb.update(Database.TABLE_SETTINGS, values, null, null);
    }
}
