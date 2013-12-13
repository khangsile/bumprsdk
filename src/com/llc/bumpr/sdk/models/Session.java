package com.llc.bumpr.sdk.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.ion.Ion;
import com.llc.bumpr.sdk.lib.ApiRequest;

public class Session {
	
	private String authToken;
	public static String baseURL = "http://ec2-54-196-170-148.compute-1.amazonaws.com/api/v1";
	
	
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
				request.execute(baseURL, authToken);
			else 
				return false;
		} else {
			request.execute(baseURL, "");
		}
		
		return true;
	}
	
	public void logout(Context context, final FutureCallback<String> cb) {
		Ion.with(context).load("DELETE", baseURL + "/sessions.json")
			.addHeader("X-AUTH-TOKEN", authToken)
			.setHeader("Content-Length", "0")
			.asString()
			.setCallback(cb);
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
	public void register(Context context, Registration registration, final FutureCallback<User> cb) {	
		Log.i("Registration",registration.toJson().toString());
		
		
		Ion.with(context).load("POST", baseURL + "/users.json")
			.setJsonObjectBody(registration.toJson())
			//.setStringBody(registration.toJson().toString())
			.as(new TypeToken<LoginResponse>(){})
			.setCallback(getLoginCallback(cb));
	}
	
	/**
	 * method which logs a User in to the Bumpr network
	 * @param email the email of the user
	 * @param password the password of the user
	 * @param gcmRegistrationId the GCM Registration Id for the phone
	 * @param cb a callback method for implementation of failure and success. Note this callback returns 
	 * the ActiveSession in the event that the login is successful. 
	 */
	public void login(Context context, Login login, final FutureCallback<User> cb) {
		Ion.with(context).load("POST", baseURL + "/sessions.json")
			/*.setBodyParameter("email", login.email)
			.setBodyParameter("password", login.password)
			.setBodyParameter("registration_id", login.registrationId)
			.setBodyParameter("platform", login.platform)*/
			.setJsonObjectBody(login)
			.as(new TypeToken<LoginResponse>() {})
			.setCallback(getLoginCallback(cb));
	}
	
	public void loginWithFacebook(Context context, Login login, final FutureCallback<User> cb) {
		Log.i("method", AsyncHttpPost.METHOD);
		Ion.with(context).load("POST", baseURL + "/facebook_login.json")
			.addHeader("OAUTH", login.accessToken)
			.setBodyParameter("platform", login.platform)
			.setBodyParameter("registration_id", login.registrationId)
			.as(new TypeToken<LoginResponse>() {})
			.setCallback(getLoginCallback(cb));
	}
	
	/*********************** GETTERS *****************************/
	
	/**
	 * Get the authentication token
	 * @return a String representing the authentication token of the user
	 */
	public String getAuthToken() {
		return authToken;
	}
	
	/************************ HELPER *******************************/
	
	private FutureCallback<LoginResponse> getLoginCallback(final FutureCallback<User> cb) {
		return new FutureCallback<LoginResponse>() {
			
			@Override
			public void onCompleted(Exception arg0, LoginResponse login) {
				User user = null;
				if (arg0 == null) {
					user = login.getUser();
					User.setActiveUser(user);
					authToken = login.getAuthToken();
				} else {
					arg0.printStackTrace();
				}
				
				if (user == null) {
					arg0 = new Exception("User cannot be null");
				}
				
				cb.onCompleted(arg0, user);
			}
		};
	}
	
}
