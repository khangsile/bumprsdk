package com.llc.bumpr.sdk.lib;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinate implements Parcelable {
	public double lat;
	public double lon;
	
	public Coordinate(double longitude, double latitude) {
		this.lon = longitude;
		this.lat = latitude;
	}
	
	public Coordinate(Parcel source) {
		lon = source.readFloat();
		lat = source.readFloat();
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
