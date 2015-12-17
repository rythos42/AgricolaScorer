package com.geeksong.agricolascorer.mapper;

import java.util.Locale;

import com.geeksong.agricolascorer.model.GameType;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	private static final String Name = "AgricolaScorer";
	private static final int Version = 31;
	
	
    public static final String TABLE_RECENTPLAYERS = "RecentPlayers";
	public static final String TABLE_GAMES = "Games";
	public static final String TABLE_SCORES = "Scores";
	private static final String TABLE_SETTINGS = "Settings";
	public static final String TABLE_ALL_CREATURES_SCORES = "AllCreaturesScores";
    
    public static final String KEY_NAME = "name";
    private static final String KEY_FARMERS = "farmers";
    public static final String KEY_GAMECOUNT = "gameCount";
    private static final String KEY_WHO_WON = "whoWon";
	
	public static final String KEY_DATE = "playedDate";
	public static final String KEY_GAME_TYPE = "gameType";
	
	public static final String KEY_ID = "id";
	public static final String KEY_FINALSCORE = "finalScore";
	
	public static final String KEY_FIELDSCORE = "fieldScore";
	public static final String KEY_PASTURESCORE = "pastureScore";
	public static final String KEY_GRAINSCORE = "grainScore";
	public static final String KEY_VEGETABLESCORE = "vegetableScore";
	public static final String KEY_SHEEPSCORE = "sheepScore";
	public static final String KEY_WILDBOARSCORE = "wildBoarScore";
	public static final String KEY_CATTLESCORE = "cattleScore";
	public static final String KEY_UNUSEDSPACESSCORE = "unusedSpacesScore";
	public static final String KEY_FENCEDSTABLESSCORE = "fencedStablesScore";
	public static final String KEY_ROOMSSCORE = "roomsScore";
	public static final String KEY_FAMILYMEMBERSCORE = "familyMemberScore";
	public static final String KEY_POINTSFORCARDS = "pointsForCards";
	public static final String KEY_BONUSPOINTS = "bonusPoints";
	public static final String KEY_BEGGINGCARDSSCORE = "beggingCardsScore";
	public static final String KEY_HORSESCORE = "horseScore";
	public static final String KEY_INBEDFAMILYCOUNT = "inBedFamilyCount";
	public static final String KEY_TOTALFAMILYCOUNT = "totalFamilyCount";
	
	public static final String KEY_ROOMTYPE = "roomType";
	public static final String KEY_ROOMCOUNT = "roomCount";
	public static final String KEY_PLAYERID = "playerId";
	public static final String KEY_GAMEID = "gameId";
	
	public static final String KEY_REMEMBER_FARMERS = "rememberFarmers";
	
	public static final String KEY_SHEEP_COUNT = "sheepCount";
	public static final String KEY_WILD_BOAR_COUNT = "wildBoarCount";
	public static final String KEY_CATTLE_COUNT = "cattleCount";
	public static final String KEY_HORSE_COUNT = "horseCount";
	public static final String KEY_FULL_EXPANSION_COUNT = "fullExpansionCount";
	public static final String KEY_BUILDING_POINTS = "buildingPoints";
	
	private static Database instance;
	public static Database getInstance() {
		return instance;
	}
	
	public static void initialize(Context context) {
		instance = new Database(context);
	}
	
	private Database(Context context) {
		super(context, Name, null, Version);
	}
	
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECENTPLAYERS_TABLE = "CREATE TABLE " + TABLE_RECENTPLAYERS + "(" +
        		KEY_ID + " INTEGER PRIMARY KEY," +
        		KEY_NAME + " TEXT " +
				")";
        db.execSQL(CREATE_RECENTPLAYERS_TABLE);
        
    	String CREATE_GAMES_TABLE = "CREATE TABLE " + TABLE_GAMES + "(" +
	    		KEY_ID + " INTEGER PRIMARY KEY, " +
	    		KEY_DATE + " INTEGER, " +
	    		KEY_GAME_TYPE + " INTEGER, " +
	    		KEY_WHO_WON + " INTEGER " +
			")";
    	db.execSQL(CREATE_GAMES_TABLE);
        	
	    String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "(" +
		    KEY_ID + " INTEGER PRIMARY KEY, " +
		    KEY_FINALSCORE + " INTEGER, " +
		    KEY_FIELDSCORE + " INTEGER, " +
		    KEY_PASTURESCORE + " INTEGER, " +
		    KEY_GRAINSCORE + " INTEGER, " +
		    KEY_VEGETABLESCORE + " INTEGER, " +
		    KEY_SHEEPSCORE + " INTEGER, " +
		    KEY_WILDBOARSCORE + " INTEGER, " +
		    KEY_CATTLESCORE + " INTEGER, " +
		    KEY_UNUSEDSPACESSCORE + " INTEGER, " +
		    KEY_FENCEDSTABLESSCORE + " INTEGER, " +
		    KEY_ROOMSSCORE + " INTEGER, " +
		    KEY_FAMILYMEMBERSCORE + " INTEGER, " +
		    KEY_POINTSFORCARDS + " INTEGER, " +
		    KEY_BONUSPOINTS + " INTEGER, " +
		    KEY_BEGGINGCARDSSCORE + " INTEGER, " +
		    KEY_ROOMTYPE + " INTEGER, " +
		    KEY_ROOMCOUNT + " INTEGER, " +
		    KEY_PLAYERID + " INTEGER, " +
		    KEY_GAMEID + " INTEGER, " + 
		    KEY_HORSESCORE + " INTEGER, " +
		    KEY_INBEDFAMILYCOUNT + " INTEGER, " +
		    KEY_TOTALFAMILYCOUNT + " INTEGER, " + 
			"FOREIGN KEY(" + KEY_PLAYERID + ") REFERENCES " + TABLE_RECENTPLAYERS + "(" + KEY_ID + "), " +
			"FOREIGN KEY(" + KEY_GAMEID + ") REFERENCES " + TABLE_GAMES + "(" + KEY_ID + ")" +
			")";
	    db.execSQL(CREATE_SCORES_TABLE);

	    createAllCreaturesScoreTable(db);
    }
    
    private void createAllCreaturesScoreTable(SQLiteDatabase db) {
	    String CREATE_ALL_CREATURES_SCORE_TABLE = "CREATE TABLE " + TABLE_ALL_CREATURES_SCORES + "(" +
	    		KEY_ID + " INTEGER PRIMARY KEY, " +
	    		KEY_SHEEP_COUNT + " INTEGER, " +
	    		KEY_WILD_BOAR_COUNT + " INTEGER, " +
	    		KEY_CATTLE_COUNT + " INTEGER, " +
	    		KEY_HORSE_COUNT + " INTEGER, " +
	    		KEY_FULL_EXPANSION_COUNT + " INTEGER, " +
	    		KEY_BUILDING_POINTS + " INTEGER, " +
	    		KEY_GAMEID + " INTEGER, " + 
	    		KEY_PLAYERID + " INTEGER, " +
				"FOREIGN KEY(" + KEY_PLAYERID + ") REFERENCES " + TABLE_RECENTPLAYERS + "(" + KEY_ID + "), " +
				"FOREIGN KEY(" + KEY_GAMEID + ") REFERENCES " + TABLE_GAMES + "(" + KEY_ID + ")" +
	    		")";
	    db.execSQL(CREATE_ALL_CREATURES_SCORE_TABLE);
    }
     
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// KEY_GAMECOUNT on TABLE_RECENTPLAYERS isn't used anymore and could be dropped if SQLite supported it nicely.
    	// "farmers" on TABLE_GAMES can be dropped.
    	
    	if(oldVersion <= 19) {
	    	db.execSQL("ALTER TABLE " + TABLE_SCORES + " ADD COLUMN " + KEY_HORSESCORE + " INTEGER");
	    	db.execSQL("ALTER TABLE " + TABLE_SCORES + " ADD COLUMN " + KEY_INBEDFAMILYCOUNT + " INTEGER");
	    	db.execSQL("ALTER TABLE " + TABLE_SCORES + " ADD COLUMN " + KEY_TOTALFAMILYCOUNT + " INTEGER");
    	}
    	
    	if(oldVersion <= 30) {
    		createAllCreaturesScoreTable(db);

    		db.execSQL("ALTER TABLE " + TABLE_GAMES + " ADD COLUMN " + KEY_GAME_TYPE + " INTEGER");
			db.execSQL("ALTER TABLE " + TABLE_GAMES + " ADD COLUMN " + KEY_WHO_WON + " INTEGER");

    		// Make GameType column populated for all previous games.
    		db.execSQL(String.format(Locale.US, "UPDATE %s SET %s=%d WHERE %s=%d AND (%s IS NULL OR %s ='')", 
    				TABLE_GAMES, KEY_GAME_TYPE, GameType.Farmers.ordinal(), KEY_FARMERS, 1, KEY_GAME_TYPE, KEY_GAME_TYPE));
    		db.execSQL(String.format(Locale.US, "UPDATE %s SET %s=%d WHERE (%s=%d OR %s IS NULL) AND (%s IS NULL OR %s = '')", 
    				TABLE_GAMES, KEY_GAME_TYPE, GameType.Agricola.ordinal(), KEY_FARMERS, 0, KEY_FARMERS, KEY_GAME_TYPE, KEY_GAME_TYPE));
    	
			db.execSQL("DROP TABLE " + TABLE_SETTINGS);
    	}
    }
}
