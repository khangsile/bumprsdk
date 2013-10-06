package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;

import com.llc.bumpr.sdk.models.Session;
import com.llc.bumpr.sdk.models.User;

public interface Sessions {
	@GET ("/user/login")
	public void login(@Body String email, @Body String password, Callback<Session> cb);
	
	@GET ("/user/login")
	public Session login(@Body String email, @Body String password);
	
	@PUT ("/user/update")
	public void update(@Header("Access-Token") String token, @Body User user, Callback<User> cb);
	
	@POST ("/user/register")
	public void register(@Body User user, @Body String password, @Body String passwordConfirmation, Callback<Session> cb);
	
	@POST ("/user/register")
	public Session register(@Body User user, @Body String password, @Body String passwordConfirmation);
}
