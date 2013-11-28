package com.llc.bumpr.sdk.lib;

public interface ApiRequest {
	
	public void execute(String baseURL, String authToken);
	
	public boolean needsAuth();
}
