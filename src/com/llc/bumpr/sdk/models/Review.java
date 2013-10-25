package com.llc.bumpr.sdk.models;

public class Review {
	private int driverId;
	private int requestId;
	private int driverRating;
	private String description;
	
	public Review(Builder builder) {
		this.driverId = builder.driverId;
		this.requestId = builder.requestId;
		this.driverRating = builder.driverRating;
		this.description = builder.description;
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
	
	public static class Builder {
		private int driverId;
		private int requestId;
		private int driverRating;
		private String description;
		
		public Builder setDriverId(int id) { driverId = id; return this; }
		public Builder setRequestId(int id) { requestId = id; return this; }
		public Builder setDriverRating(int rating) { driverRating = rating; return this; }
		public Builder setDescription(String description) { this.description = description; return this; }
		public Review build() {
			return new Review(this);
		}
	}
}
