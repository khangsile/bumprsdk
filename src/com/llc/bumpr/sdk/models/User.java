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
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
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
	@SerializedName("first_name")
	protected String firstName;
	
	/** The user's last name */
	@SerializedName("last_name")
	protected String lastName;
	
	/** The user's current city (that they are living in) */
	protected String city;
	
	/** The user's current state (that they are living in) */
	protected String state;
	
	/** The user's email */
	protected String email;
	
	/** The link the the user's profile image */
	@SerializedName("profile_pic")
	protected String profileImage;
	
	/** The description (provided by the user) of the user */
	protected String description;
	
	/** The user's phone number */
	@SerializedName("phone_number")
	protected String phoneNumber;
	
	/** A Singleton that represents the current user (on the device). */
	private static User activeUser = null;
	
	/************************** STATIC ************************/
	
	/**
	 * A Static method that creates a request to get the active user information
	 * @param cb The callback method once the user has been created
	 */
	public static ApiRequest getMeRequest(final Context context, final FutureCallback<User> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load("POST", baseURL + "/me.json")
					.addHeader("X-AUTH-TOKEN", authToken)
					.addHeader("Content-Length", "0")
					.as(new TypeToken<LoginResponse>() {})
					.setCallback(new FutureCallback<LoginResponse>() {

						@Override
						public void onCompleted(Exception arg0, LoginResponse login) {
							User user = null;
							if (arg0 == null) {
								activeUser = login.getUser();
								user = activeUser;
							}
							
							cb.onCompleted(arg0, user);
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
		return new User.Builder<User>(user).build();
	}
		
	/**
	 * A Static method to get information of a user
	 * @param id The id of the user to look up
	 * @param cb A Callback for on success or failure of the call
	 * @return an ApiRequest object to be sent to the Session.execute
	 */
	public static ApiRequest getUser(final Context context, final int id, final FutureCallback<User> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load(baseURL + "/users/" + id + ".json")
					.as(new TypeToken<User>() {})
					.setCallback(cb);
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
			public void execute(String baseURL, String authToken) {
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
	public ApiRequest getUpdateRequest(final Context context, final HashMap<String, Object> user, final FutureCallback<User> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {				
				Ion.with(context).load("PUT", baseURL + "/users/" + id + ".json")
					.addHeader("X-AUTH-TOKEN", authToken)
					.setJsonObjectBody(new JSONObject(user))
					.as(new TypeToken<User>() {})
					.setCallback(new FutureCallback<User>() {

						@Override
						public void onCompleted(Exception arg0, User arg1) {
							if (arg0 == null && arg1 != null) {
								update(arg1);
								cb.onCompleted(arg0, arg1);
							} else {
								arg0.printStackTrace();
							}
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
	public ApiRequest getRegisterRequest(final HashMap<String, Object> driver, final Callback<Driver> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				api.registerDriver(authToken, driver, new Callback<Driver>() {

					@Override
					public void failure(RetrofitError arg0) {
						cb.failure(arg0);
					}

					@Override
					public void success(Driver arg0, Response arg1) {
						try {
							//String json = BumprError.responseToString(arg1);
							String json = arg1.getBody().toString();
							Log.i("BUMPRAPI", json);
						} catch (Exception e) {
							e.printStackTrace();
						}
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
	
	/**
	 * Set the driver profile for the user
	 * @param driverProfile the driver profile of the user
	 */
	public void setDriverProfile(Driver driverProfile) {
		//Do nothing
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
	 * @return the user's driver profile information in the form of a driver object
	 */
	public Driver getDriverProfile() {
		return new Driver();
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