package com.plshapkin.barcodescanner.util;

import android.databinding.BindingConversion;
import android.view.View;

public final class BindingConversions {

	@BindingConversion
	public static int booleanToVisibility(boolean visible) {
		return visible ? View.VISIBLE : View.GONE;
	}

}
