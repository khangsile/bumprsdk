package com.llc.bumpr.sdk.interfaces;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.ActiveSession;
import com.llc.bumpr.sdk.models.Driver;
import com.llc.bumpr.sdk.models.InactiveSession;
import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.Request;
import com.llc.bumpr.sdk.models.SearchQuery;
import com.llc.bumpr.sdk.models.User;

public interface BumprAPI {
	
	@POST ("/registrations.json")
	public void register(@Body Map<String, Registration> user, Callback<ActiveSession> cb);
	
	@POST ("/registrations.json")
	public ActiveSession register(@Body Map<String, Registration> user);
	
	@POST ("/requests.json")
	public void request(@Header("Access-Token") String token, @Body Request request, Callback<Request> cb);

	@POST ("/requests.json")
	public Request request(@Header("Access-Token") String token, @Body Request request);
	
	@PUT ("/requests/respond_to.json")
	public void respondTo(@Header("Access-Token") String token, @Body Map<String, Object> map, Callback<String> cb);

	@POST ("/search.json")
	public void searchDrivers(@Body SearchQuery query, Callback<List<Driver>> cb);

	@POST ("/search.json")
	public List<Driver> searchDrivers(@Body SearchQuery query);
	
	@POST ("/sessions.json")
	public void login(@Body Map<String, Login> userLogin, Callback<ActiveSession> cb);
	
	@POST ("/sessions.json")
	public ActiveSession login(@Body Map<String, Login> userLogin);
	
	@DELETE ("/sessions.json")
	public void logout(@Header("Access-Token") String token, Callback<InactiveSession> cb);
	
	@DELETE ("/sessions.json")
	public InactiveSession logout(@Header("Access-Token") String token);
	
	@GET("/users/{id}.json")
	public void get(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}.json")
	public User get(@Path("id") int user_id);
	
	@PUT ("/users/{id}.json")
	public void update(@Path("id") int userId, @Body Map<String, Object> body, Callback<User> cb);
	
	@PUT ("/users/{id}.json")
	public User update(@Path("id") int userId, @Body Map<String, Object> body);
	
}
