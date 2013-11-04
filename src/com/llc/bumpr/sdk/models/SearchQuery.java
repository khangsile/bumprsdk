package com.llc.bumpr.sdk.models;

import com.llc.bumpr.sdk.lib.Coordinate;

public class SearchQuery {
	
	private float top;
	private float right;
	private float bottom;
	private float left;
	private double minFee;
	private int minSeats;
	
	public Coordinate getTopRight() {
		return new Coordinate(top, right);
	}
	
	public Coordinate getBottomLeft() {
		return new Coordinate(bottom, left);
	}
	
	public double getMinFee() {
		return minFee;
	}
	
	public int getMinSeats() {
		return minSeats;
	}
	
	public static class Builder<T extends SearchQuery> {
		T item;
		
		private float top;
		private float right;
		private float bottom;
		private float left;
		private double minFee;
		private int minSeats;
		
		public Builder(T item) { this.item = item; }
		public Builder<T> setTop(float top) { item.top = top; return this; }
		public Builder<T> setRight(float right) { item.right = right; return this; }
		public Builder<T> setBottom(float bottom) { item.bottom = bottom; return this; }
		public Builder<T> setLeft(float left) { item.left = left; return this; }
		public Builder<T> setMinFee(double minFee) { item.minFee = minFee; return this; }
		public Builder<T> setMinSeats(int minSeats) { item.minSeats = minSeats; return this; }
		
		public T build() { return item; }
	}
}
