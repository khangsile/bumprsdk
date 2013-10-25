package com.llc.bumpr.sdk.models;

import java.util.HashMap;

import retrofit.Callback;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.BumprClient;

public class ActiveSession extends Session {

	private String authToken;
	
	/*********************** Driver Method's ***********************/
	
	public boolean respondTo(Request request, Callback<String> cb) {
		
		BumprAPI api = BumprClient.api();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("accepted", new Boolean(request.getAccepted()));
		api.respondTo(authToken, request.getId(), map, cb);
		
		return true;
	}
	
	/**
	 * Get the authentication token
	 * @return a String representing the authentication token of the user
	 */
	public String getAuthToken() {
		return authToken;
	}
	
	/*********************** Private Methods ***********************/
}
