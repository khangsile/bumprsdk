package com.llc.bumpr.sdk.lib;

public interface ApiRequest {
	
	public void execute(String authToken);
	
	public boolean needsAuth();
}
