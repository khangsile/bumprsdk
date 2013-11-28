package com.llc.bumpr.sdk.interfaces;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import com.llc.bumpr.sdk.lib.Coordinate;
import com.llc.bumpr.sdk.models.Driver;
import com.llc.bumpr.sdk.models.Request;
import com.llc.bumpr.sdk.models.Review;

public interface DriverAPI {

	@POST ("/drivers.json")
	public void registerDriver(@Header("X-AUTH-TOKEN") String token, @Body HashMap<String, Object> driver, Callback<Driver> cb);
	
	@POST ("/drivers.json")
	public Driver registerDriver(@Header("X-AUTH-TOKEN") String token);
	
	@GET ("/drivers/{id}.json")
	public void getDriver(@Path("id") int driverId, Callback<Response> cb);
	
	@GET ("/drivers/{id}.json")
	public Response getDriver(@Path("id") int driverId);
	
	@PUT ("/drivers/{id}.json")
	public void updateDriver(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body HashMap<String, Object> driver, Callback<Driver> cb);
	
	@PUT ("/drivers/{id}.json")
	public Driver updateDriver(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body HashMap<String, Object> driver);
	
	@POST ("/drivers/{id}/driver_reviews.json")
	public void createReview(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body HashMap<String, Object> review, Callback<Response> cb);
	
	@POST ("/drivers/{id}/driver_reviews.json")
	public Response createReview(@Header("X-AUTH-TOKEN") String token, @Path("id") int driverId, @Body Review review);
	
	@POST ("/drivers/{id}/requests.json")
	public void request(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body HashMap<String, Object> trip, Callback<Request> cb);

	@POST ("/drivers/{id}/requests.json")
	public Request request(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body HashMap<String, Object> trip);
	
	
	//@PUT ("/drivers/{id}/")
	
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
	
	@PUT ("/drivers/{id}/driver_location.json")
	public void updateLocation(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Coordinate coordiante, Callback<Response> cb);
	
	@PUT ("/drivers/{id}/driver_location.json")
	public Response updateLocation(@Header("X-AUTH-TOKEN") String token, @Path("id") int id, @Body Coordinate coordinate);
	
	@PUT ("/drivers/{driverId}/requests/{id}.json")
	public void respondTo(@Header("X-AUTH-TOKEN") String token, @Path("driverId") int driverId, @Path("id") int id, @Body Map<String, Object> map, Callback<Response> cb);
	
	
}
