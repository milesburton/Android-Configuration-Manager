package com.mb.android.preferences.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mb.android.preferences.R;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.manager.ConfigFilter;
import com.mb.android.preferences.manager.ConfigManager;
import com.mb.android.preferences.manager.OnConfigLoadedListener;
import com.mb.android.ui.listeners.CustomClickListener;
import com.mb.android.ui.listeners.OnCustomClickListener;

public abstract class ConfigListActivity extends SherlockFragmentActivity implements OnCustomClickListener<Config>, OnClickListener {
	private final static int BTN_ADD_NEW = 1337;

	private final ConfigFilter filter;

	private List<Config> configList;

	private ConfigManager configManager;
	private Config selectedConfig = null;

	public ConfigListActivity(ConfigFilter filter, ConfigManager configManager) {
		this.filter = filter;
		this.configManager = configManager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		configList = getConfigList();

		ProviderListAdapter adapter = new ProviderListAdapter();
		setContentView(R.layout.activity_list);
		Button button = new Button(this);
		button.setId(BTN_ADD_NEW);
		button.setText("Add new");
		button.setOnClickListener(this);

		// Fetch view and associate handlers
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.addFooterView(button);
		lv.setAdapter(adapter);

		configManager.listenForConfigLoaded(adapter);
	}

	private List<Config> getConfigList() {
		return new ArrayList<Config>(configManager.getConfigurationFor(filter).values());
	}

	public void OnClick(View view, int position, Config providerConfig) {
		if (view.getId() == R.id.toggle_default_icon) {

			onDefaultSelected(providerConfig);
		} else {
			onConfigSelected(providerConfig);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		onConfigContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (selectedConfig != null)
			onConfigDeleted(selectedConfig);
		return true;
	}

	private class ProviderListAdapter extends BaseAdapter implements OnConfigLoadedListener {
		private LayoutInflater mInflater;

		public ProviderListAdapter() {
			mInflater = LayoutInflater.from(ConfigListActivity.this);
		}

		public int getCount() {
			return configList.size();
		}

		public Object getItem(int arg0) {
			return configList.get(arg0);
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview_row_preference_config, null);
				convertView.setOnCreateContextMenuListener(ConfigListActivity.this);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.toggleDefaultIcon = (ImageView) convertView.findViewById(R.id.toggle_default_icon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Config provider = (Config) getItem(position);

			CustomClickListener<Config> handler = new CustomClickListener<Config>(ConfigListActivity.this, position, provider);

			holder.title.setText(provider.getName());
			holder.toggleDefaultIcon.setOnClickListener(handler);

			if (isDefaultConfig(provider)) {
				holder.toggleDefaultIcon.setImageResource(android.R.drawable.star_on);
			} else {
				holder.toggleDefaultIcon.setImageResource(android.R.drawable.star_off);
			}

			convertView.setOnTouchListener(handler);
			convertView.setOnClickListener(handler);
			convertView.setOnCreateContextMenuListener(ConfigListActivity.this);

			return convertView;
		}

		public void configLoaded(Map<String, Config> configuration) {
			configList = getConfigList();
			notifyDataSetChanged();
		}

	}

	private static class ViewHolder {
		private TextView title;
		private ImageView toggleDefaultIcon;
	}

	public void OnTouch(View aView, int position, Config payload) {
		selectedConfig = payload;
	}

	public void OnLongClick(View aView, int position, Config payload) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case BTN_ADD_NEW:
			onAddConfig();
			break;
		}
	}

	public abstract void onAddConfig();

	public abstract boolean isDefaultConfig(Config config);

	public abstract void onDefaultSelected(Config config);

	public abstract void onConfigSelected(Config config);

	public abstract void onConfigDeleted(Config config);

	public abstract void onConfigContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo);

}
