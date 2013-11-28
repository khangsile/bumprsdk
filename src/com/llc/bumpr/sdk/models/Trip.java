package com.llc.bumpr.sdk.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.lib.Coordinate;

public class Trip implements Parcelable {

	/** The id of the trip */
	private int id;
	/** The userId that created the trip */
	private int userId;
	/** The driverId that the trip belongs to */
	private int driverId;
	/** The start of the trip */
	private Coordinate start;
	/** The end of the trip */
	private Coordinate end;
	/** The trip's fee */
	private double cost;
	/** The min seats for the driver */
	private int minSeats;
	/** The number of seats for the driver */
	private int numSeats;
	
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
		
		public Builder setUserId(int userId) { this.userId = userId; return this; }
		public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
		public Builder setFee(double cost) { this.cost = cost; return this; }
		public Builder setStart(Coordinate start) { this.start = start; return this; }
		public Builder setEnd(Coordinate end) { this.end = end; return this; }
		public Builder setMinSeats(int minSeats) { this.minSeats = minSeats; return this; }
		public Builder setNumSeats(int numSeats) { this.numSeats = numSeats; return this; }
		
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
