package com.llc.bumpr.sdk.models;

public class Registration extends User {
	
	private String password;
	private String passwordConfirmation;

	public Registration(Builder builder) {
		super(builder);
		this.password = builder.password;
		this.passwordConfirmation = builder.passwordConfirmation;
	}
	
	public class Builder extends User.Builder {
		
		private String password;
		private String passwordConfirmation;
		
		public Builder setPassword(String password) { this.password = password; return this; }
		public Builder setPasswordConfirmation(String passwordConfirmation) 
		{ this.passwordConfirmation = passwordConfirmation; return this; }
		
		public Registration build() {
			return new Registration(this);
		}
	}

}
