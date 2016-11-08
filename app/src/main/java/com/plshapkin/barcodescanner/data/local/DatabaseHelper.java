package com.plshapkin.barcodescanner.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.plshapkin.barcodescanner.data.pojo.Barcode;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DatabaseHelper {

	private final BriteDatabase db;

	public DatabaseHelper(DbOpenHelper dbOpenHelper) {
		db = new SqlBrite.Builder().build().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
	}

	public Observable<Void> insertBarcode(final Barcode barcode) {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {
				db.insert(Db.BarcodeTable.TABLE_NAME,
						Db.BarcodeTable.toContentValues(barcode),
						SQLiteDatabase.CONFLICT_REPLACE);
			}
		});
	}

	public Observable<List<Barcode>> getBarcodes() {
		return db.createQuery(Db.BarcodeTable.TABLE_NAME, Db.Sql.GET_BARCODES)
				.mapToList(new Func1<Cursor, Barcode>() {
					@Override
					public Barcode call(Cursor cursor) {
						return Db.BarcodeTable.parseCursor(cursor);
					}
				});
	}
}
