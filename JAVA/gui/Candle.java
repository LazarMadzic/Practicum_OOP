package gui;

public class Candle {
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	private int time;
	private double high;
	private double low;
	private double open;
	private double close;
	
	public Candle(int t,double h,double l,double o,double c) {
		time=t;
		high=h;
		low=l;
		open=o;
		close=c;
		
	}
	@Override
	public String toString() {
		return "[" + time + " " + high + " " + low + " " + open + " " + close + "]";
	}
	
}
