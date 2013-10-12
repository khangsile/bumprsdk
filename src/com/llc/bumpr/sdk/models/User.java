package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.List;

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
	
	protected List<Request> sentRequests = new ArrayList<Request>();
			
	public Builder<User> getBuilder() {
		return new Builder<User>(this);
	}
	
	/*************************** API METHODS **********************/
	
	/**
	 * Sends a request from the user
	 * @param request
	 */
	public void request(Request request) {
		
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
		
		protected String firstName;
		protected String lastName;
		protected String city;
		protected String state;
		protected String email;
		protected String profileImage;
		protected String description;
		protected String phoneNumber;
		
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

