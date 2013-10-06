package com.llc.bumpr.sdk.models;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.Sessions;
import com.llc.bumpr.sdk.lib.BumpRest;

public class Session {
	private static Session activeSession = null;
	
	private User user;
	private String accessToken;
	
	/**
	 * Get the active user session
	 * @return a session object representing the active session 
	 */
	public static Session getActiveSession() {
		return activeSession;
	}
	
/*************************** LOGIN/REGISTRATION ******************/
	
	/**
	 * Static method which registers a User to the Bumpr network.
	 * 
	 * @param firstname The firstname of the user
	 * @param lastname The lastname of the user
	 * @param email The email address of the user
	 * @param password The password of the user
	 * @param passwordConfirmation The password confirmation of the user (This must match with password)
	 * @return A new Session once a successful registration has been returned from the server
	 */
	public static void register(User user, String password, String passwordConfirmation, Callback<Session> cb) {
		Sessions sessions = BumpRest.sessions();
		sessions.register(user, password, passwordConfirmation, cb);
	}
	
	/**
	 * Static method which logs a User in to the Bumpr network
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return A User object once a login has been successful or nil if the failed
	 */
	public static void login(String email, String password, Callback<Session> cb) {
		Sessions sessions = BumpRest.sessions();
		sessions.login(email, password, cb);
	}
	
	public void update(final User user, final Callback<User> cb) {
		Sessions sessions = BumpRest.sessions();
		sessions.update(accessToken, user, new Callback<User>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(User user, Response response) {
				if (user != null)  Session.activeSession.user = user;
				cb.success(user, response);
			}
			
		});
	}
}
