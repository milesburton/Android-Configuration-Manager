package com.mb.android.preferences.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mb.android.preferences.R;
import com.mb.android.preferences.domain.ConfigCategory;
import com.mb.android.ui.listeners.CustomClickListener;
import com.mb.android.ui.listeners.OnCustomClickListener;

public abstract class PreferenceLauncherActivity extends FragmentActivity implements OnCustomClickListener<ConfigCategory> {

	private List<ConfigCategory> categoryList = new ArrayList<ConfigCategory>();

	protected void addCategoryList(ConfigCategory category) {
		this.categoryList.add(category);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SetupCategoriesAdapter adapter = new SetupCategoriesAdapter();
		setContentView(R.layout.activity_list);

		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
	}

	private class SetupCategoriesAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public SetupCategoriesAdapter() {
			mInflater = LayoutInflater.from(PreferenceLauncherActivity.this);
		}

		public int getCount() {
			return categoryList.size();
		}

		public Object getItem(int arg0) {
			return categoryList.get(arg0);
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview_row_icon_title_summary, null);
				convertView.setOnCreateContextMenuListener(PreferenceLauncherActivity.this);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.summary = (TextView) convertView.findViewById(R.id.summary);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ConfigCategory category = (ConfigCategory) getItem(position);

			CustomClickListener<ConfigCategory> handler = new CustomClickListener<ConfigCategory>(PreferenceLauncherActivity.this, position, category);

			holder.icon.setImageResource(category.getDrawble());
			holder.title.setText(category.getTitle());
			holder.summary.setText(category.getSummary());
			convertView.setOnClickListener(handler);
			return convertView;
		}

	}

	private static class ViewHolder {
		private ImageView icon;
		private TextView title;
		private TextView summary;
	}

	public void OnClick(View aView, int position, ConfigCategory payload) {
		if (payload == null)
			return;

		Intent intent = payload.getIntent();
		startActivity(intent);
	}

	public void OnLongClick(View aView, int position, ConfigCategory payload) {

	}

	public void OnTouch(View aView, int position, ConfigCategory payload) {

	}

}
