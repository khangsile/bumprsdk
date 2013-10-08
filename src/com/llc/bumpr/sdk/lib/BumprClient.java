package com.llc.bumpr.sdk.lib;

import retrofit.RestAdapter;

import com.llc.bumpr.sdk.interfaces.Sessions;
import com.llc.bumpr.sdk.interfaces.Users;
import com.llc.restrofit.Restrofit;

public class BumprClient {
	
	private static final String baseURL = "https://www.bumpr.io/api/v1";
	private static Restrofit rest; 
	
	private static RestAdapter sharedAdapter() {
		return (Restrofit.sharedAdapter() != null) ? 
			Restrofit.sharedAdapter() : Restrofit.defaultAdapter(baseURL);
	}
	
	public static Sessions sessions() {
		RestAdapter rest = sharedAdapter();
		return rest.create(Sessions.class);
	}
	
	public static Users users() {
		RestAdapter rest = sharedAdapter();
		return rest.create(Users.class);
	}
	
}
