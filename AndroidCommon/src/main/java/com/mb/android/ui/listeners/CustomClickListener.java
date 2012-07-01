package com.mb.android.ui.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class CustomClickListener<T> implements OnTouchListener,
		OnClickListener, OnLongClickListener {
	private int position;
	private OnCustomClickListener<T> callback;
	private T payload;

	public CustomClickListener(OnCustomClickListener<T> callback, int pos,
			T payload) {
		position = pos;
		this.callback = callback;
		this.payload = payload;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		callback.OnTouch(v, position, payload);
		return false; // Don't consume event
	}

	@Override
	public void onClick(View v) {
		callback.OnClick(v, position, payload);
	}

	@Override
	public boolean onLongClick(View v) {
		callback.OnLongClick(v, position, payload);
		return true;
	}

}
