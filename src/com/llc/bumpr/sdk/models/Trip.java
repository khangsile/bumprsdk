package com.llc.bumpr.sdk.models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.llc.bumpr.sdk.lib.ApiRequest;
import com.llc.bumpr.sdk.lib.Location;

/**
 * Model that represents a trip
 * @author KhangSiLe
 *
 */
public class Trip implements Parcelable {

	/** The id of the trip */
	@Expose(serialize=false, deserialize=true)
	private int id;
	
	/** The driverId that the trip belongs to */
	@SerializedName("driver_id")
	private int driverId;
	
	/** The start of the trip */
	@Expose()
	@SerializedName("start_location")
	private Location start;
	
	/** The end of the trip */
	@Expose()
	@SerializedName("end_location")
	private Location end;
	
	/** The trip's fee */
	@Expose()
	private double cost;
	
	/** The start date of the trip **/
	@Expose()
	@SerializedName("start_time")
	private Date startTime;
	
	/** The min seats for the driver */
	@Expose()
	@SerializedName("min_seats")
	private int minSeats;
	
	/** The number of seats for the driver */
	@Expose(serialize=false)
	@SerializedName("num_seats")
	private int numSeats;
	
	/** The tags of trip */
	@Expose()
	@SerializedName("tag_list")
	private ArrayList<String> tags;
	
	/** The owner of the trip */
	@Expose(serialize=false, deserialize=true)
	@SerializedName("user")
	private User owner;
	
	/** The users involved in the trip */
	@Expose(serialize=false, deserialize=true)
	private ArrayList<User> users;
	
	
	/************************************* STATIC METHODS ****************************/
	
	public static ApiRequest getTrips(final Context context, final FutureCallback<List<Trip>> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load(baseURL + "/trips.json")
				.setHeader("X-AUTH-TOKEN", authToken)
				.asString()
				.setCallback(new FutureCallback<String>() {

					@Override
					public void onCompleted(Exception arg0, String arg1) {
						List<Trip> trips = null;
						if (arg0 == null) {
							Type type = new TypeToken<List<Trip>>(){}.getType();
												
							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD hh:mm:ss 'UTC'").create();
							trips = gson.fromJson(arg1, type);
						}
						cb.onCompleted(arg0, trips);
					}
				});
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
		};
	}
	
	public ApiRequest getTrip(final Context context, final int id, final FutureCallback<Trip> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {
				Ion.with(context).load(baseURL + "/trips.json")
				.setHeader("X-AUTH-TOKEN", authToken)
				.asString()
				.setCallback(new FutureCallback<String>() {

					@Override
					public void onCompleted(Exception arg0, String arg1) {
						Trip trip = null;
						if (arg0 == null) {												
							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD hh:mm:ss 'UTC'").create();
							trip = gson.fromJson(arg1, Trip.class);
						}
						cb.onCompleted(arg0, trip);
					}
				});
			}

			@Override
			public boolean needsAuth() {
				return true;
			}
			
		};
	}
	
	/************************************* INSTANCE **********************************/
	
	/**
	 * Private constructor for the trip
	 * @param builder A builder class that builds the Trip
	 */
	private Trip(Builder builder) {
		this.driverId = builder.driverId;
		this.cost = builder.cost;
		this.start = builder.start;
		this.end = builder.end;
		this.minSeats = builder.minSeats;
		this.numSeats = builder.numSeats;
		this.startTime = builder.startTime;
		this.tags = builder.tags;
	}
	
	/**
	 * Constructor for reading from JSONObject
	 * @param json The JSON representation of the trip object
	 * @throws JSONException
	 */
	@Deprecated()
	public Trip(JSONObject json) throws JSONException {
		JSONObject start = json.getJSONObject("start");
		JSONObject end = json.getJSONObject("end");
		
		this.start = new Location(start.getDouble("lat"), start.getDouble("lon"));
		this.end = new Location(end.getDouble("lat"), end.getDouble("lon"));
		
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
		this.start = (Location) source.readParcelable(Location.class.getClassLoader());
		this.end = (Location) source.readParcelable(Location.class.getClassLoader());
		this.cost = source.readDouble();
		this.tags = new ArrayList<String>();
		source.readStringList(tags);
		this.owner = (User) source.readParcelable(User.class.getClassLoader());
		this.numSeats = source.readInt();
		this.minSeats = source.readInt();
		source.readList(users, User.class.getClassLoader());
	}
	
	public ApiRequest post(final Context context, final FutureCallback<String> cb) {
		return new ApiRequest() {

			@Override
			public void execute(String baseURL, String authToken) {			
				Gson gson = new Gson();
				JsonObject json = new JsonObject(); //gson.toJsonTree(this);
				
				json.add("end_location", gson.toJsonTree(end));
				json.add("start_location", gson.toJsonTree(start));
				json.add("cost", new JsonPrimitive(cost));
				json.add("min_seats", new JsonPrimitive(minSeats));					
				CharSequence date = DateFormat.format("yyyy-MM-dd'T'hh:mm:ss.sss'Z'", startTime);
				json.add("start_time", new JsonPrimitive(date.toString()));
					
				if (driverId > 0) json.add("driver_id", new JsonPrimitive(driverId));
				
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
	
	public int getId() {
		return id;
	}
	
	public Date getDate() {
		return startTime;
	}
	
	public double getCost() {
		return cost;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
	
	public int getMinSeats() {
		return minSeats;
	}
	
	public Location getStart() {
		return start;
	}
	
	public Location getEnd() {
		return end;
	}
	
	public User getOwner() {
		return owner;
	}
	
	/******************************** BUILDER **************************/
	
	public static class Builder {
		
		private double cost;
		private int userId;
		private int driverId;
		private Location start;
		private Location end;
		private int minSeats;
		private int numSeats;
		private Date startTime;
		private ArrayList<String> tags;
		
		public Builder setUserId(int userId) { this.userId = userId; return this; }
		public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
		public Builder setFee(double cost) { this.cost = cost; return this; }
		public Builder setStart(Location start) { this.start = start; return this; }
		public Builder setEnd(Location end) { this.end = end; return this; }
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
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(driverId);
		dest.writeParcelable(start, 0);
		dest.writeParcelable(end, 0);
		dest.writeDouble(cost);
		dest.writeStringList(tags);
		dest.writeParcelable(owner, 0);
		dest.writeInt(numSeats);
		dest.writeInt(minSeats);
		dest.writeList(users);
	}
	
	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {

		@Override
		public Trip createFromParcel(Parcel source) {
			return new Trip(source);
		}

		@Override
		public Trip[] newArray(int size) {
			return new Trip[size];
		}
	};
}
