package com.plshapkin.barcodescanner.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.plshapkin.barcodescanner.viewModel.BaseViewModel;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseActivity extends AppCompatActivity {

	private final Set<BaseViewModel> viewModels = Collections.newSetFromMap(new ConcurrentHashMap<BaseViewModel, Boolean>());
	private Toast toast;

	public final void showToast(@StringRes int resId) {
		showToast(getString(resId));
	}

	public final void showToast(String text) {
		hideToast();
		toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void hideToast() {
		if (toast != null)
			toast.cancel();
	}

	protected final void bindViewModel(@NonNull BaseViewModel viewModel) {
		viewModels.add(viewModel);
		viewModel.setActivity(this);
	}

	protected final void unbindViewModel(@NonNull BaseViewModel viewModel) {
		viewModels.remove(viewModel);
		viewModel.setActivity(null);
	}

	protected final void unbindAllViewModels() {
		for (BaseViewModel viewModel : viewModels) {
			unbindViewModel(viewModel);
		}
	}

	@Override
	@CallSuper
	protected void onStart() {
		super.onStart();
		for (BaseViewModel viewModel : viewModels) {
			viewModel.onStart();
		}
	}

	@Override
	@CallSuper
	protected void onResume() {
		super.onResume();
		for (BaseViewModel viewModel : viewModels) {
			viewModel.onResume();
		}
	}

	@Override
	@CallSuper
	protected void onPause() {
		super.onPause();
		for (BaseViewModel viewModel : viewModels) {
			viewModel.onPause();
		}
	}

	@Override
	@CallSuper
	protected void onStop() {
		super.onStop();
		for (BaseViewModel viewModel : viewModels) {
			viewModel.onStop();
		}
	}
}
