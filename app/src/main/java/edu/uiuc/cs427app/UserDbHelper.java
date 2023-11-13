package edu.uiuc.cs427app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {

    // Database name and version constants
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for UserDbHelper
     *
     * @param context The context in which the database helper is created.
     */
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     * This method executes an SQL statement to create the 'users' table.
     *
     * @param db The SQLiteDatabase instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserContract.TABLE_NAME + " ("
                + UserContract.COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + UserContract.COLUMN_PASSWORD + " TEXT, "
                + UserContract.COLUMN_FAVORED_CITIES + " TEXT, "
                + UserContract.COLUMN_THEME + " TEXT)";
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     * This method is not currently implemented and can be modified to handle database upgrades.
     *
     * @param db         The SQLiteDatabase instance.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    /**
     * Called when the database has been opened.
     * This method can be used to perform actions when the database is opened,
     * such as dropping and recreating tables if necessary.
     *
     * @param db The SQLiteDatabase instance.
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Drop the existing user table and recreate it when the database is opened
        // TODO: uncomment this part when move to production
        // db.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
        // onCreate(db);
    }
}
