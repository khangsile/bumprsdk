package com.llc.bumpr.sdk.interfaces;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.ActiveSession;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.User;

public interface Users {
	
	@GET("/users/{id}.json")
	public void get(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}.json")
	public User get(@Path("id") int user_id);
	
	@PUT ("/users/{id}.json")
	public void update(@Path("id") int userId, @Body Map<String, Object> body, Callback<User> cb);
	
	@PUT ("/users/{id}.json")
	public User update(@Path("id") int userId, @Body Map<String, Object> body);
	
	@POST ("/registrations.json")
	public void register(@Body Map<String, Registration> user, Callback<ActiveSession> cb);
	
	@POST ("/registrations.json")
	public ActiveSession register(@Body Map<String, Registration> user);
}
