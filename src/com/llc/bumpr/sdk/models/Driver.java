package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.llc.bumpr.sdk.lib.Location;

/**
 * A Driver class that represents the driver's profile.
 * @author KhangSiLe
 * @version 0.1
 */
public class Driver implements Parcelable {

	/** The driver id (linked to the driver table) */
	private int id;
	/** The driver's balance (money) */
	private double balance;
	/** The driver license number */
	private String licenseId;
	/** The driver insurance number */
	private String insuranceId;
	/** The fee of the driver */
	private double fee;
	/** The driver's number of seats */
	private int seats;
	/** The driver's rating */
	private double rating;
	/** A boolean indicating if the driver is a cab */
	private boolean cab;
	/** A boolean if the driver has been authorized by us. */
	private boolean trustworthy;
	/** A boolean if the driver is currently available to drive. */
	private boolean active;
	/** A coordinate that represents the position of the driver */
	private Location position;
	/** A List of Request objects that represents the requests to the driver */
	private List<Request> requests = new ArrayList<Request>();
	
	/********************************** STATIC *****************************/
	
	
	/********************************** INSTANCE ***************************/
	
	/**
	 * Driver default constructor
	 */
	public Driver() {	
	}
	
	/**
	 * A constructor for implementing the Driver as a Parcelable.
	 * @param source The source Parcel
	 */
	public Driver(Parcel source) {
		id = source.readInt();
		fee = source.readDouble();
		licenseId = source.readString();
		insuranceId = source.readString();
		balance = source.readDouble();
		active = source.readByte() != 0;
		source.readList(requests, Request.class.getClassLoader());
	}
	
	/**
	 * Constructor to create Driver from JSON
	 * @param json The json representation of the driver 
	 * @throws JSONException exception thrown from invalid json representation
	 */
	public Driver(JSONObject json) throws JSONException {
		id = json.getInt("id");
		fee = json.getDouble("fee");
		String sRating = json.getString("rating");
		if (sRating.equals("null")) rating = 0;
		else rating = Double.parseDouble(sRating);
		try {
			position = new Location(json.getDouble("lat"), json.getDouble("lon"));
		}catch(JSONException e){
			Log.i("com.llc.bumpr.sdk", "Lat/Long not passed to driver");
		}
	}
	
	/****************************** API ***********************************/
	
	/**
	 * Update the driver's location
	 * @param coordinate A Coordinate object 
	 * @return an ApiRequest object which can be sent to the session object to be executed
	 */
	public ApiRequest updateLocation(final Location coordinate, final Callback<Response> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				api.updateLocation(authToken, id, coordinate, cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}	
		};
	}
	
	/**
	 * Respond to the given request.
	 * @param request The request that the driver is responding to.
	 * @param cb The Callback method that needs to be implemented when response is returned from the server
	 * @return an ApiRequest object which can be sent to the session object to be executed.
	 */
	public ApiRequest respondToRequest(final Request request, final Callback<Response> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("accepted", new Boolean(request.getAccepted()));
				api.respondTo(authToken, id, request.getId(), map, cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
		};
	}
	
	/**
	 * Get request to update driver
	 */
	public ApiRequest getUpdateRequest(final HashMap<String, Object> driver, final Callback<Driver> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				api.updateDriver(authToken,id, driver, new Callback<Driver>() {

					@Override
					public void failure(RetrofitError arg0) {
						cb.failure(arg0);
					}

					@Override
					public void success(Driver arg0, Response arg1) {
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
	 * Sets the driver's status if he is available to drive or not.
	 * @param status a boolean indicating the mode that the driver would like to switch to
	 */
	public ApiRequest toggleStatusRequest(final boolean status, final Callback<Driver> cb) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("active", (Boolean) status);
		return getUpdateRequest(map, cb);
	}
	
	/****************************** SETTERS *******************************/
	
	/**
	 * Adds a request to the requests list of the driver.
	 * @param request a Request object to add to the requests list
	 */
	public void addRequest(Request request) {
		if (requests == null) requests = new ArrayList<Request>();
		requests.add(request);
	}
	
	/******************************* GETTERS *******************************/
	
	/**
	 * @return the id of the driver
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the drivers balance. This represents the amount they have made off of driving users. 
	 * @return the balance of the driver
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Returns the drivers license id number. This verifies that they have a true license.
	 * @return the license id number of the driver
	 */
	public String getLicenseId() {
		return licenseId;
	}
	
	/**
	 * Returns the drivers insurance id number. This verifies that they have insurance for their car.
	 * @return the insurance id number of the driver
	 */
	public String getInsuranceId() {
		return insuranceId;
	}
	
	/**
	 * Returns the status of the driver. True represents they are driving. False otherwise.
	 * @return a boolean representing the status of the driver
	 */
	public boolean getStatus() {
		return active;
	}
	
	/**
	 * @return the fee (double) of the driver per mile
	 */
	public double getFee() {
		return fee;
	}
	
	/**
	 * @return the rating (double) of the driver
	 */
	public double getRating() {
		return rating;
	}
	
	/**
	 * @return the Coordinate location of the driver
	 */
	public Location getPosition() {
		return position;
	}
	
	/**
	 * Returns the requests sent to the driver.
	 * @return an List of the drivers requests
	 */
	public List<Request> getRequests() {
		return new ArrayList<Request>(requests);
	}
	
	/************************ PRIVATE ******************************/
	
	/**
	 * Update a driver from another driver.
	 * @param user the Driver from which the data is transferred over
	 */
	private void update(Driver driver) {
		if (driver == null) {
			throw new IllegalArgumentException("Object (Driver) is null");
		}
		
		this.id = driver.getId();
		this.balance = driver.getBalance();
		this.fee = driver.getFee();
		this.licenseId = driver.getLicenseId();
		this.active = driver.getStatus();
	}
	
	/************************ BUILDER *****************************/
	
	public static final class Builder<T extends Driver> {
		
		private T item;
				
		public Builder(T item) { this.item = item; }
		public Builder<T> setId(int Id) { item.id = Id; return this; }
		public Builder<T> setFee(double fee) { item.fee = fee; return this; } 
		public Builder<T> setLicenseId(String licenseId) { item.licenseId = licenseId; return this; }
		public Builder<T> setInsuranceId(String insuranceId) { item.insuranceId = insuranceId; return this; }
		public T build() { return item; }
	}

	/********************** PARCELABLE *****************************/
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeDouble(fee);
		dest.writeString(licenseId);
		dest.writeString(insuranceId);
		dest.writeDouble(balance);
		dest.writeByte((byte) (active ? 1 : 0));
		dest.writeList(requests);
	}
	
	public static final Parcelable.Creator<Driver> CREATOR = new Parcelable.Creator<Driver>() {
		@Override
		public Driver createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Driver(source);
		}

		@Override
		public Driver[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Driver[size];
		}
		
	};
	
}
