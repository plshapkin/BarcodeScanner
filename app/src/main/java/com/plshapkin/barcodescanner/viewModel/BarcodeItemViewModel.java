package com.plshapkin.barcodescanner.viewModel;

import android.databinding.Bindable;
import android.webkit.URLUtil;

import com.orhanobut.logger.Logger;
import com.plshapkin.barcodescanner.data.pojo.Barcode;
import com.plshapkin.barcodescanner.model.BarcodeItemModel;
import com.plshapkin.barcodescanner.view.activity.WebViewActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BarcodeItemViewModel extends BaseViewModel {

	private final BarcodeItemModel barcodeItemModel;
	private Barcode barcode;

	public BarcodeItemViewModel(BarcodeItemModel barcodeItemModel) {
		this.barcodeItemModel = barcodeItemModel;
	}

	@Bindable
	public Barcode getBarcode() {
		return barcode;
	}

	public void swapBarcode(Barcode barcode) {
		this.barcode = barcode;
		notifyChange();
		if (barcode.getStatusCode() == 0 && URLUtil.isValidUrl(barcode.getText()))
			checkStatusCode();
	}

	public void onClick() {
		if (URLUtil.isValidUrl(barcode.getText()))
			getActivity().startActivity(WebViewActivity.getStartIntent(getActivity(), barcode.getText()));
	}

	private void checkStatusCode() {
		unsubscribeRegisteredSubscriptions();
		registerSubscription(barcodeItemModel.getUrlStatusCode(barcode.getText())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<Integer>() {
							@Override
							public void call(Integer integer) {
								barcode.setStatusCode(integer);
								notifyChange();
							}
						},
						new Action1<Throwable>() {
							@Override
							public void call(Throwable e) {
								Logger.e(e, e.getMessage());
							}
						}
				));
	}
}
