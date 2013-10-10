package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.User;

public interface Users {
	
	@GET("/users/{id}")
	public void get(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}")
	public User get(@Path("id") int user_id);
	
	@PUT ("/users/{id}")
	public void update(@Header("Access-Token") String token, @Body User user, @Path("id") int userId, Callback<Boolean> cb);
	
	@PUT ("/users/{id}")
	public Boolean update(@Header("Access-Token") String token, @Body User user, @Path("id") int userId);
}
