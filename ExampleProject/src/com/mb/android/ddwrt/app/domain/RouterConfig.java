package com.mb.android.ddwrt.app.domain;

import com.mb.android.preferences.annotations.ConfigDescription;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.annotations.PreferenceType;
import com.mb.android.preferences.domain.Config;

public class RouterConfig extends Config {

	public static final String ID = "routeConfig";
	
	public RouterConfig() {
		super.setId(ID);
	}

	@ConfigMetadata(id = "ipAddress", required = true, type = PreferenceType.String)
	@ConfigDescription(title = "IP Address", description = "IP Address (or hostname) of your router")
	private String ipAddress = "";

	@ConfigMetadata(id = "username", required = true, type = PreferenceType.String)
	@ConfigDescription(title = "Username", description = "Username to access your router")
	private String username = "";
	
	@ConfigMetadata(id = "password", required = true, type = PreferenceType.String)
	@ConfigDescription(title = "Password", description = "Password to access your router")
	private String password = "";
	
	@ConfigMetadata(id = "useSSL", required = true, type = PreferenceType.Boolean)
	@ConfigDescription(title = "Use SSL", description = "")
	private Boolean useSSL = false;

	@ConfigMetadata(id = "allowInvalidSSL", required = true, type = PreferenceType.Boolean)
	@ConfigDescription(title = "Allow Invalid SSL", description = "Accept invalid SSL certificates (ie: Self Signed)")
	private Boolean allowInvalidSSL = true;

	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Boolean shouldUseSSL() {
		return useSSL;
	}

	public Boolean shouldAllowInvalidSSL() {
		return allowInvalidSSL;
	}

	public String getName() {
		return "General Configuration";
	}

}
