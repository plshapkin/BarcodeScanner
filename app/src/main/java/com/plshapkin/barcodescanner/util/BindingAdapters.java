package com.plshapkin.barcodescanner.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

public final class BindingAdapters {

	@BindingAdapter("observableData")
	public static void RecyclerViewObservableDataAdapter(RecyclerView recyclerView, Object data) {
		if (recyclerView.getAdapter() != null)
			recyclerView.getAdapter().notifyDataSetChanged();
	}
}
