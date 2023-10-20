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

    private static final String AUTHORITY = "edu.uiuc.cs427app.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/users");
    private SQLiteOpenHelper dbHelper;

    private static final int USERS = 100;
    private static final int USER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.myapp.provider/users
        sUriMatcher.addURI("edu.uiuc.cs427app.provider", "users", USERS);

        // content://com.example.myapp.provider/users/#
        sUriMatcher.addURI("edu.uiuc.cs427app.provider", "users/#", USER_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new UserDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(UserContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

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

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = db.update(UserContract.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(UserContract.TABLE_NAME, selection, selectionArgs);
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    // ... (other methods and member variables)
}

