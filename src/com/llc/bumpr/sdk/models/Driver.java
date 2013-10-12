package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
	
	private double balance;
	private String licenseId;
	private String insuranceId;
	private boolean cab;
	private boolean trustworthy;
	private boolean status;
	
	private List<Request> requests = new ArrayList<Request>();
		
	/****************************** SETTERS *******************************/
	
	/**
	 * Toggles the status of the driver. If the status is true, then the driver is currently driving.
	 * If the status is false, then the driver is not driving currently.
	 *  
	 * @return a boolean indicating if the driver is driving
	 */
	public boolean toggleStatus() {
		status = !status;
		return status;
	}
	
	/**
	 * Adds a request to the requests list.
	 * @param request a Request object to add to the requests list
	 */
	public void addRequest(Request request) {
		if (requests == null) requests = new ArrayList<Request>();
		requests.add(request);
	}
	
	
	/******************************* GETTERS *******************************/
	
	/**
	 * Returns the drivers balance. This represents the amount they have made off of driving users. 
	 *
	 * @return the balance of the driver
	 */
	public double getBalance() {
		
		return balance;
	}
	
	/**
	 * Returns the drivers license id number. This verifies that they have a true license.
	 * 
	 * @return the license id number of the driver
	 */
	public String getLicenseId() {
		return licenseId;
	}
	
	/**
	 * Returns the drivers insurance id number. This verifies that they have insurance for their car.
	 * 
	 * @return the insurance id number of the driver
	 */
	public String getInsuranceId() {
		return insuranceId;
	}
	
	/**
	 * Returns the status of the driver. True represents they are driving. False otherwise.
	 * 
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
	
	public static final class Builder extends User.Builder<Driver> {
		
		private String licenseId;
		private String insuranceId;
		
		public Builder() { super(new Driver()); }
		public Builder setLicenseId(String licenseId) { build().licenseId = licenseId; return this; }
		public Builder setInsuranceId(String insuranceId) { build().insuranceId = insuranceId; return this; }
	}
	
}
