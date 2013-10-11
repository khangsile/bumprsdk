package com.llc.bumpr.sdk.interfaces;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Header;
import retrofit.http.POST;

import com.llc.bumpr.sdk.models.ActiveSession;
import com.llc.bumpr.sdk.models.Driver;
import com.llc.bumpr.sdk.models.InactiveSession;
import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.Request;

public interface Sessions {
	
	@POST ("/sessions.json")
	public void login(@Body Map<String, Login> userLogin, Callback<ActiveSession> cb);
	
	@POST ("/sessions.json")
	public ActiveSession login(@Body Map<String, Login> userLogin);
	
	@DELETE ("/user/sessions.json")
	public void logout(@Header("Access-Token") String token, Callback<InactiveSession> cb);
	
	@DELETE ("/user/sessions.json")
	public InactiveSession logout(@Header("Access-Token") String token);
	
	@POST ("/registrations.json")
	public void register(@Body Map<String, Registration> user, Callback<ActiveSession> cb);
	
	@POST ("/registrations.json")
	public ActiveSession register(@Body Map<String, Registration> user);

	@POST ("/requests")
	public void request(@Header("Access-Token") String token, @Body Request request, Callback<Request> cb);

	@POST ("/requests")
	public Request request(@Header("Access-Token") String token, @Body Request request);

	@POST ("/search")
	public void searchDrivers(@Body Map<String, String> search, Callback<List<Driver>> cb);

	@POST ("/search")
	public List<Driver> searchDrivers(@Body Map<String, String> search);

}
