package com.plshapkin.barcodescanner.model;

import java.io.Serializable;

import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class BarcodeItemModel extends BaseModel implements Serializable {

	public BarcodeItemModel() {
		if (getHttpClient() == null)
			initHttpClient();
	}

	public Observable<Integer> getUrlStatusCode(final String url) {
		return Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				if (subscriber.isUnsubscribed())
					return;
				try {
					Response response = getHttpClient()
							.newCall(new Request.Builder().url(url).build()).execute();
					subscriber.onNext(response.code());
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}
}
