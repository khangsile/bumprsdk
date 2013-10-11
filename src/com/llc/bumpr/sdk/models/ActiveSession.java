package com.llc.bumpr.sdk.models;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.Sessions;
import com.llc.bumpr.sdk.interfaces.Users;
import com.llc.bumpr.sdk.lib.BumprClient;

public class ActiveSession extends Session {

	private User user;
	private String authToken;
	
	public void logout(final Callback<InactiveSession> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.logout(authToken, new Callback<InactiveSession>() {

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

	public void request(Request request, final Callback<Request> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.request(authToken, request, cb);
	}
	
	/**
	 * Takes a User object and 
	 * @param user
	 * @param cb
	 */
	public void update(final User user, final Callback<User> cb) {
		Users users = BumprClient.users();
		users.update(authToken, user, user.getId(), new Callback<Boolean>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(Boolean success, Response response) {
				if (user != null) {
					Session session = Session.getSession();
					if (session.getClass() == ActiveSession.class) {
						((ActiveSession) session).updateUser(user); 
					}
				}
				cb.success(user, response);
			}
			
		});
	}
	
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
	
	private void updateUser(User user) {
		this.user = user;
	}
}
