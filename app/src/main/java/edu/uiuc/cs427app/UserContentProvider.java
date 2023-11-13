package edu.uiuc.cs427app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserContentProvider extends ContentProvider {

    // Content provider constants
    private static final String AUTHORITY = "edu.uiuc.cs427app.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/users");
    private SQLiteOpenHelper dbHelper;

    // URI matcher constants
    private static final int USERS = 100;
    private static final int USER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.myapp.provider/users
        sUriMatcher.addURI("edu.uiuc.cs427app.provider", "users", USERS);

        // content://com.example.myapp.provider/users/#
        sUriMatcher.addURI("edu.uiuc.cs427app.provider", "users/#", USER_ID);
    }


    /**
     * Initializes the content provider activity.
     *
     * @return True upon successful initialization.
     */
    @Override
    public boolean onCreate() {
        dbHelper = new UserDbHelper(getContext());
        return true;
    }

    /**
     * Queries data from the database.
     *
     * @param uri           The content URI to query.
     * @param projection    The list of columns to include in the query.
     * @param selection     The selection criteria.
     * @param selectionArgs The selection arguments.
     * @param sortOrder     The sort order.
     * @return A Cursor containing the result of the query.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(UserContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Inserts data into the database.
     *
     * @param uri    The content URI to insert data into.
     * @param values The ContentValues containing the data to insert.
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(UserContract.TABLE_NAME, null, values);
        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
            Uri insertedUri = ContentUris.withAppendedId(uri, id);
            Log.d("UserContentProvider", "Data inserted successfully at: " + insertedUri);
            return insertedUri;
        } else {
            // Handle insertion failure
            Log.e("UserContentProvider", "Failed to insert data");
            return null;
        }
    }

    /**
     * Updates data in the database.
     *
     * @param uri           The content URI to update data in.
     * @param values        The ContentValues containing the data to update.
     * @param selection     The selection criteria.
     * @param selectionArgs The selection arguments.
     * @return The number of rows updated.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = db.update(UserContract.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Deletes data from the database.
     *
     * @param uri           The content URI to delete data from.
     * @param selection     The selection criteria.
     * @param selectionArgs The selection arguments.
     * @return The number of rows deleted.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(UserContract.TABLE_NAME, selection, selectionArgs);
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of the data at the given URI.
     *
     * @param uri The content URI.
     * @return The MIME type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

}

