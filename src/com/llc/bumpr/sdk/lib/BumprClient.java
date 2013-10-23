package com.llc.bumpr.sdk.lib;

import retrofit.RestAdapter;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.restrofit.Restrofit;

public class BumprClient {
	
	private static final String baseURL = "http://bumpr.herokuapp.com/api/v1";
	private static Restrofit rest; 
	
	private static RestAdapter sharedAdapter() {
		return (Restrofit.sharedAdapter() != null) ? 
			Restrofit.sharedAdapter() : Restrofit.defaultAdapter(baseURL);
	}
	
	public static BumprAPI api() {
		RestAdapter rest = sharedAdapter();
		return rest.create(BumprAPI.class);
	}
	
}
