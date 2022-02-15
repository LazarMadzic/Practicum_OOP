#ifndef EMA_h
#define EMA_H
#include <vector>
#include "Indicator.h"
class EMA :
    public Indicator
{
public:
    EMA(int f) {
        //this->setT(i);
        this->setTy("EMA");
        //this->val = f;
        this->setT(f);
    }
    /*double rac(std::vector<Candle> c, int &pos) {
        if (pos == 0) {
            return this->getD();
        
        }else 
        
    }*/
    void calc(std::vector<Candle> c) override {
        int h = this->getT();
        double suma = 0;
        for (int i = 0; i < c.size(); i++)
        {
            int j = i;
            if (i == 0) {
                this->pushD(c[0].getC());

            }
            else {
                int x = 0;
                suma = 0;
                while ((i - j) != h)
                {
                    double xx = 2.0 / (j + 2);
                    double pom= ((c[j].getC()) * (xx)) + (this->getD(j - 1)) * (1.0 - xx);
                    suma = suma + pom;
                    j--;
                    x++;
                    if (j <= 0)break;
                }

                this->pushD(suma/x);
            }
        }
    }
};
#endif // !MA_h
