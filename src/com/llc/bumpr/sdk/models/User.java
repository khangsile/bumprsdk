package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

/**
 * A User class that represents a User on the Bumpr network.
 * @author KhangSiLe
 * @version 0.1
 */
public class User implements Parcelable {
	/** The user's id in the database */
	protected int id;
	/** The user's first name */
	protected String firstName;
	/** The user's last name */
	protected String lastName;
	/** The user's current city (that they are living in) */
	protected String city;
	/** The user's current state (that they are living in) */
	protected String state;
	/** The user's email */
	protected String email;
	/** The link the the user's profile image */
	protected String profileImage;
	/** The description (provided by the user) of the user */
	protected String description;
	/** The user's phone number */
	protected String phoneNumber;
	/** The user's driver profile */
	protected Driver driverProfile;
	/** A List of Request objects that represent the requests that the user has sent out */
	protected List<Request> sentRequests = new ArrayList<Request>();
	/** A Singleton that represents the current user (on the device). */
	private static User activeUser = null;
	
	/************************** STATIC ************************/
	
	/**
	 * A Static method that gets the active user (one who is using the device)
	 * @return a User object indicating the active user
	 */
	public static User getActiveUser() {
		return activeUser;
	}
	
	/**
	 * A Static method to set the active user (one who is using the device)
	 * @param user The user object that represents the active user
	 */
	public static void setActiveUser(User user) {
		activeUser = user;
	}
			
	public static ApiRequest getUser(final int id, final Callback<User> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				// TODO Auto-generated method stub
				BumprAPI api = BumprClient.api();
				api.get(id, cb);
			}

			@Override
			public boolean needsAuth() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
	}
	
	/************* INSTANCE ***************/
	
	protected User() {
		
	}
	
	public User(Parcel source) {
		readFromParcel(source);
	}
	
	/**
	 * Updates a user
	 * @param user a User object with the changes to the user. This object should be built from the 
	 * original user that you are attempting to update.
	 * @param cb a Callback that returns the updated User object from the database.
	 */
	public ApiRequest getUpdateRequest(final HashMap<String, Object> user, final Callback<User> cb) {
		
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				// TODO Auto-generated method stub
				BumprAPI api = BumprClient.api();
				api.update(authToken,id, user, new Callback<User>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub
						cb.failure(arg0);
					}

					@Override
					public void success(User arg0, Response arg1) {
						// TODO Auto-generated method stub
						update(arg0);
						cb.success(arg0, arg1);
					}		
				});
			}

			@Override
			public boolean needsAuth() {
				// TODO Auto-generated method stub
				return true;
			}
		};
	}
	
	/**
	 * Sends a request from the user to the driver
	 * @param request
	 * @return 
	 */
	public ApiRequest getDriverRequest(final Request request, final Callback<Request> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				// TODO Auto-generated method stub
				BumprAPI api = BumprClient.api();
				api.request(authToken, request, cb);
			}

			@Override
			public boolean needsAuth() {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
	}
	
	/*************************** SETTERS **************************/
	
	public void update(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.city = user.getCity();
		this.state = user.getState();
		this.email = user.getEmail();
		this.profileImage = user.getProfileImage();
		this.description = user.getDescription();
		this.phoneNumber = user.getPhoneNumber();
	}
	
	public void addRequest(Request request) {
		if (sentRequests == null) sentRequests = new ArrayList<Request>();
		sentRequests.add(request);
	}
			
	/**************************** GETTERS *************************/
	
	/**
	 * Returns the users id
	 * @return an integer indicating the id of the user
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the first name of the user
	 * @return a string that indicates the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns the last name of the user
	 * @return a string that indicates the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns the user's email
	 * @return a string indicating the user's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns the user's phone number
	 * @return a string indicating the user's phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Returns the city that the user is from
	 * @return a string indicating the city of the user
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Returns the state that the user is from
	 * @return a string indicating the state of the user
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Returns the user's description
	 * @return a string of the user's description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the user's profile image
	 * @return a string that indicates the link to the user's profile image
	 */
	public String getProfileImage() {
		return profileImage;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Request> getSentRequests() {
		return new ArrayList<Request>(sentRequests);
	}
	
	/**
	 * 
	 */
	public Driver getDriverProfile() {
		return driverProfile;
	}
		
	/****************************** BUILDER ************************************/
	
	public static class Builder<T extends User> {
		private final T item;
		
		public Builder(T item) { this.item = item; }
		public Builder<T> setFirstName(String firstName) { item.firstName = firstName; return this; }
		public Builder<T> setLastName(String lastName) { item.lastName = lastName; return this; }
		public Builder<T> setCity(String city) { item.city = city; return this; }
		public Builder<T> setState(String state) { item.state = state; return this; }
		public Builder<T> setEmail(String email) { item.email = email; return this; }
		public Builder<T> setProfileImage(String profileImage) { item.profileImage = profileImage; return this; }
		public Builder<T> setDescription(String description) { item.description = description; return this; }
		public Builder<T> setPhoneNumber(String phoneNumber) { item.phoneNumber = phoneNumber; return this; }
		public T build() { return item; }
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
		dest.writeInt(id);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(city);
		dest.writeString(state);
		dest.writeString(email);
		dest.writeString(profileImage);
		dest.writeString(description);
		dest.writeString(phoneNumber);
		dest.writeList(sentRequests);
		dest.writeParcelable(driverProfile, 0);
	}
	
	public void readFromParcel(Parcel source) {
		id = source.readInt();
		firstName = source.readString();
		lastName = source.readString();
		city = source.readString();
		state = source.readString();
		email = source.readString();
		profileImage = source.readString();
		description = source.readString();
		phoneNumber = source.readString();
		source.readList(sentRequests, Request.class.getClassLoader());
		driverProfile = (Driver) source.readParcelable(Driver.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
		
	};
}

