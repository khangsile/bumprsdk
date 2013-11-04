package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;
import com.llc.bumpr.sdk.lib.Coordinate;

/**
 * A Driver class that represents the driver's profile.
 * @author KhangSiLe
 * @version 0.1
 */
public class Driver implements Parcelable {
	/** The driver's balance (money) */
	private double balance;
	/** The driver id (linked to the driver table) */
	private int id;
	/** The driver license number */
	private String licenseId;
	/** The driver insurance number */
	private String insuranceId;
	/** The fee of the driver */
	private double fee;
	/** The driver's number of seats */
	private int seats;
	/** A boolean indicating if the driver is a cab */
	private boolean cab;
	/** A boolean if the driver has been authorized by us. */
	private boolean trustworthy;
	/** A boolean if the driver is currently available to drive. */
	private boolean status;
	/** A coordinate that represents the position of the driver */
	private Coordinate position;
	/** A List of Request objects that represents the requests to the driver */
	private List<Request> requests = new ArrayList<Request>();
	
	/**
	 * A constructor for implementing the Driver as a Parcelable.
	 * @param source The source Parcel
	 */
	public Driver(Parcel source) {
		id = source.readInt();
		licenseId = source.readString();
		insuranceId = source.readString();
		balance = source.readDouble();
		status = source.readByte() != 0;
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
		position = new Coordinate(json.getDouble("lon"), json.getDouble("lat"));
	}
	
	/****************************** API ***********************************/
	
	/**
	 * Update the driver's location
	 * @param coordinate A Coordinate object 
	 * @return an ApiRequest object which can be sent to the session object to be executed
	 */
	public ApiRequest updateLocation(final Coordinate coordinate, final Callback<Response> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
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
			public void execute(String authToken) {
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("accepted", new Boolean(request.getAccepted()));
				api.respondTo(authToken, request.getId(), map, cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
			
		};
	}
		
	/****************************** SETTERS *******************************/
	
	/**
	 * Toggles the status of the driver. If the status is true, then the driver is currently driving.
	 * If the status is false, then the driver is not driving currently.
	 * @return a boolean indicating if the driver is driving
	 */
	public boolean toggleStatus() {
		status = !status;
		return status;
	}
	
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
		return status;
	}
	
	/**
	 * Returns the requests sent to the driver.
	 * @return an List of the drivers requests
	 */
	public List<Request> getRequests() {
		return new ArrayList<Request>(requests);
	}
	
	/************************ BUILDER *****************************/
	
	public static final class Builder<T extends Driver> {
		
		private T item;
				
		public Builder(T item) { this.item = item; }
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
		dest.writeString(licenseId);
		dest.writeString(insuranceId);
		dest.writeDouble(balance);
		dest.writeByte((byte) (status ? 1 : 0));
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
