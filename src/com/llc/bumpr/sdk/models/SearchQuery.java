package com.llc.bumpr.sdk.models;

public class SearchQuery {
	
	private double startLong;
	private double startLat;
	private double endLong;
	private double endLat;
	private double minFee;
	private int minSeats;
	
	public double getStartLong() {
		return startLong;
	}
	
	public double getStartLat() {
		return startLat;
	}
	
	public double getEndLong() {
		return endLong;
	}
	
	public double getEndLat() {
		return endLat;
	}
	
	public double getMinFee() {
		return minFee;
	}
	
	public int getMinSeats() {
		return minSeats;
	}
	
	public static class Builder<T extends SearchQuery> {
		T item;
		
		protected double startLong;
		protected double startLat;
		protected double endLong;
		protected double endLat;
		protected double minFee;
		protected int minSeats;
		
		public Builder(T item) { this.item = item; }
		public Builder<T> setStartLong(double startLong) { item.startLong = startLong; return this; }
		public Builder<T> setStartLat(double startLat) { item.startLat = startLat; return this; }
		public Builder<T> setEndLong(double endLong) { item.endLong = endLong; return this; }
		public Builder<T> setEndLat(double endLat) { item.endLat = endLat; return this; }
		public Builder<T> setMinFee(double minFee) { item.minFee = minFee; return this; }
		public Builder<T> setMinSeats(int minSeats) { item.minSeats = minSeats; return this; }
		
		public T build() { return item; }
	}
}
