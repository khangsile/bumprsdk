package com.llc.bumpr.sdk.models;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.client.Response;

import com.google.gson.annotations.SerializedName;
import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

public class Review {
	
	@SerializedName("user_id")
	private int userId;
	
	@SerializedName("driver_id")
	private int driverId;
	
	@SerializedName("request_id")
	private int requestId;
	
	private int rating;
	private String content;
	
	public Review(Builder builder) {
		this.userId = builder.userId;
		this.driverId = builder.driverId;
		this.requestId = builder.requestId;
		this.rating = builder.rating;
		this.content = builder.content;
	}

	/********************** API ***********************/

	public ApiRequest getPostRequest(final Callback<Response> cb) {

		final Review review = this;
		return new ApiRequest() {
			@Override
			public void execute(String baseURL, String authToken) {
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("request_id", requestId);
				map.put("rating", rating);
				map.put("content", content);
				api.createReview(authToken, driverId, map, cb);			
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
		return rating;
	}
	
	public String getContent() {
		return content;
	}

	/********************* BUILDER **********************/
	
	public static class Builder {
		private int userId;
		private int driverId;
		private int requestId;
		private int rating;
		private String content;
		private User user;
		
		public Builder setUserId(int id) { userId = id; return this; }
		public Builder setDriverId(int id) { driverId = id; return this; }
		public Builder setRequestId(int id) { requestId = id; return this; }
		public Builder setRating(int rating) { rating = rating; return this; }
		public Builder setContent(String content) { this.content = content; return this; }
		public Builder setUser(User user) { this.user = user; return this; }
		public Review build() {
			return new Review(this);
		}
	}
}
