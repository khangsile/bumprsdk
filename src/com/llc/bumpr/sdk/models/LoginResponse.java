package com.llc.bumpr.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Class that receives the Json response from the server during login or
 * registration. This class is solely used for the automatic mapping of the 
 * user and authentication token from the Json.
 * @author KhangSiLe
 *
 */
public class LoginResponse {
	
	/**
	 * The authentication token of the session
	 */
	@SerializedName("auth_token")
	private String authToken;
	
	/**
	 * The User object of the user who logged in
	 */
	private User user;
	
	public String getAuthToken() {
		return authToken;
	}
	
	public User getUser() {
		return user;
	}
}
