package com.llc.bumpr.sdk.models;

import java.util.Date;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.client.Response;
import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

/**
 *
 * @author KhangSiLe
 *
 */
public class Request implements Parcelable {
	/** The id of the request */
	private int id;
	
	/** The id of the passenger */
	private int userId;
	
	/** The id of the driver */
	private int driverId;
	
	/** The id of the trip */
	private int tripId;
	
	/** The date time that the request was sent */
	private Date timeSent;
	
	/** The time the request was accepted */
	private Date timeAccepted;
	
	/** A boolean denoting if the request was accepted (or not). True for yes/False for no. */
	private boolean accepted;
	
	/** A boolean denoting if the correct confirmation code was delivered. */
	private boolean confirmed;
	
	/** The trip of the request */
	private Trip trip;
	
	public Request(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.driverId = builder.driverId;
		this.trip = builder.trip;
	}
	
	public Request(Parcel source) {
		id = source.readInt();
		userId = source.readInt();
		driverId = source.readInt();
		tripId = source.readInt();
		//timeSent = new Date(source.readLong());
		//timeAccepted = new Date(source.readLong());
		accepted = (source.readByte() != 0);
		confirmed = (source.readByte() != 0);
		trip = source.readParcelable(Trip.class.getClassLoader());
	}
	
	/**
	 * Sends a request from the user to the driver
	 * @param request
	 * @return an ApiRequest object/interface to be given to the session
	 */
	public ApiRequest postRequest(final Callback<Request> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("start", trip.getStart());
				map.put("end", trip.getEnd());
				api.request(authToken, driverId, map, cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
		};
	}
	
	/**
	 * Answers the request sent to the user giving the option to accept or deny the request.
	 * @param accept A boolean indicating whether the request was accepted or denied
	 */
	public ApiRequest respondTo(final boolean accept, final Callback<Response> cb) {
		return new ApiRequest() {
			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("accepted", accept);
				api.respondTo(authToken, driverId, id, map, cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}			
		};
	}
		
	/**
	 * Confirms if the request has been completed by matching the inputed confirmation code to the one give
	 * to the passenger.
	 * @param confirmationCode The confirmation code given by the passenger. If this matches, then the request 
	 * has been completed.
	 * @return a boolean indicating if the confirmation was successful.
	 */
	public ApiRequest confirmRequest(final boolean completed, Callback<Response> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
			}

			@Override
			public boolean needsAuth() {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
	}
	
	/************************** GETTERS *****************************/
	
	public int getId() {
		return id;
	}
	
	public int getDriverId() {
		return driverId;
	}
	
	public boolean getAccepted() {
		return accepted;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	/*************************** BUILDER ****************************/
	
	public static class Builder {
		
		private int id;
		private int userId;
		private int driverId;
		private Trip trip;
		
		public Builder setId(int id) { this.id = id; return this; }
		public Builder setUserId(int userId) { this.userId = userId; return this; }
		public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
		public Builder setTrip(Trip trip) { this.trip = trip; return this; }
		
		public Request build() {
			if (userId < 1 || driverId < 1 || trip == null) {
				throw new IllegalStateException("Invalid Request state: missing parameters");
			}
			
			return new Request(this);
		}
	}
	
	/***************************** PARCELABLE ********************************/

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeInt(userId);
		dest.writeInt(driverId);
		dest.writeInt(tripId);
		//dest.writeLong(timeSent.getTime());
		//dest.writeLong(timeAccepted.getTime());
		dest.writeByte((byte) (accepted ? 1 : 0));		
		dest.writeByte((byte) (confirmed ? 1 : 0));
		dest.writeParcelable(trip, 0);
	}
	
	public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
		@Override
		public Request createFromParcel(Parcel source) {
			return new Request(source);
		}

		@Override
		public Request[] newArray(int size) {
			return new Request[size];
		}
		
	};
}
