package com.llc.bumpr.sdk.models;

import java.util.Date;

public class Request {
	private int id;
	private int userId;
	private int driverId;
	private int tripId;
	
	private Date timeSent;
	private Date timeAccepted;
	
	private String confirmationCode;
	
	private boolean accepted;
	private boolean confirmed;
	
	private Trip trip;
	
	public Request(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.driverId = builder.driverId;
		this.trip = builder.trip;
	}
	
	/**
	 * Answers the request sent to the user giving the option to accept or deny the request.
	 * 
	 * @param accept A boolean indicating whether the request was accepted or denied
	 */
	public void answerRequest(boolean accept) {
		
	}
	
	/**
	 * Confirms if the request has been completed by matching the inputed confirmation code to the one give
	 * to the passenger.
	 * 
	 * @param confirmationCode The confirmation code given by the passenger. If this matches, then the request 
	 * has been completed.
	 * @return a boolean indicating if the confirmation was successful.
	 */
	public boolean confirmRequest(String confirmationCode) {
		return true;
	}
	
	/************************** GETTERS *****************************/
	
	public int getId() {
		return id;
	}
	
	public boolean getAccepted() {
		return accepted;
	}
	
	/*************************** BUILDER ****************************/
	
	public class Builder {
		
		private int id;
		private int userId;
		private int driverId;
		private Trip trip;
		
		public Builder setId(int id) { this.id = id; return this; }
		public Builder setUserId(int userId) { this.userId = userId; return this; }
		public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
		public Builder setTrip(Trip trip) { this.trip = trip; return this; }
		
		public Request build() throws Exception {
			if (id < 1 || userId < 1 || driverId < 1 || trip == null) {
				throw new Exception("Invalid Request state: missing parameters");
			}
			
			return new Request(this);
		}
	}
}
