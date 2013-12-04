package com.llc.bumpr.sdk.lib;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * A class to store coordinates on a map
 * @author KhangSiLe
 *
 */
public class Coordinate implements Parcelable {
	
	/** latitude **/
	@SerializedName("latitude")
	public double lat;
	
	/** longitude **/
	@SerializedName("longitude")
	public double lon;
	
	/** title of location **/
	public String title = null;
	
	public Coordinate() {}
	
	/** 
	 * Basic constructor
	 * @param latitude the latitude of the coordinate
	 * @param longitude the longitude of the coordinate
	 */
	public Coordinate(double latitude, double longitude) {
		this.lon = longitude;
		this.lat = latitude;
	}
	
	/**
	 * Set the latitude
	 * @param latitude the latitude of the coordinate
	 * @return the object being modified
	 */
	public Coordinate setLatitude(double latitude) {
		this.lat = latitude;
		return this;
	}
	
	/**
	 * Set the longitude
	 * @param longitude the longitude of the coordinate
	 * @return the object being modified
	 */
	public Coordinate setLongitude(double longitude) {
		this.lon = longitude;
		return this;
	}
	
	/**
	 * Set the title
	 * @param title the title of the coordinate
	 * @return the object you are modifying
	 */
	public Coordinate setTitle(String title) {
		this.title = title;
		return this;
	}
	
	/**
	 * Constructor to implement Parcelable
	 * @param source the Parcel source 
	 */
	public Coordinate(Parcel source) {
		lon = source.readDouble();
		lat = source.readDouble();
		title = source.readString();
	}
	
	/************************* PARCELABLE ********************/
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(lon);
		dest.writeDouble(lat);
		dest.writeString(title);
	}
	
	public static final Parcelable.Creator<Coordinate> CREATOR = new Parcelable.Creator<Coordinate>() {

		@Override
		public Coordinate createFromParcel(Parcel source) {
			return new Coordinate(source);
		}

		@Override
		public Coordinate[] newArray(int size) {
			return new Coordinate[size];
		}
		
	};
}
