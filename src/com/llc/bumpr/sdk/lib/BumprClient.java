package com.llc.bumpr.sdk.lib;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import com.google.gson.Gson;
import com.llc.bumpr.sdk.interfaces.BumprAPI;
import com.llc.bumpr.sdk.interfaces.DriverAPI;
import com.llc.bumpr.sdk.interfaces.RequestAPI;
import com.llc.bumpr.sdk.interfaces.SessionAPI;
import com.llc.bumpr.sdk.interfaces.UserAPI;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.serializers.RegistrationSerializer;
import com.llc.restrofit.Restrofit;

public class BumprClient {
	
	private static String baseURL = "http://192.168.1.200:3000/api/v1"; //"http://bumpr.herokuapp.com/api/v1";
	
	private static RestAdapter sharedAdapter() {
		
		RestAdapter adapter = Restrofit.sharedAdapter();
		
		if (adapter == null) {
			Gson gson = Restrofit.defaultGsonBuilder()
							.registerTypeAdapter(Registration.class, new RegistrationSerializer())
							.create();
			adapter = Restrofit.defaultBuilder(baseURL)
						.setConverter(new GsonConverter(gson))
						.build();
			Restrofit.setSharedAdapter(adapter);
		}
			
		return adapter;
	}
	
	public static RestAdapter setBaseURL(String baseURL) {
		BumprClient.baseURL = baseURL;
		Restrofit.setSharedAdapter(null);
		
		return sharedAdapter();
	}
	
	public static BumprAPI api() {
		RestAdapter rest = sharedAdapter();
		return rest.create(BumprAPI.class);
	}
	
	public static UserAPI userAPI() {
		RestAdapter rest = sharedAdapter();
		return rest.create(UserAPI.class);
	}
	
	public static DriverAPI driverAPI() {
		RestAdapter rest = sharedAdapter();
		return rest.create(DriverAPI.class);
	}
	
	public static RequestAPI requestAPI() {
		RestAdapter rest = sharedAdapter();
		return rest.create(RequestAPI.class);
	}
	
	public static SessionAPI sessionAPI() {
		RestAdapter rest = sharedAdapter();
		return rest.create(SessionAPI.class);
	}
	
}
