package com.llc.bumpr.sdk.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.Coordinate;

public class Trip implements Parcelable {

	/** The id of the trip */
	private int id;
	
	/** The userId that created the trip */
	@SerializedName("user_id")
	private int userId;
	
	/** The driverId that the trip belongs to */
	@SerializedName("driver_id")
	private int driverId;
	
	/** The start of the trip */
	private Coordinate start;
	
	/** The end of the trip */
	private Coordinate end;
	
	/** The trip's fee */
	private double cost;
	
	/** The start date of the trip **/
	@SerializedName("start_time")
	private Date startTime;
	
	/** The min seats for the driver */
	@SerializedName("min_seats")
	private int minSeats;
	
	/** The number of seats for the driver */
	@SerializedName("num_seats")
	private int numSeats;
	
	/** The tags of trip */
	@SerializedName("tag_list")
	private ArrayList<String> tags;
	
	
	/************************************* STATIC METHODS ****************************/
	
	public static ApiRequest search(final Context context, final ArrayList<String> tags, final Coordinate start, final Coordinate end, final int radius) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				JSONObject json = new JSONObject();
			
				try {
					json.put("tag_list", new JSONArray(tags));
					
					JSONObject jstart = new JSONObject();
					jstart.put("latitude", start.lat);
					jstart.put("longitude", start.lon);
					jstart.put("distance", radius);
					
					JSONObject jend = new JSONObject();
					jend.put("latitude", end.lat);
					jend.put("longitude", end.lon);
					jend.put("distance", radius);
					
					json.put("start_location", jstart);
					json.put("end_location", jend);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
				Ion.with(context).load(baseURL + "/trips/search")
					.setJsonObjectBody(json)
					.as(new TypeToken<List<Trip>>() {})
					.setCallback(new FutureCallback<List<Trip>>() {

						/*
						 * tag_list - an array of strings
						 * start_location - longitude, latitude, distance in meters
						 * end_location - longitude, latitude, distance in meters
						 */
						@Override
						public void onCompleted(Exception arg0, List<Trip> arg1) {
							//Do something							
						}
						
					});
			}

			@Override
			public boolean needsAuth() {
				return false;
			}
			
		};
	}
	
	/**
	 * Private constructor for the trip
	 * @param builder A builder class that builds the Trip
	 */
	private Trip(Builder builder) {
		this.userId = builder.userId;
		this.driverId = builder.driverId;
		this.cost = builder.cost;
		this.start = builder.start;
		this.end = builder.end;
		this.minSeats = builder.minSeats;
		this.numSeats = builder.numSeats;
		this.tags = builder.tags;
	}
	
	/**
	 * Constructor for reading from JSONObject
	 * @param json The JSON representation of the trip object
	 * @throws JSONException
	 */
	public Trip(JSONObject json) throws JSONException {
		JSONObject start = json.getJSONObject("start");
		JSONObject end = json.getJSONObject("end");
		
		this.start = new Coordinate(start.getDouble("lat"), start.getDouble("lon"));
		this.end = new Coordinate(end.getDouble("lat"), end.getDouble("lon"));
		
		try {
			driverId = json.getInt("driver_id");
		} catch (JSONException e) {
		}
	}
	
	/**
	 * Constructor for implementing parcelable objects
	 * @param source The parcel containing the data for the trip
	 */
	public Trip(Parcel source) {
		this.id = source.readInt();
		this.driverId = source.readInt();
		this.start = (Coordinate) source.readParcelable(Coordinate.class.getClassLoader());
		this.end = (Coordinate) source.readParcelable(Coordinate.class.getClassLoader());
		this.cost = source.readDouble();
		source.readList(tags, String.class.getClassLoader());
	}
	
	public ApiRequest post(final Context context, final FutureCallback<String> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				JSONObject json = new JSONObject();
				Gson gson = new Gson();
				
				try {
					json.put("end_location", gson.toJsonTree(end));
					json.put("start_location", gson.toJsonTree(start));
					json.put("cost", cost);
					json.put("min_seats", minSeats);					
					CharSequence date = DateFormat.format("yyyy-MM-DD'T'hh:mm:ss.sss'Z'", startTime);
					json.put("start_time", date);
					
					if (driverId > 0) json.put("driver_id", driverId);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Ion.with(context).load("POST", baseURL + "/trips")
					.addHeader("X-AUTH-TOKEN", authToken)
					.setJsonObjectBody(json)
					.asString()
					.setCallback(cb);
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
			
		};
	}
	
	/******************************** GETTERS **************************/
	
	public Coordinate getStart() {
		return start;
	}
	
	public Coordinate getEnd() {
		return end;
	}
	
	/******************************** BUILDER **************************/
	
	public static class Builder {
		
		private double cost;
		private int userId;
		private int driverId;
		private Coordinate start;
		private Coordinate end;
		private int minSeats;
		private int numSeats;
		private Date startTime;
		private ArrayList<String> tags;
		
		public Builder setUserId(int userId) { this.userId = userId; return this; }
		public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
		public Builder setFee(double cost) { this.cost = cost; return this; }
		public Builder setStart(Coordinate start) { this.start = start; return this; }
		public Builder setEnd(Coordinate end) { this.end = end; return this; }
		public Builder setMinSeats(int minSeats) { this.minSeats = minSeats; return this; }
		public Builder setNumSeats(int numSeats) { this.numSeats = numSeats; return this; }
		public Builder setTags(ArrayList<String> tags) { this.tags = tags; return this; }
		public Builder setStartTime(Date startTime) { this.startTime = startTime; return this; }
		
		public Trip build() {
			if (start == null || end == null) {
				throw new IllegalArgumentException("Invalid Trip state: Trip missing parameters");
			}
			
			return new Trip(this);
		}
	}

	/******************************* Parcelable ******************************/
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeInt(driverId);
		dest.writeParcelable(start, 0);
		dest.writeParcelable(end, 0);
		dest.writeDouble(cost);
		dest.writeList(tags);
	}
	
	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {

		@Override
		public Trip createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Trip(source);
		}

		@Override
		public Trip[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Trip[size];
		}
	};
}
