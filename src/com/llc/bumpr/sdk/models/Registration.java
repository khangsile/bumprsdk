package com.llc.bumpr.sdk.models;

public class Registration extends User {
	
	private String password;
	private String passwordConfirmation;
	
	public static final class Builder extends User.Builder<Registration> {
	
		private String password;
		private String passwordConfirmation;
	
		public Builder() { super(new Registration()); } 
		public Builder setPassword(String password) { build().password = password; return this; }
		public Builder setPasswordConfirmation(String passwordConfirmation) { build().passwordConfirmation = passwordConfirmation; return this; }
		
	}

}
