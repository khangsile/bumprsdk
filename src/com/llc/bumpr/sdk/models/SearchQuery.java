package com.llc.bumpr.sdk.models;

import com.llc.bumpr.sdk.lib.Location;

public class SearchQuery {
	
	private double top;
	private double right;
	private double bottom;
	private double left;
	private double minFee;
	private int minSeats = 2;
	
	public Location getTopRight() {
		return new Location(top, right);
	}
	
	public Location getBottomLeft() {
		return new Location(bottom, left);
	}
	
	public double getMinFee() {
		return minFee;
	}
	
	public int getMinSeats() {
		return minSeats;
	}
	
	public static class Builder<T extends SearchQuery> {
		T item;
		
		private double top;
		private double right;
		private double bottom;
		private double left;
		private double minFee;
		private int minSeats;
		
		public Builder(T item) { this.item = item; }
		public Builder<T> setTop(double top) { item.top = top; return this; }
		public Builder<T> setRight(double right) { item.right = right; return this; }
		public Builder<T> setBottom(double bottom) { item.bottom = bottom; return this; }
		public Builder<T> setLeft(double left) { item.left = left; return this; }
		public Builder<T> setMinFee(double minFee) { item.minFee = minFee; return this; }
		public Builder<T> setMinSeats(int minSeats) { item.minSeats = minSeats; return this; }
		
		public T build() { return item; }
	}
}
