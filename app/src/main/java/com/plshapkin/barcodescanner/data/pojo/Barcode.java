package com.plshapkin.barcodescanner.data.pojo;

import java.io.Serializable;

public class Barcode implements Serializable {

	private String text = "";
	private int statusCode;

	//region getters
	public String getText() {
		return text;
	}

	public int getStatusCode() {
		return statusCode;
	}
	//endregion

	//region setters
	public void setText(String text) {
		this.text = text;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	//endregion

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Barcode && ((Barcode) obj).getText().equals(this.getText());
	}
}
