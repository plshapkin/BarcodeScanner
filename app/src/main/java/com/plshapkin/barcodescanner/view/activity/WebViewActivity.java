package com.plshapkin.barcodescanner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.plshapkin.barcodescanner.R;
import com.plshapkin.barcodescanner.databinding.WebViewBinding;

public class WebViewActivity extends BaseActivity {

	public static Intent getStartIntent(Context context, String url) {
		return new Intent(context, WebViewActivity.class)
				.putExtra("url", url);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(getIntent().getStringExtra("url"));
		final WebViewBinding binding = DataBindingUtil.setContentView(this, R.layout.web_view);
		binding.webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				getSupportActionBar().setTitle(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		binding.webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				binding.progressBar.setProgress(newProgress);
				binding.progressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
			}
		});
		binding.webView.getSettings().setJavaScriptEnabled(true);
		binding.webView.loadUrl(getIntent().getStringExtra("url"));
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
