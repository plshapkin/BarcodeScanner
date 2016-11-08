package com.plshapkin.barcodescanner;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

public class AppDelegate extends Application {

	public static final String LOG_TAG = String.valueOf(BuildConfig.APPLICATION_ID.hashCode());

	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
		Logger.init(LOG_TAG).logLevel(LogLevel.FULL);
	}
}
