package com.plshapkin.barcodescanner.viewModel;

import android.databinding.ObservableArrayList;

import com.plshapkin.barcodescanner.data.pojo.Barcode;
import com.plshapkin.barcodescanner.model.BarcodeScannerModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BarcodeScannerViewModel extends BaseViewModel implements Serializable {

	public transient ObservableArrayList<Barcode> barcodes;

	private final BarcodeScannerModel barcodeScannerModel;

	public BarcodeScannerViewModel(BarcodeScannerModel barcodeScannerModel) {
		this.barcodeScannerModel = barcodeScannerModel;
		barcodes = new ObservableArrayList<>();
		getBarcodes();
	}

	public void getBarcodes() {
		registerSubscription(barcodeScannerModel.getBarcodes()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<List<Barcode>>() {
							@Override
							public void call(List<Barcode> barcodeList) {
								barcodes.clear();
								barcodes.addAll(barcodeList);
							}
						}));
	}

	public void addBarcode(Barcode barcode) {
		if (!barcodes.contains(barcode)) {
			barcodeScannerModel.insertBarcode(barcode);
			barcodes.add(barcode);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		barcodes = new ObservableArrayList<>();
		getBarcodes();
	}
}
