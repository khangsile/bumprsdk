package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.ActiveSession;
import com.llc.bumpr.sdk.models.InactiveSession;
import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.Session;
import com.llc.bumpr.sdk.models.User;

public interface Sessions {
	
	@POST ("/user/sessions")
	public void login(@Body Login user_login, Callback<ActiveSession> cb);
	
	@POST ("/user/login")
	public ActiveSession login(@Body Login user_login);
	
	@DELETE ("/user/sessions/")
	public void logout(@Header("Access-Token") String token, Callback<InactiveSession> cb);
	
	@DELETE ("/user/sessions")
	public InactiveSession logout(@Header("Access-Token") String token);
	
	@PUT ("/users/{id}")
	public void update(@Header("Access-Token") String token, @Body User user, @Path("id") int userId, Callback<User> cb);
	
	@POST ("/registration")
	public void register(@Body Registration user, Callback<ActiveSession> cb);
	
	@POST ("/registration")
	public Session register(@Body Registration user);
	
}
