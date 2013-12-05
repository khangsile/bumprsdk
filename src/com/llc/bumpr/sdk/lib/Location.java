package com.llc.bumpr.sdk.lib;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A class to store coordinates on a map
 * @author KhangSiLe
 *
 */
public class Location implements Parcelable {
	
	/** latitude **/
	@Expose
	@SerializedName("latitude")
	public double lat;
	
	/** longitude **/
	@Expose
	@SerializedName("longitude")
	public double lon;
	
	/** title of location **/
	@Expose
	public String title = null;
	
	public Location() {}
	
	/** 
	 * Basic constructor
	 * @param latitude the latitude of the coordinate
	 * @param longitude the longitude of the coordinate
	 */
	public Location(double latitude, double longitude) {
		this.lon = longitude;
		this.lat = latitude;
	}
	
	/**
	 * Set the latitude
	 * @param latitude the latitude of the coordinate
	 * @return the object being modified
	 */
	public Location setLatitude(double latitude) {
		this.lat = latitude;
		return this;
	}
	
	/**
	 * Set the longitude
	 * @param longitude the longitude of the coordinate
	 * @return the object being modified
	 */
	public Location setLongitude(double longitude) {
		this.lon = longitude;
		return this;
	}
	
	/**
	 * Set the title
	 * @param title the title of the coordinate
	 * @return the object you are modifying
	 */
	public Location setTitle(String title) {
		this.title = title;
		return this;
	}
	
	/**
	 * Constructor to implement Parcelable
	 * @param source the Parcel source 
	 */
	public Location(Parcel source) {
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
	
	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel source) {
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
		
	};
}
