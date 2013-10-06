package com.llc.bumpr.sdk.models;

import java.util.Date;

public class Request {
	private int id, userId, driverId, tripId;
	private Date timeSent, timeAccepted;
	private String confirmationCode;
	private boolean accepted, confirmed;
	
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
}
