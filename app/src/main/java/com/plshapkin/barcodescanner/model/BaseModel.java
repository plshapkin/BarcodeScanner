package com.plshapkin.barcodescanner.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.plshapkin.barcodescanner.data.local.DatabaseHelper;
import com.plshapkin.barcodescanner.data.local.DbOpenHelper;

import okhttp3.OkHttpClient;

public class BaseModel {

	private static DatabaseHelper databaseHelper;
	private static OkHttpClient httpClient;

	@Nullable
	protected static DatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}

	protected static void initDatabaseHelper(Context context) {
		databaseHelper = new DatabaseHelper(new DbOpenHelper(context));
	}

	@Nullable
	protected static OkHttpClient getHttpClient() {
		return httpClient;
	}

	protected static void initHttpClient() {
		httpClient = new OkHttpClient.Builder()
				.addNetworkInterceptor(new StethoInterceptor())
				.build();
	}
}
