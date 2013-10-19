package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.BumprClient;

/**
 * A Driver class that represents the driver's profile.
 * @author KhangSiLe
 * @version 0.1
 */
public class Driver {
	/**
	 * The driver's balance (money)
	 */
	private double balance;
	/**
	 * The driver id (linked to the driver table)
	 */
	private int id;
	/**
	 * The driver license number
	 */
	private String licenseId;
	/**
	 * The driver insurance number
	 */
	private String insuranceId;
	/**
	 * A boolean indicating if the driver is a cab
	 */
	private boolean cab;
	/**
	 * A boolean if the driver has been authorized by us.
	 */
	private boolean trustworthy;
	/**
	 * A boolean if the driver is currently available to drive.
	 */
	private boolean status;
	/**
	 * A List of Request objects that represents the requests to the driver
	 */
	private List<Request> requests = new ArrayList<Request>();
	
	/****************************** API ***********************************/
	
	/**
	 * Respond to the given request.
	 * @param request The request that the driver is responding to.
	 * @param cb The Callback method that needs to be implemented when response is returned from the server
	 * @return an ApiRequest object which can be sent to the session object to be executed.
	 */
	public ApiRequest respondToRequest(final Request request, final Callback<String> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String authToken) {
				// TODO Auto-generated method stub
				BumprAPI api = BumprClient.api();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("accepted", new Boolean(request.getAccepted()));
				api.respondTo(authToken, request.getId(), map, cb);
			}

			@Override
			public boolean needsAuth() {
				// TODO Auto-generated method stub
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
	
}
