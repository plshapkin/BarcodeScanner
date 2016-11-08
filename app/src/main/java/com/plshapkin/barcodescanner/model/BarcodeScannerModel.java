package com.plshapkin.barcodescanner.model;

import android.content.Context;

import com.plshapkin.barcodescanner.data.pojo.Barcode;

import java.io.Serializable;
import java.util.List;

import rx.Observable;

public class BarcodeScannerModel extends BaseModel implements Serializable {

	public BarcodeScannerModel(Context context) {
		if (getDatabaseHelper() == null)
			initDatabaseHelper(context);
	}

	public Observable<List<Barcode>> getBarcodes() {
		return getDatabaseHelper().getBarcodes().limit(1);
	}

	public void insertBarcode(Barcode barcode) {
		getDatabaseHelper().insertBarcode(barcode).subscribe();
	}
}
