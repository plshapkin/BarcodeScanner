package com.plshapkin.barcodescanner.viewModel;

import android.databinding.BaseObservable;

import com.plshapkin.barcodescanner.view.activity.BaseActivity;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseViewModel extends BaseObservable {

	private WeakReference<BaseActivity> activity = new WeakReference<>(null);
	private final CompositeSubscription subscriptions = new CompositeSubscription();

	public final BaseActivity getActivity() {
		return activity.get();
	}

	public final void setActivity(BaseActivity activity) {
		this.activity = new WeakReference<>(activity);
	}

	public void onStart() {
	}

	public void onResume() {
	}

	public void onPause() {
	}

	public void onStop() {
	}

	public final Subscription registerSubscription(Subscription subscription) {
		subscriptions.add(subscription);

		return subscription;
	}

	public final void unsubscribeRegisteredSubscriptions() {
		subscriptions.clear();
	}
}