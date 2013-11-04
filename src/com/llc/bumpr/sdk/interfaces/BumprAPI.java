package com.llc.bumpr.sdk.interfaces;

import java.util.HashMap;
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
import retrofit.http.Query;

import com.llc.bumpr.sdk.lib.Coordinate;
import com.llc.bumpr.sdk.models.Driver;
import com.llc.bumpr.sdk.models.Login;
import com.llc.bumpr.sdk.models.LoginResponse;
import com.llc.bumpr.sdk.models.Registration;
import com.llc.bumpr.sdk.models.Request;
import com.llc.bumpr.sdk.models.Review;
import com.llc.bumpr.sdk.models.SearchQuery;
import com.llc.bumpr.sdk.models.Trip;
import com.llc.bumpr.sdk.models.User;

public interface BumprAPI {
	
	@POST ("/drivers.json")
	public void registerDriver(@Header("X-AUTH-TOKEN") String token, Callback<Driver> cb);
	
	@POST ("/drivers.json")
	public Driver registerDriver(@Header("X-AUTH-TOKEN") String token);
	
	@POST ("/drivers/{id}/driver_review.json")
	public void createReview(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body Review review, Callback<Response> cb);
	
	@POST ("/drivers/{id}/driver_review.json")
	public Response createReview(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body Review review);
	
	@POST ("/drivers/{id}/requests.json")
	public void request(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Trip trip, Callback<Request> cb);

	@POST ("/drivers/{id}/requests.json")
	public Request request(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Trip trip);
	
	/**
	 * search drivers method. currently Retrofit does not support variable-query params
	 */
	@GET ("/drivers.json")
	public void searchDrivers(@Query("top") double top, @Query("left") double left, @Query("bottom") double bottom, @Query("right") double right, Callback<Response> cb);
	
	@GET ("/drivers.json")
	public void searchDrivers(@Query("top") double top, @Query("left") double left, @Query("bottom") double bottom, 
			@Query("right") double right, @Query("min_fee") double minFee, @Query("min_seats") int minSeats, Callback<Response> cb);
	
	@GET ("/drivers.json")
	public Response searchDrivers(@Query("top") double top, @Query("left") double left, @Query("bottom") double bottom, @Query("right") double right);
	
	@PUT ("/drivers/{id}/update_location.json")
	public void updateLocation(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Coordinate coordiante, Callback<Response> cb);
	
	@PUT ("/drivers/{id}/update_location.json")
	public Response updateLocation(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Coordinate coordinate);
	
	@PUT ("/requests/{id}.json")
	public void respondTo(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Map<String, Object> map, Callback<Response> cb);
	
	@POST ("/sessions.json")
	public void login(@Body Login login, Callback<LoginResponse> cb);
	
	@POST ("/sessions.json")
	public LoginResponse login(@Body Login login);
	
	@POST ("/sessions.json")
	public void login(@Body HashMap<String, Object> login, Callback<LoginResponse> cb);
	
	@POST ("/sessions.json")
	public LoginResponse login(@Body HashMap<String, Object> login);
	
	@DELETE ("/sessions.json")
	public void logout(@Header("X-AUTH-TOKEN") String token, Callback<Response> cb);
	
	@DELETE ("/sessions.json")
	public Response logout(@Header("X-AUTH-TOKEN") String token);
	
	@GET("/users/{id}.json")
	public void get(@Path("id") int user_id, Callback<User> cb);
	
	@GET("/users/{id}.json")
	public User get(@Path("id") int user_id);

	@POST ("/users.json")
	public void register(@Body Registration user, Callback<LoginResponse> cb);
	
	@POST ("/users.json")
	public LoginResponse register(@Body Registration user);
	
	@PUT ("/users/{id}.json")
	public void update(@Header("X-AUTH-TOKEN") String token, @Path("id") int userId, @Body HashMap<String, Object> user, Callback<User> cb);
	
	@PUT ("/users/{id}.json")
	public User update(@Header("X-AUTH-TOKEN") String token, @Path("id") int userId, @Body HashMap<String, Object> user);
	
}
