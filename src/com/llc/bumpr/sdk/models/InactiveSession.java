package com.llc.bumpr.sdk.models;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.Sessions;
import com.llc.bumpr.sdk.lib.BumprClient;

public class InactiveSession extends Session {

/*************************** LOGIN/REGISTRATION ******************/
	
	/**
	 * Method which registers a User to the Bumpr network.
	 * 
	 * @param firstname The firstname of the user
	 * @param lastname The lastname of the user
	 * @param email The email address of the user
	 * @param password The password of the user
	 * @param passwordConfirmation The password confirmation of the user (This must match with password)
	 * @return A new Session once a successful registration has been returned from the server
	 */
	public void register(Registration registration, final Callback<ActiveSession> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.register(registration, new Callback<ActiveSession>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(ActiveSession session, Response response) {
				// TODO Auto-generated method stub
				Session.setSession(session);
				cb.success(session, response);
			}
			
		});
	}
	
	public ActiveSession register(Registration registration) {
		Sessions sessions = BumprClient.sessions();
		ActiveSession session = sessions.register(registration);
		Session.setSession(session);
		return session;
	}
	
	/**
	 * method which logs a User in to the Bumpr network
	 * @param email the email of the user
	 * @param password the password of the user
	 * @param cb a callback method for implementation of failure and success
	 * @return A User object once a login has been successful or nil if the failed
	 */
	public void login(String email, String password, final Callback<ActiveSession> cb) {
		Sessions sessions = BumprClient.sessions();
		sessions.login(new Login(email, password), new Callback<ActiveSession>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(ActiveSession session, Response response) {
				// TODO Auto-generated method stub
				Session.setSession(session);
				cb.success(session, response);
			}
			
		});
	}
	
	/**
	 * method which logs a User in to the Bumpr network. Must be run in a thread.
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return A User object once a login has been successful or nil if the failed
	 */
	public ActiveSession login(String email, String password) {
		Sessions sessions = BumprClient.sessions();
		ActiveSession session = sessions.login(new Login(email, password));
		Session.setSession(session);
		return session;
	}
}
