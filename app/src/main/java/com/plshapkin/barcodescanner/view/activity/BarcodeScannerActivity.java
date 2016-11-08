package com.plshapkin.barcodescanner.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.plshapkin.barcodescanner.R;
import com.plshapkin.barcodescanner.data.pojo.Barcode;
import com.plshapkin.barcodescanner.databinding.BarcodeItemBinding;
import com.plshapkin.barcodescanner.databinding.BarcodeScannerBinding;
import com.plshapkin.barcodescanner.model.BarcodeItemModel;
import com.plshapkin.barcodescanner.model.BarcodeScannerModel;
import com.plshapkin.barcodescanner.ui.adapter.DataBoundAdapter;
import com.plshapkin.barcodescanner.ui.adapter.DataBoundViewHolder;
import com.plshapkin.barcodescanner.viewModel.BarcodeItemViewModel;
import com.plshapkin.barcodescanner.viewModel.BarcodeScannerViewModel;

import java.util.List;

public class BarcodeScannerActivity extends BaseActivity {

	private BarcodeScannerBinding binding;
	private BarcodeScannerViewModel barcodeScannerViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareBinding(savedInstanceState);
		prepareBarcodeScanner();
		prepareRecyclerView();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BarcodeScannerViewModel.class.getName(), barcodeScannerViewModel);
	}

	@Override
	protected void onResume() {
		super.onResume();
		binding.barcodeScanner.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		binding.barcodeScanner.pause();
	}

	private void prepareBinding(Bundle savedState) {
		binding = DataBindingUtil.setContentView(this, R.layout.barcode_scanner);
		barcodeScannerViewModel = savedState != null && savedState.containsKey(BarcodeScannerViewModel.class.getName()) ?
				(BarcodeScannerViewModel) savedState.getSerializable(BarcodeScannerViewModel.class.getName()) :
				new BarcodeScannerViewModel(new BarcodeScannerModel(getApplicationContext()));
		bindViewModel(barcodeScannerViewModel);
		binding.setViewModel(barcodeScannerViewModel);
	}

	private void prepareBarcodeScanner() {
		binding.barcodeScanner.decodeContinuous(new BarcodeScannerCallback());
	}

	private void prepareRecyclerView() {
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(R.layout.barcode_item);
		binding.recyclerView.setAdapter(adapter);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
				binding.recyclerView.getContext(),
				((LinearLayoutManager)binding.recyclerView.getLayoutManager()).getOrientation());
		binding.recyclerView.addItemDecoration(dividerItemDecoration);
	}

	private class BarcodeScannerCallback implements BarcodeCallback {

		private BeepManager beepManager;

		public BarcodeScannerCallback() {
			beepManager = new BeepManager(BarcodeScannerActivity.this);
			beepManager.setVibrateEnabled(true);
			beepManager.setBeepEnabled(false);
		}

		@Override
		public void barcodeResult(BarcodeResult result) {
			beepManager.playBeepSoundAndVibrate();
			Barcode barcode = new Barcode();
			barcode.setText(result.getText());
			barcodeScannerViewModel.addBarcode(barcode);
		}

		@Override
		public void possibleResultPoints(List<ResultPoint> resultPoints) {

		}
	}

	private class RecyclerViewAdapter extends DataBoundAdapter<BarcodeItemBinding> {

		public RecyclerViewAdapter(@LayoutRes int layoutId) {
			super(layoutId);
		}

		@Override
		public DataBoundViewHolder<BarcodeItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
			DataBoundViewHolder<BarcodeItemBinding> holder = super.onCreateViewHolder(parent, viewType);
			BarcodeItemViewModel itemViewModel = new BarcodeItemViewModel(new BarcodeItemModel());
			bindViewModel(itemViewModel);
			holder.binding.setViewModel(itemViewModel);

			return holder;
		}

		@Override
		protected void bindItem(DataBoundViewHolder<BarcodeItemBinding> holder, int position,
								List<Object> payloads) {
			holder.binding.getViewModel().swapBarcode(barcodeScannerViewModel.barcodes.get(position));
		}

		@Override
		public int getItemCount() {
			return barcodeScannerViewModel.barcodes.size();
		}
	}
}
