package com.llc.bumpr.sdk.models;

public class User {
	
	private int id;
	
	private String firstname;
	private String lastname;
	private String city;
	private String state;
	private String email;
	private String profileImage;
	private String description;
	private String phoneNumber;
	
	public User(Builder builder) {
		this.firstname = builder.firstname;
		this.lastname = builder.lastname;
		this.city  = builder.city;
		this.state = builder.state;
		this.email = builder.email;
		this.profileImage = builder.profileImage;
		this.description = builder.description;
		this.phoneNumber = builder.phoneNumber;
	}
	
	/*************************** API METHODS **********************/
	
	/**
	 * Sends a request from the user
	 * @param request
	 */
	public void request(Request request) {
		
	}
			
	/**************************** GETTERS *************************/
	
	/**
	 * Returns the first name of the user
	 * @return a string that indicates the first name
	 */
	public String getFirstName() {
		return firstname;
	}
	
	/**
	 * Returns the last name of the user
	 * @return a string that indicates the last name
	 */
	public String getLastName() {
		return lastname;
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
	
	/****************************** BUILDER ************************************/
	
	public static class Builder {
		
		private String firstname;
		private String lastname;
		private String city;
		private String state;
		private String email;
		private String profileImage;
		private String description;
		private String phoneNumber;
		
		public Builder setFirstname(String firstname) { this.firstname = firstname; return this; }
		public Builder setLastname(String lastname) { this.lastname = lastname; return this; }
		public Builder setCity(String city) { this.city = city; return this; }
		public Builder setState(String state) { this.state = state; return this; }
		public Builder setEmail(String email) { this.email = email; return this; }
		public Builder setProfileImage(String profileImage) { this.profileImage = profileImage; return this; }
		public Builder setDescription(String description) { this.description = description; return this; }
		public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
		
		public User build() throws Exception {
			if (firstname == null || lastname == null || email == null) {
				throw new Exception("Invalid User state: missing params");
			}
			
			return new User(this);
		}
		
	}
	
}
