package com.llc.bumpr.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class Registration implements Parcelable {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String passwordConfirmation;
	private String platform = "android";
	private String registrationId;
	
	public Registration() {
	}
	
	public Registration(Parcel source) {
		firstName = source.readString();
		lastName = source.readString();
		email = source.readString();
		password = source.readString();
		passwordConfirmation = source.readString();
		registrationId = source.readString();
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		
		json.addProperty("first_name", firstName);
		json.addProperty("last_name", lastName);
		json.addProperty("password", password);
		json.addProperty("password_confirmation", passwordConfirmation);
		json.addProperty("email", email);
		json.addProperty("registration_id", registrationId);
		json.addProperty("platform", platform);
		
		return json;
	}
	
	/***************************** GETTERS ****************************/
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	
	public String getRegistrationId() {
		return registrationId;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	/*********************** BUILDER **********************************/

	public static final class Builder {
	
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private String passwordConfirmation;
		private String registrationId;
		private Registration item;
	
		public Builder(Registration r) { this.item = r; } 
		public Builder setFirstName(String firstName) { item.firstName = firstName; return this; }
		public Builder setLastName(String lastName) { item.lastName = lastName; return this; }
		public Builder setEmail(String email) { item.email = email; return this; }
		public Builder setPassword(String password) { item.password = password; return this; }
		public Builder setPasswordConfirmation(String passwordConfirmation) { item.passwordConfirmation = passwordConfirmation; return this; }
		public Builder setRegistrationId(String registrationId) { item.registrationId = registrationId; return this; }
		
		public Registration build() {
			return item;
		}
	}
	
/*********************************** PARCELABLE *************************/
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(email);
		dest.writeString(password);
		dest.writeString(passwordConfirmation);
		dest.writeString(registrationId);
	}
	
	public static final Parcelable.Creator<Registration> CREATOR = new Parcelable.Creator<Registration>() {
		@Override
		public Registration createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Registration(source);
		}

		@Override
		public Registration[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Registration[size];
		}
	};
}
