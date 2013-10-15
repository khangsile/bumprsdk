package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

public class User {
	
	protected int id;
	
	protected String firstName;
	protected String lastName;
	protected String city;
	protected String state;
	protected String email;
	protected String profileImage;
	protected String description;
	protected String phoneNumber;
	protected Driver driverProfile;
	
	protected List<Request> sentRequests = new ArrayList<Request>();
	
	private static User activeUser = null;
	
	/************************** STATIC ************************/
	
	public static User getActiveUser() {
		return activeUser;
	}
	
	public static void setActiveUser(User user) {
		activeUser = user;
	}
	
	/*************************** INSTANCE *************************/
	
	public Builder<User> getBuilder() {
		return new Builder<User>(this);
	}
	
	/*************************** API METHODS **********************/
	
	/************* STATIC ***************/
	
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
	
	/**
	 * Updates a user
	 * @param user a User object with the changes to the user. This object should be built from the 
	 * original user that you are attempting to update.
	 * @param cb a Callback that returns the updated User object from the database.
	 */
	public ApiRequest getUpdateRequest(final User user, final Callback<User> cb) {
		
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				// TODO Auto-generated method stub
				BumprAPI api = BumprClient.api();
				api.update(id, user, new Callback<User>() {

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
	
	public List<Request> getSentRequests() {
		return new ArrayList<Request>(sentRequests);
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
}

