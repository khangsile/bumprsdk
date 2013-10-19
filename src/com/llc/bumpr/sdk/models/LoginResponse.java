package com.llc.bumpr.sdk.models;

public class LoginResponse {
	private String authToken;
	private User user;
	
	public String getAuthToken() {
		return authToken;
	}
	
	public User getUser() {
		return user;
	}
}
