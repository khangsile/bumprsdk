package com.llc.bumpr.sdk.models;

import retrofit.Callback;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

public class Review {
	private int userId;
	private int driverId;
	private int requestId;
	private int driverRating;
	private String description;
	private User user;
	
	public Review(Builder builder) {
		this.userId = builder.userId;
		this.driverId = builder.driverId;
		this.requestId = builder.requestId;
		this.driverRating = builder.driverRating;
		this.description = builder.description;
		this.user = builder.user;
	}

	/********************** API ***********************/

	public ApiRequest getPostRequest(final Callback<Response> cb) {

		final Review review = this;
		return new ApiRequest() {
			@Override
			public void execute(String authToken) {
				BumprAPI api = BumprClient.api();
				api.createReview(authToken, review.getDriverId(), review, cb);			
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
		};

	}

	/********************** GETTERS *******************/

	public int getUserId() {
		return userId;
	}
	
	public int getDriverId() {
		return driverId;
	}
	
	public int getRequestId() {
		return requestId;
	}
	
	public int getDriverRating() {
		return driverRating;
	}
	
	public String getDescription() {
		return description;
	}

	/********************* BUILDER **********************/
	
	public static class Builder {
		private int userId;
		private int driverId;
		private int requestId;
		private int driverRating;
		private String description;
		private User user;
		
		public Builder setUserId(int id) { userId = id; return this; }
		public Builder setDriverId(int id) { driverId = id; return this; }
		public Builder setRequestId(int id) { requestId = id; return this; }
		public Builder setDriverRating(int rating) { driverRating = rating; return this; }
		public Builder setDescription(String description) { this.description = description; return this; }
		public Builder setUser(User user) { this.user = user; return this; }
		public Review build() {
			return new Review(this);
		}
	}
}
