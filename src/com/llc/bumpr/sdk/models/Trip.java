package com.llc.bumpr.sdk.models;

import java.util.Date;

import android.graphics.PointF;

public class Trip {

	private int id;
	private int driverId;
	private PointF start;
	private PointF end;
	private double fee;
	
	public Trip(Builder builder) {
		this.fee = builder.fee;
		this.start = builder.start;
		this.end = builder.end;
	}
	
	/******************************** BUILDER **************************/
	
	public class Builder {
		
		private double fee;
		private PointF start;
		private PointF end;
		
		public Builder setFee(double fee) { this.fee = fee; return this; }
		public Builder setStart(PointF start) { this.start = start; return this; }
		public Builder setEnd(PointF end) { this.end = end; return this; }
		
		public Trip build() throws Exception {
			if (start == null || end == null) {
				throw new Exception("Invalid Trip state: Trip missing parameters");
			}
			
			return new Trip(this);
		}
	}
}
