package com.llc.bumpr.sdk.models;

import java.util.List;

import retrofit.Callback;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.interfaces.Users;
import com.llc.bumpr.sdk.lib.BumprClient;


abstract public class Session {
	
	private static Session activeSession = new InactiveSession();
	
	/**
	 * Get the active user session
	 * @return a session object representing the active session 
	 */
	public static Session getSession() {
		return activeSession;
	}
	
	public static Session setSession(Session session) {
		activeSession = session;
		return session;
	}
	
	/********************* CLASS METHODS **********************/
	
	public void getUser(int id, Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		api.get(id, cb);
	}
	
	public void searchDrivers(SearchQuery query, Callback<List<Driver>> cb) {
		BumprAPI api = BumprClient.api();
		api.searchDrivers(query, cb);
	}
}
