package com.llc.bumpr.sdk.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.lib.Coordinate;

public class Trip implements Parcelable {

	/** The id of the trip */
	private int id;
	/** The driverId that the trip belongs to */
	private int driverId;
	/** The start of the trip */
	private Coordinate start;
	/** The end of the trip */
	private Coordinate end;
	/** The trip's fee */
	private double fee;
	
	/**
	 * Private constructor for the trip
	 * @param builder A builder class that builds the Trip
	 */
	private Trip(Builder builder) {
		this.fee = builder.fee;
		this.start = builder.start;
		this.end = builder.end;
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
		id = source.readInt();
		driverId = source.readInt();
		start = source.readParcelable(Coordinate.class.getClassLoader());
		end = source.readParcelable(Coordinate.class.getClassLoader());
		fee = source.readDouble();
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
		
		private double fee;
		private Coordinate start;
		private Coordinate end;
		
		public Builder setFee(double fee) { this.fee = fee; return this; }
		public Builder setStart(Coordinate start) { this.start = start; return this; }
		public Builder setEnd(Coordinate end) { this.end = end; return this; }
		
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
		dest.writeDouble(fee);
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
