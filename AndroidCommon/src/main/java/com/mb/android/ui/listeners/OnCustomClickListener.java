package com.mb.android.ui.listeners;

import android.view.View;

public interface OnCustomClickListener<T> {
	public void OnTouch(View aView, int position, T payload);

	public void OnClick(View aView, int position, T payload);

	public void OnLongClick(View aView, int position, T payload);

}
