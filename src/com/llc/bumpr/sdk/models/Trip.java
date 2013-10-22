package com.llc.bumpr.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.llc.bumpr.sdk.lib.Coordinate;

public class Trip implements Parcelable {

	private int id;
	private int driverId;
	private Coordinate start;
	private Coordinate end;
	private double fee;
	
	public Trip(Builder builder) {
		this.fee = builder.fee;
		this.start = builder.start;
		this.end = builder.end;
	}
	
	public Trip(Parcel source) {
		id = source.readInt();
		driverId = source.readInt();
		start = source.readParcelable(Coordinate.class.getClassLoader());
		end = source.readParcelable(Coordinate.class.getClassLoader());
		fee = source.readDouble();
	}
	
	/******************************** BUILDER **************************/
	
	public class Builder {
		
		private double fee;
		private Coordinate start;
		private Coordinate end;
		
		public Builder setFee(double fee) { this.fee = fee; return this; }
		public Builder setStart(Coordinate start) { this.start = start; return this; }
		public Builder setEnd(Coordinate end) { this.end = end; return this; }
		
		public Trip build() throws Exception {
			if (start == null || end == null) {
				throw new Exception("Invalid Trip state: Trip missing parameters");
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
	
	public class Creator implements Parcelable.Creator<Trip> {

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
	}
}
