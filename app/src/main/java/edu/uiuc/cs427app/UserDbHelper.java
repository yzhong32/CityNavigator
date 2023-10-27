package edu.uiuc.cs427app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // helper for the db
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // initializes a new SQLite db
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserContract.TABLE_NAME + " ("
                + UserContract.COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + UserContract.COLUMN_PASSWORD + " TEXT, "
                + UserContract.COLUMN_FAVORED_CITIES + " TEXT, "
                + UserContract.COLUMN_THEME + " TEXT)";
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    // upgrades db when called
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    //drop and recreate db when needed
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Drop the existing user table and recreate it when the database is opened
        // TODO: uncomment this part when move to production
//        db.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
//        onCreate(db);
    }
}
