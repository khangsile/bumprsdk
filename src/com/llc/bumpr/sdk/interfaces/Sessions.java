package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;

import com.llc.bumpr.sdk.models.Session;

public interface Sessions {
	@POST ("/user/login")
	public void login(@Body String email, @Body String password, Callback<Session> cb);
	
	@POST ("/user/login")
	public Session login(@Body String email, @Body String password);
	
	@PUT ("/user/register")
	public void register(@Body String firstname, @Body String lastname, @Body String email, @Body String password, @Body String passwordConfirmation, Callback<Session> cb);
	
	@PUT ("/user/register")
	public Session register(@Body String firstname, @Body String lastname, @Body String email, @Body String password, @Body String passwordConfirmation);
}
