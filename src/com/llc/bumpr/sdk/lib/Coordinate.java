package com.llc.bumpr.sdk.lib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class to store coordinates on a map
 * @author KhangSiLe
 *
 */
public class Coordinate implements Parcelable {
	
	/** latitude **/
	public double lat;
	
	/** longitude **/
	public double lon;
	
	/** title of location **/
	public String title;
	
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(lon);
		dest.writeDouble(lat);
		dest.writeString(title);
	}
	
	public static final Parcelable.Creator<Coordinate> CREATOR = new Parcelable.Creator<Coordinate>() {

		@Override
		public Coordinate createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Coordinate(source);
		}

		@Override
		public Coordinate[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Coordinate[size];
		}
		
	};
}
