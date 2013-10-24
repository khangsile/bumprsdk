package com.llc.bumpr.sdk.lib;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinate implements Parcelable {
	public float latitude;
	public float longitude;
	
	public Coordinate(float longitude, float latitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Coordinate(Parcel source) {
		longitude = source.readFloat();
		latitude = source.readFloat();
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
		dest.writeFloat(longitude);
		dest.writeFloat(latitude);
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
