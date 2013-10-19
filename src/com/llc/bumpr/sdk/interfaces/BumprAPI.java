package com.llc.bumpr.sdk.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.Driver;
import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.LoginResponse;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.Request;
import com.llc.bumpr.sdk.models.SearchQuery;
import com.llc.bumpr.sdk.models.User;

public interface BumprAPI {
	
	@POST ("/registrations.json")
	public void register(@Body Registration user, Callback<LoginResponse> cb);
	
	@POST ("/registrations.json")
	public LoginResponse register(@Body Registration user);
	
	@POST ("/requests.json")
	public void request(@Header("X-AUTH-TOKEN") String token, @Body Request request, Callback<Request> cb);

	@POST ("/requests.json")
	public Request request(@Header("X-AUTH-TOKEN") String token, @Body Request request);
	
	@PUT ("/requests/{id}.json")
	public void respondTo(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Map<String, Object> map, Callback<String> cb);

	@POST ("/search.json")
	public void searchDrivers(@Body SearchQuery query, Callback<List<Driver>> cb);

	@POST ("/search.json")
	public List<Driver> searchDrivers(@Body SearchQuery query);
	
	@POST ("/sessions.json")
	public void login(@Body Login login, Callback<LoginResponse> cb);
	
	@POST ("/sessions.json")
	public LoginResponse login(@Body HashMap<String, Login> login);
	
	@DELETE ("/sessions.json")
	public void logout(@Header("X-AUTH-TOKEN") String token, Callback<Response> cb);
	
	@DELETE ("/sessions.json")
	public Response logout(@Header("X-AUTH-TOKEN") String token);
	
	@GET("/users/{id}.json")
	public void get(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}.json")
	public User get(@Path("id") int user_id);
	
	@PUT ("/users/{id}.json")
	public void update(@Path("id") int userId, @Body User user, Callback<User> cb);
	
	@PUT ("/users/{id}.json")
	public User update(@Path("id") int userId, @Body User user);
	
}
