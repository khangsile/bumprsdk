package com.llc.bumpr.sdk.models;

public class Login {
	private String user_email;
	private String password;
	
	public Login(String user_email, String password) {
		this.user_email = user_email;
		this.password = password;
	}
}
