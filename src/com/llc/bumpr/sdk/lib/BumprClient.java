package com.llc.bumpr.sdk.lib;

import retrofit.RestAdapter;

import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.restrofit.Restrofit;

public class BumprClient {
	
	private static String baseURL = "http://192.168.1.200:3000/api/v1";//"http://bumpr.herokuapp.com/api/v1";
	private static Restrofit rest; 
	
	private static RestAdapter sharedAdapter() {
		return (Restrofit.sharedAdapter() != null) ? 
			Restrofit.sharedAdapter() : Restrofit.defaultAdapter(baseURL);
	}
	
	public static RestAdapter setBaseURL(String baseURL) {
		BumprClient.baseURL = baseURL;
		return Restrofit.defaultAdapter(baseURL);
	}
	
	public static BumprAPI api() {
		RestAdapter rest = sharedAdapter();
		return rest.create(BumprAPI.class);
	}
	
}
