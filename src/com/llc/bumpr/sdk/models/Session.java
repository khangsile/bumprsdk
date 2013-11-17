package com.llc.bumpr.sdk.models;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

public class Session {
	
	private String authToken;
	
	private static Session activeSession = new Session();
	
	/**
	 * Temp for testing
	 */
	public void setAuthToken(String token) {
		this.authToken = token;
	}
	
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
	
	/**
	 * Generic method to send requests based on the ApiRequest interface.
	 * @param request an object of type ApiRequest which implements an execute method and a needsAuth method
	 * @return boolean indicating if the request was able to be sent. This will return false in the event that an ApiRequest
	 * requires an AuthToken when a session has not yet been instantiated.
	 */
	public boolean sendRequest(ApiRequest request) {
		if (request.needsAuth()) {
			if (authToken != null && !authToken.trim().equals(""))
				request.execute(authToken);
			else 
				return false;
		} else {
			request.execute("");
		}
		
		return true;
	}
	
	public void logout(final Callback<Response> cb) {
		BumprAPI api = BumprClient.api();
		api.logout(authToken, cb);
	}
	
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
	public void register(Registration registration, final Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		api.register(registration, new Callback<LoginResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(LoginResponse login, Response response) {
				// TODO Auto-generated method stub
				User.setActiveUser(login.getUser());
				authToken = login.getAuthToken();
				cb.success(login.getUser(), response);
			}
			
		});
	}
	
	/**
	 * method which logs a User in to the Bumpr network
	 * @param email the email of the user
	 * @param password the password of the user
	 * @param cb a callback method for implementation of failure and success. Note this callback returns 
	 * the ActiveSession in the event that the login is successful. 
	 */
	public void login(String email, String password, final Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		api.login(new Login(email, password), new Callback<LoginResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				cb.failure(arg0);
			}

			@Override
			public void success(LoginResponse login, Response response) {
				// TODO Auto-generated method stub
				User.setActiveUser(login.getUser());
				authToken = login.getAuthToken();
				cb.success(login.getUser(), response);
			}
			
		});
	}
	
	/**
	 * method which logs a User in to the Bumpr network
	 * @param email the email of the user
	 * @param password the password of the user
	 * @param gcmRegistrationId the GCM Registration Id for the phone
	 * @param cb a callback method for implementation of failure and success. Note this callback returns 
	 * the ActiveSession in the event that the login is successful. 
	 */
	public void login(String email, String password, String gcmRegistrationId, final Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		
		HashMap<String, Object> login = new HashMap<String, Object>();
		login.put("email", email);
		login.put("password", password);
		login.put("registration_id", gcmRegistrationId);
		login.put("platform", "android");
		
		api.login(login, new Callback<LoginResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(LoginResponse login, Response response) {
				User.setActiveUser(login.getUser());
				authToken = login.getAuthToken();
				cb.success(login.getUser(), response);
			}
		});		
	}
	
	/**
	 * @param login A HashMap that represents the login information to send to the server
	 * @param cb A callback for when the server responds
	 */
	public void login(HashMap<String, Object> login, final Callback<User> cb) {
		BumprAPI api = BumprClient.api();
		
		api.login(login, new Callback<LoginResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				cb.failure(arg0);
			}

			@Override
			public void success(LoginResponse login, Response arg1) {
				User.setActiveUser(login.getUser());
				authToken = login.getAuthToken();
				cb.success(login.getUser(), arg1);
			}
			
		});
	}
	
	/*********************** GETTERS *****************************/
	
	/**
	 * Get the authentication token
	 * @return a String representing the authentication token of the user
	 */
	public String getAuthToken() {
		return authToken;
	}
	
}
