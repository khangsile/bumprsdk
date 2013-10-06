package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.User;

public interface Users {
	
	@GET("/users/{id}")
	public void get(@Path("id") String user_id, Callback<User> cb);
	
	@GET("/users/{id}")
	public User get(@Path("id") String user_id);
}
