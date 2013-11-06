package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;
import com.llc.restrofit.ResponseConverter;

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
	
	private static User jsonToUser(JSONObject json) throws JSONException {
		User user = new User(json.getJSONObject("user"));
		Driver driver = new Driver(json);
		return new User.Builder<User>(user).setDriverProfile(driver).build();
	}
		
	/**
	 * A Static method to get information of a user
	 * @param id The id of the user to look up
	 * @param cb A Callback for on success or failure of the call
	 * @return an ApiRequest object to be sent to the Session.execute
	 */
	public static ApiRequest getUser(final int id, final Callback<User> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				BumprAPI api = BumprClient.api();
				api.getUser(id, cb);
			}

			@Override
			public boolean needsAuth() {
				return false;
			}
			
		};
	}
	
	public static ApiRequest searchDrivers(final SearchQuery query, final Callback<List<User>> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				BumprAPI api = BumprClient.api();
				api.searchDrivers(query.getTopRight().lat, query.getBottomLeft().lon, 
						query.getBottomLeft().lat, query.getTopRight().lon, query.getMinFee(), query.getMinSeats(),
						new Callback<Response>() {

							@Override
							public void failure(RetrofitError arg0) {
								cb.failure(arg0);
							}

							@Override
							public void success(Response arg0, Response arg1) {
								try {
									JSONArray array = ResponseConverter.responseToJSONArray(arg0);									
									ArrayList<User> list = new ArrayList<User>();
									Log.i("this", array.length() + "");
									if (array.length() < 1) {
										cb.success(list, arg1);
										return;
									}
									
									for(int i=0; i<array.length(); i++) {
										JSONObject object = array.getJSONObject(i);
										list.add(User.jsonToUser(object));
									}
									cb.success(list, arg1);
								} catch (Exception e) {
									e.printStackTrace();
									cb.failure(null);
								}
							}
				});
			}

			@Override
			public boolean needsAuth() {
				return false;
			}
			
		};
	}
	
	/************* INSTANCE ***************/
	
	/**
	 * Constructor (for Registration.Builder)
	 */
	public User() {	
	}
	
	/**
	 * Constructor for creating user from JSON
	 */
	public User(JSONObject user) throws JSONException{
		id = user.getInt("id");
		firstName = user.getString("first_name");
		lastName = user.getString("last_name");
	}
	
	/**
	 * Constructor for implementing User as a Parcelable
	 * @param source The parcel from which we can read the information
	 */
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
				BumprAPI api = BumprClient.api();
				api.updateUser(authToken,id, user, new Callback<User>() {

					@Override
					public void failure(RetrofitError arg0) {
						cb.failure(arg0);
					}

					@Override
					public void success(User arg0, Response arg1) {
						update(arg0);
						cb.success(arg0, arg1);
					}		
				});
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
		};
	}
	
	/**
	 * Register driver
	 */
	public ApiRequest getRegisterRequest(final Callback<Driver> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				BumprAPI api = BumprClient.api();
				api.registerDriver(authToken, new Callback<Driver>() {

					@Override
					public void failure(RetrofitError arg0) {
						cb.failure(arg0);
					}

					@Override
					public void success(Driver arg0, Response arg1) {
						setDriverProfile(arg0);
						cb.success(arg0, arg1);
					}
					
				});
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
			
		};
	}
	
	/*************************** SETTERS **************************/
	
	/**
	 * Update a user from another user.
	 * @param user the User from which the data is transferred over
	 */
	public void update(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Object (User) is null");
		}
		
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
	
	public void setDriverProfile(Driver driverProfile) {
		this.driverProfile = driverProfile;
	}
	
	/**
	 * Add a request to the user's list of sent requests
	 * @param request The request to be added to the list
	 */
	public void addRequest(Request request) {
		if (request == null) {
			throw new IllegalArgumentException("Object (Request) is null");
		}
		
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
	 * Return the users list of requests
	 * @return returns a copy of the list of sent requests
	 */
	public List<Request> getSentRequests() {
		return new ArrayList<Request>(sentRequests);
	}
	
	/**
	 * @return the user's driver profile information in the form of a driver object
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
		public Builder<T> setDriverProfile(Driver driver) { item.driverProfile = driver; return this; }
		public T build() { return item; }
	}

	/*********************************** PARCELABLE *************************/
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
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