package com.llc.bumpr.sdk.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Login {
	public String email;
	public String password;
	public String platform;
	
	@SerializedName("registration_id")
	public String registrationId;
	
	@SerializedName("access_token")
	public String accessToken;
	
	private Login(Builder builder) {
		this.email = builder.email;
		this.password = builder.password;
		this.registrationId = builder.registrationId;
		this.platform = builder.platform;
		this.accessToken = builder.accessToken;
	}
	
	public JsonObject toJson() {
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(this);
		
		return jsonElement.getAsJsonObject();
	}
	
	public static class Builder {
		private String email;
		private String password;
		private String registrationId;
		private String platform;
		private String accessToken;
		
		public Builder() {}
		public Builder setEmail(String email) { this.email = email; return this; }
		public Builder setPassword(String password) { this.password = password; return this; }
		public Builder setRegistrationId(String registrationId) { this.registrationId = registrationId; return this; }
		public Builder setPlatform(String platform) { this.platform = platform; return this; }
		public Builder setAccessToken(String accessToken) { this.accessToken = accessToken; return this; }
		
		public Login build() { return new Login(this); }
	}
}
