package com.llc.bumpr.sdk.interfaces;

import java.util.HashMap;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.LoginResponse;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.User;

public interface UserAPI {
	
	@GET("/users/{id}.json")
	public void getUser(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}.json")
	public User getUser(@Path("id") int user_id);

	@POST ("/users.json")
	public void register(@Body Registration user, Callback<LoginResponse> cb);
	
	@POST ("/users.json")
	public LoginResponse register(@Body Registration user);
	
	@PUT ("/users/{id}.json")
	public void updateUser(@Header("X-AUTH-TOKEN") String token, @Path("id") int userId, @Body HashMap<String, Object> user, Callback<User> cb);
	
	@PUT ("/users/{id}.json")
	public User updateUser(@Header("X-AUTH-TOKEN") String token, @Path("id") int userId, @Body HashMap<String, Object> user);
}
