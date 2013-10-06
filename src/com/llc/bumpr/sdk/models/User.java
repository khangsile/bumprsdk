package com.llc.bumpr.sdk.models;

public class User {
	
	private int id;
	
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String email;
	private String profileImage;
	private String description;
	private String phoneNumber;
			
	/**************************** GETTERS *************************/
	
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
	
}
