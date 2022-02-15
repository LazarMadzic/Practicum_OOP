package gui;

import java.awt.*;

public class Transaction extends Panel{
	private String ticker;
	private int volume;
	private String ID;
	private double price;
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder(ID);
		sb.append("   ");
		sb.append(ticker);
		sb.append("   ");
		sb.append(volume);
		sb.append("   ");
		sb.append(price);
		return sb.toString();
	}

	public Transaction(String s, String n,int v,double p) {
		this.setID(new String(s));
		this.setTicker(new String(n));
		this.setVolume(v);
		this.setPrice(p);
		
	}
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
