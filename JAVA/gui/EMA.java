package gui;

import java.util.ArrayList;

public class EMA extends Indicator {
	public EMA(int t) {
		this.setPeriod(t);
	}
	@Override
	public void calculate(ArrayList<Candle> c) {
		int t=this.getPeriod();
		double beta=2.0/(1+t);
		for (int i = 0; i < c.size(); i++)
        {
			int j = i;
            double suma = 0;
            
            if (i == 0) {
                this.addV(c.get(0).getClose());
            
            }
            else {
                int x = 0;
                
              // while ((i-j)!= t)
               //{
                	
                    suma = c.get(j).getClose()*beta+this.getValues().get(j-1)*(1.0-beta);
                    j--;
                    x++;
                    //if (j < 1)break;
              // }
            
            
                this.addV(suma);
            }
        }
	        
	}
	
	
	public static void main(String[] args) {
		Crawler C=new Crawler("GME","01.01.2021.","01.02.2021.");
		Stock S=C.parse();
		EMA x= new EMA(10);
		x.calculate(S.getArrCandles());
		for (int i = 0; i < x.getValues().size(); i++) {
			System.out.println(x.getValues().get(i));
		}
	}
}
