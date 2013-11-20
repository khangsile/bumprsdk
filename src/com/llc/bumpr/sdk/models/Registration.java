package com.llc.bumpr.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Registration extends User {
	
	private String password;
	private String passwordConfirmation;
	private String platform = "android";
	private String registrationId;
	
	public Registration() {
		super();
	}
	
	public Registration(Parcel source) {
		super(source);
		password = source.readString();
		passwordConfirmation = source.readString();
		registrationId = source.readString();
	}

	public static final class Builder extends User.Builder<Registration> {
	
		private String password;
		private String passwordConfirmation;
		private String registrationId;
	
		public Builder() { super(new Registration()); } 
		public Builder setPassword(String password) { build().password = password; return this; }
		public Builder setPasswordConfirmation(String passwordConfirmation) { build().passwordConfirmation = passwordConfirmation; return this; }
		public Builder setRegistrationId(String registrationId) { build().registrationId = registrationId; return this; }
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
		super.writeToParcel(dest, flags);
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
