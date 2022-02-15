package gui;

import java.util.ArrayList;

public abstract class Indicator {
	private ArrayList<Double> values=new ArrayList<Double>() ;
	private int period;
	
	public void addV(Double d) {
		values.add(d);
	}
	
	public ArrayList<Double> getValues() {
		return values;
	}

	public void setValues(ArrayList<Double> values) {
		this.values = values;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public abstract void calculate(ArrayList<Candle>c);
}
