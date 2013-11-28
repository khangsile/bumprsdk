package com.llc.bumpr.sdk.interfaces;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.llc.bumpr.sdk.models.Request;

public interface RequestAPI {

	@GET ("/requests/{id}.json")
	public void getRequest(@Path("id") int id, Callback<Request> cb);
	
}
