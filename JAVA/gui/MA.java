package gui;

import java.util.ArrayList;

public class MA extends Indicator {

	public MA(int t) {
		this.setPeriod(t);
	}
	@Override
	public void calculate(ArrayList<Candle> c) {
		
		int t=this.getPeriod();
		for (int i = 0; i < c.size(); i++)
        {
            int j = i;
            double suma = 0;
            if (i == 0) {
                this.addV(c.get(0).getClose());
            
            }
            else {
                int x = 0;
                while ((i-j)!= t)
                {
                    suma = suma + c.get(j).getClose();
                    j--;
                    x++;
                    if (j < 0)break;
                }
            
            
                this.addV(suma/x);
            }
        }
	}

}
