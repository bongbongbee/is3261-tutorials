package com.example.tysonquek.tysonfurniture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by tysonquek on 27/8/15.
 */
public class FurnitureOpenHelper extends SQLiteOpenHelper {
    private static final boolean DEBUG = false;
    private static final String DATABASE_NAME = "FurnitureRecords.db";
    private static final int DATABASE_VERSION = 3;
    private static final String COLUMN_TYPE = "TEXT";
    private static final String COMMA_SEP = ",";
    private static final String FURNITURE_TABLE_CREATE =
            "CREATE TABLE " + FurnitureDBContract.FurnitureEntry.TABLE_NAME + " ("
                    + FurnitureDBContract.FurnitureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + FurnitureDBContract.FurnitureEntry.COLUMN_NAME_FURNITURE + " " + COLUMN_TYPE + COMMA_SEP
                    + FurnitureDBContract.FurnitureEntry.COLUMN_NAME_CLASSIFICATION + " " + COLUMN_TYPE + ");";
    private static final String FURNITURE_TABLE_DROP = "DROP TABLE IF EXISTS " + FurnitureDBContract.FurnitureEntry.TABLE_NAME;
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public FurnitureOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        if (DEBUG) Log.d("[DEBUG] " + getClass().getSimpleName(), ": " + FURNITURE_TABLE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG) Log.d("[DEBUG] " + getClass().getSimpleName(), ": " + FURNITURE_TABLE_CREATE);
        db.execSQL(FURNITURE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor retrieveAll() {
        if (mDatabase == null) mDatabase = getWritableDatabase();

        String[] projection = {
                FurnitureDBContract.FurnitureEntry._ID,
                FurnitureDBContract.FurnitureEntry.COLUMN_NAME_FURNITURE,
                FurnitureDBContract.FurnitureEntry.COLUMN_NAME_CLASSIFICATION
        };
        String sortOrder = FurnitureDBContract.FurnitureEntry._ID + " ASC";

        Cursor cursor = mDatabase.query(
                FurnitureDBContract.FurnitureEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.d(getClass().getSimpleName(), cursor.getCount() + "");
        if (cursor.getCount() > 0) {
            if (DEBUG) Log.d(getClass().getSimpleName(), "more than 0");
            return cursor;
        } else {
            if (DEBUG) Log.d(getClass().getSimpleName(), "less than 0");
            return null;
        }

    }

    public void insert(String furnitureName, String classification) {
        if (mDatabase == null) mDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FurnitureDBContract.FurnitureEntry.COLUMN_NAME_FURNITURE, String.valueOf(furnitureName));
        values.put(FurnitureDBContract.FurnitureEntry.COLUMN_NAME_CLASSIFICATION, String.valueOf(classification));
        mDatabase.insert(FurnitureDBContract.FurnitureEntry.TABLE_NAME, null, values);
//        mDatabase.close();
    }

    public void deleteAll() {
        if (mDatabase == null) mDatabase = getWritableDatabase();
        mDatabase.delete(FurnitureDBContract.FurnitureEntry.TABLE_NAME, null, null);
    }

}

final class FurnitureDBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FurnitureDBContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class FurnitureEntry implements BaseColumns {
        public static final String TABLE_NAME = "furniture";
        public static final String COLUMN_NAME_FURNITURE = "furniture";
        public static final String COLUMN_NAME_CLASSIFICATION = "classification";

    }
}
