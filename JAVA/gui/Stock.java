package gui;

import java.util.ArrayList;

public class Stock {
	private  ArrayList<Candle> arrCandles = new ArrayList<Candle>();
	private double min;
	private double max;
	
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public Stock() {
		
	}
	public void addCD(Candle c) {
		if(arrCandles.size()==0) {
			max=c.getHigh();
			min=max;
			
		}
		if(c.getHigh()>max) {
			max=c.getHigh();
		}
		if(c.getLow()<min) {
			min=c.getLow();
			
		}
		arrCandles.add(c);
	}
	public ArrayList<Candle> getArrCandles() {
		return arrCandles;
	}
	public void setArrCandles(ArrayList<Candle> arrCandles) {
		this.arrCandles = arrCandles;
	}
}
