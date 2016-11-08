package com.plshapkin.barcodescanner.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	public static final String DATABASE_FILE_NAME = "app_database.db";
	public static final int DATABASE_VERSION = 1;

	public DbOpenHelper(Context context) {
		super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(Db.BarcodeTable.CREATE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}