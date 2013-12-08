package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.Location;

/**
 * Model that represents a search request for a trip
 * Use this model to build your search requests, and pass it to the
 * session sendRequest method to send it.
 * @author KhangSiLe
 *
 */
public class SearchRequest implements ApiRequest {
	
	private Context context;
	private FutureCallback<String> cb;
	
	@Expose()
	@SerializedName("start_location")
	private Location startLocation = null;
	
	@Expose()
	@SerializedName("end_location")
	private Location endLocation = null;
	
	@Expose()
	@SerializedName("tag_list")
	private ArrayList<String> tags;
	
	@Expose()
	@SerializedName("max_cost")
	private double maxCost = 100000;
	
	@Expose()
	@SerializedName("min_seats")
	private int minSeats = 1;
	
	/**
	 * Standard constructor
	 */
	public SearchRequest() {
	}
	
	public SearchRequest setContext(Context context) {
		this.context = context;
		return this;
	}
	
	public SearchRequest setCallback(FutureCallback<String> cb) {
		this.cb = cb;
		return this;
	}
	
	public SearchRequest setStart(Location start) {
		this.startLocation = start;
		return this;
	}
	
	public SearchRequest setEnd(Location end) {
		this.endLocation = end;
		return this;
	}
	
	public SearchRequest setTags(ArrayList<String> tags) {
		this.tags = tags;
		return this;
	}
	
	public SearchRequest setMaxCost(double maxCost) {
		this.maxCost = maxCost;
		return this;
	}
	
	public SearchRequest setMinSeats(int minSeats) {
		this.minSeats = minSeats;
		return this;
	}
	
	public Location getStart() {
		return startLocation;
	}
	
	public Location getEnd() {
		return endLocation;
	}
	
	@Override
	public void execute(String baseURL, String authToken) {
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
		JsonElement json = gson.toJsonTree(this);
		
		Ion.with(context).load("POST", baseURL + "/search")
		.setTimeout(5000)
		.setJsonObjectBody(json)
		.asString()
		//.as(new TypeToken<List<Trip>>() {})
		.setCallback(cb);
	}

	@Override
	public boolean needsAuth() {
		return false;
	}
	
}
