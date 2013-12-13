package com.llc.bumpr.sdk.models;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

/**
 * Represents requests
 * @author KhangSiLe
 *
 */
public class Request implements Parcelable {
	/** The id of the request */
	private int id;
	
	/** A boolean denoting if the request was accepted (or not). True for yes/False for no. */
	private Boolean accepted;
	
	/** The trip of the request */
	private Trip trip;
	
	/** The user who sent the request */
	private User user;
		
	public Request(Parcel source) {
		id = source.readInt();
		accepted = (source.readByte() != 0);
		trip = source.readParcelable(Trip.class.getClassLoader());
		user = source.readParcelable(User.class.getClassLoader());
	}
	
	public static ApiRequest getRequests(final Context context, final String type, final FutureCallback<List<Request>> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load("GET", baseURL + "/requests.json")
				.addHeader("X-AUTH-TOKEN", authToken)
				.addQuery("type", type)
				.asString()
				.setCallback(new FutureCallback<String>() {

					@Override
					public void onCompleted(Exception arg0, String arg1) {
						List<Request> requests = null;
						if (arg0 == null) {
							Type type = new TypeToken<List<Request>>(){}.getType();
							
							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss 'UTC'").create();
							requests = gson.fromJson(arg1, type);
						}
						
						cb.onCompleted(arg0, requests);
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
	 * Sends a request from the user to the driver
	 * @param request
	 * @return an ApiRequest object/interface to be given to the session
	 */
	public static ApiRequest postRequest(final Context context, final Trip trip, final FutureCallback<String> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load("POST", baseURL + "/trips/" + trip.getId() + "/requests.json")
				.setHeader("X-AUTH-TOKEN", authToken)
				.setHeader("Content-Length", "0")
				.asString()
				.setCallback(cb);
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
	public ApiRequest respondTo(final Context context, final boolean accept, final FutureCallback<String> cb) {
		return new ApiRequest() {
			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load("PUT", baseURL + "/requests/" + id + ".json")
				.setHeader("X-AUTH-TOKEN", authToken)
				.setBodyParameter("accepted", (accept) ? "true" : "false")
				.asString()
				.setCallback(cb);
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
	
	public User getUser() {
		return user;
	}
	
	public Boolean getAccepted() {
		return accepted;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	
	/***************************** PARCELABLE ********************************/

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeByte((byte) (accepted ? 1 : 0));		
		dest.writeParcelable(trip, 0);
		dest.writeParcelable(user, 0);
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
