package com.llc.bumpr.sdk.models;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.interfaces.Users;
import com.llc.bumpr.sdk.lib.BumprClient;

public class ActiveSession extends Session {

	private User user;
	private String authToken;
	
	public void logout(final Callback<InactiveSession> cb) {
		BumprAPI api = BumprClient.api();
		api.logout(authToken, new Callback<InactiveSession>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(InactiveSession session, Response response) {
				// TODO Auto-generated method stub
				Session.setSession(session);
				cb.success(session, response);
			}
			
		});
	}
	
	/**
	 * Sends a request to the server
	 * @param request The request to the server
	 * @param cb A Callback for when the response body is returned 
	 */
	public void request(Request request, final Callback<Request> cb) {
		BumprAPI sessions = BumprClient.api();
		sessions.request(authToken, request, cb);
	}
	
	/**
	 * Updates a user
	 * @param user a User object with the changes to the user. This object should be built from the 
	 * original user that you are attempting to update.
	 * @param cb a Callback that returns the updated User object from the database.
	 */
	public void update(final User user, final Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("auth_token", authToken);
		api.update(user.getId(), map, new Callback<User>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(User user, Response response) {
				if (user != null) {
					Session session = Session.getSession();
					if (session.getClass() == ActiveSession.class) {
						((ActiveSession) session).getUser().update(user); 
					}
				}
				cb.success(user, response);
			}
		});
	}
	
	/*********************** Driver Method's ***********************/
	
	public boolean respondTo(Request request, Callback<String> cb) {
		if (!isDriver()) return false;
		
		BumprAPI api = BumprClient.api();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("request_id", new Integer(request.getId()));
		map.put("response", new Boolean(request.getAccepted()));
		api.respondTo(authToken, map, cb);
		
		return true;
	}
	
	/*********************** GETTERS *******************************/
	
	/**
	 * Get the active user
	 * @return a User object representing the active user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Get the authentication token
	 * @return a String representing the authentication token of the user
	 */
	public String getAuthToken() {
		return authToken;
	}
	
	/*********************** Private Methods ***********************/

	private boolean isDriver() {
		return (user instanceof Driver);
	}
}
