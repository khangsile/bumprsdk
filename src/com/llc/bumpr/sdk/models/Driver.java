package com.llc.bumpr.sdk.models;

import com.llc.restrofit.Restrofit;

public class Driver extends User {
	
	private double balance;
	private String licenseId, insuranceId;
	private boolean cab, trustworthy, status;
	
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
	 * Toggles the status of the driver. If the status is true, then the driver is currently driving.
	 * If the status is false, then the driver is not driving currently.
	 *  
	 * @return a boolean indicating if the driver is driving
	 */
	public boolean toggleStatus() {
		status = !status;
		return status;
	}
}
