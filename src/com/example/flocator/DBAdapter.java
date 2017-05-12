package com.example.flocator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;


import com.example.flocator.MainActivity;

public class DBAdapter  {
	
    static final String KEY_ROWID = "Number";
    static final String KEY_NAME = "Name";
    static final String KEY_ADDRESS = "Address";
    static final String KEY_POSTALCODE = "Postal_Code";
    static final String KEY_AREA = "Area";
    static final String KEY_CITY = "City";
    static final String KEY_LATITUDE = "Latitude";
    static final String KEY_LONGITUDE = "Longitude";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "schools.db";
    static final String DATABASE_TABLE = "torontoschools";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
        "create table schools (number integer primary key autoincrement, "
        + "Name text, Address text, Post_Code text, Area text, City text, Latitude text, Longitude text);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //---retrieves all the contacts---
    public Cursor getAllSchools()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, null, null, null, null, null);
    }

    public Cursor getOneMileSchools()
    {
    	//String s= "SELECT" + KEY_NAME + "FROM " + DATABASE_TABLE + "WHERE" + KEY_LATITUDE + "=" + 43.63831652520;
    	
    	//return db.rawQuery(s, null);
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                new String[]{MainActivity.latitude +"0.01041666666" ,MainActivity.longitude + "0.01445671659"}, null, null, null);        
    }
  

    public Cursor getFiveMileSchools()
    {

        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                new String[]{MainActivity.latitude + "0.05208333333",MainActivity.longitude + "0.05208333333"}, null, null, null);  
             
    }
    
    
    public Cursor getTenMileSchools()
    {

        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                new String[]{MainActivity.latitude + "0.10416666666",MainActivity.longitude + "0.10416666666"}, null, null, null);  
    }
    
    
    public Cursor getTwentyFiveMileSchools()
    {
    	 return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                 KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                 new String[]{MainActivity.latitude + "0.26041666666",MainActivity.longitude + "0.26041666666"}, null, null, null); 
    }
    
    
    public Cursor getFiftyMileSchools()
    {
    	 return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                 KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                 new String[]{MainActivity.latitude + "0.52083333333",MainActivity.longitude + "0.52083333333"}, null, null, null); 
    }
    
    
    
    public Cursor getHundredMileSchools()
    
    {
    	 return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                 KEY_ADDRESS,KEY_POSTALCODE,KEY_AREA, KEY_CITY,KEY_LATITUDE, KEY_LONGITUDE}, KEY_LATITUDE + "<=? AND " + KEY_LONGITUDE + "<=?",
                 new String[]{MainActivity.latitude + "1.04166666666",MainActivity.longitude + "1.04166666666"}, null, null, null); 
    }
     
    //---retrieves a particular contact---
    public Cursor getSchool(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_POSTALCODE,KEY_AREA,KEY_CITY, KEY_LATITUDE,KEY_LONGITUDE}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
      
}

