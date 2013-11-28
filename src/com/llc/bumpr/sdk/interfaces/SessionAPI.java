package com.llc.bumpr.sdk.interfaces;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Header;
import retrofit.http.POST;

import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.LoginResponse;

public interface SessionAPI {
	
	@POST ("/sessions.json")
	public LoginResponse login(@Body Login login);
	
	@POST ("/sessions.json")
	public void login(@Body HashMap<String, Object> login, Callback<LoginResponse> cb);
	
	@POST ("/facebook_login.json")
	public void login(@Header("OmniAuth") String header, @Body HashMap<String, Object> login, Callback<LoginResponse> cb);
	
	@POST ("/sessions.json")
	public LoginResponse login(@Body HashMap<String, Object> login);
	
	@DELETE ("/sessions.json")
	public void logout(@Header("X-AUTH-TOKEN") String token, Callback<Response> cb);
	
	@DELETE ("/sessions.json")
	public Response logout(@Header("X-AUTH-TOKEN") String token);
}
