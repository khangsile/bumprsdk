package com.llc.bumpr.sdk.lib;

import java.io.InputStream;
import java.util.Scanner;

import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.Gson;
import com.llc.restrofit.Restrofit;

public class BumprError {
	
	private int code;
	private String message;
	private String error;
	
	public static BumprError errorToBumprError(RetrofitError e) throws Exception {
		String json = BumprError.errorToString(e);
        Gson gson = Restrofit.defaultGsonBuilder().create();
        return gson.fromJson(json, BumprError.class); 
	}
	
	public static String errorToString(RetrofitError e) throws Exception {
		InputStream in = e.getResponse().getBody().in();
        return new Scanner(in,"UTF-8").useDelimiter("\\A").next();
	}
	
	public static String responseToString(Response e) throws Exception {
		InputStream in = e.getBody().in();
		return new Scanner(in, "UTF-8").useDelimiter("\\A").next();
	}
	
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
	
	/**
	 * return the error in the form of a string
	 */
	public String getError() {
		return error;
	}
}
