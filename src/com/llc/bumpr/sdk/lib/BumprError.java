package com.llc.bumpr.sdk.lib;

public class BumprError {
	
	private int code;
	private String message;
	
	/**
	 * Returns the error code of the the error. This correlates directly to the error listings
	 * on the Bumpr API.
	 * @return the error code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Returns the message of the error. This is a more in depth message than the error code.
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}
}
