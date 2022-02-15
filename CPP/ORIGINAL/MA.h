#ifndef MA_h
#define MA_H
#include <vector>
#include "Indicator.h"
class MA :
    public Indicator
{   
    //double val;
public:
    MA(int f) {
        //this->setT(i);
        this->setTy("MA");
        //this->val = f;
        this->setT(f);
    }
    void calc(std::vector<Candle> c) override {
        /*for (std::vector<Candle>::reverse_iterator i = c.rbegin(); i != c.rend(); ++i) {
            int x = 0;
            double suma = 0;
            std::vector<Candle>::reverse_iterator j = i;
            while (j != c.rend() || x < this->getT()) {
                suma = suma + (*i).getC();
                j++;
                x++;
            }
            this.
        }*/
        int h = this->getT();
        for (int i = 0; i < c.size(); i++)
        {
            int j = i;
            double suma = 0;
            if (i == 0) {
                this->pushD(c[0].getC());
            
            }
            else {
                int x = 0;
                while ((i-j)!= h)
                {
                    suma = suma + c[j].getC();
                    j--;
                    x++;
                    if (j < 0)break;
                }
            
            
                 this->pushD(suma / x);
            }
        }
    }
};

#endif // !MA_h
