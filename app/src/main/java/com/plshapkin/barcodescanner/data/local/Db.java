package com.plshapkin.barcodescanner.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.plshapkin.barcodescanner.data.pojo.Barcode;

public class Db {

	public abstract static class BarcodeTable {

		public static final String TABLE_NAME = "barcode";

		public static final String COLUMN_TEXT = "text";

		public static final String CREATE = ""
				+ " CREATE TABLE " + TABLE_NAME
				+ " ( "
				+ COLUMN_TEXT + " TEXT PRIMARY KEY "
				+ " ); ";

		public static ContentValues toContentValues(Barcode barcode) {
			ContentValues values = new ContentValues();
			values.put(COLUMN_TEXT, barcode.getText());

			return values;
		}

		public static Barcode parseCursor(Cursor cursor) {
			Barcode barcode = new Barcode();
			barcode.setText(getString(cursor, COLUMN_TEXT));

			return barcode;
		}
	}

	public abstract static class Sql {

		public static final String GET_BARCODES = ""
				+ " SELECT * FROM " + BarcodeTable.TABLE_NAME;
	}

	public static String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
	}
}