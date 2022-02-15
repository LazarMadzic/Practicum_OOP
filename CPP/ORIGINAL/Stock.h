#pragma once

#include <iostream>
#include <sstream>
#include <string>
#include <cstring>
#include <algorithm>
#include <ctime>
#include <time.h>
#include <vector>
#include <map>
#include <iomanip>
//#include "Parser.h"
#include "Candle.h"
#include "Indicator.h"
#include "MA.h"
#include "EMA.h"
class Stock
{
	std::string name;
	std::vector<Candle>data;
	//std::vector<Indicator>ind;
	Indicator* I=NULL;
	bool ready;
	public:
		Stock() {
			
		}
		std::vector<Candle>& getData() {
			return this->data;
		}
		void setName(std::string n) {
			this->name = n;
		}
		void setR(bool b) {
			this->ready = b;
		}
		bool getR() {
		
			return this->ready;
		}
		Candle getLast() {
			return data.back();
		
		}
		//Indicator* ind() {
		//	return new MA();
		//}
		void setI(int d,std::string s) {
			//Indicator *iih= new MA(d);
			if (s == "MA") {
				this->I = new MA(d);
			}
			else {
				this->I = new EMA(d);
			
			}
		}
		std::vector<Candle> getCa() {
			return this->data;
		
		}
		Indicator* getI() {
			return this->I;
		
		}
		//standard output
		friend std::ostream& operator<<(std::ostream& it, Stock &s) {
			if (s.I == NULL) {
				it << "\t   Time\t\t" << "     Low\t\t" << "   Open\t\t" << "\t Close\t\t" << "\tHigh" << std::endl;
				for (int i = 0; i < s.data.size(); i++)
				{
					if (s.getData()[i].getC() > s.getData()[i].getO()) {
						it << "\x1B[32m" << s.getData()[i] << "\033[0m\t\t" << '\n';
					}
					else {
						it << "\x1B[31m" << s.getData()[i] << "\033[0m\t\t" << '\n';

					}
				}
			}
			else {
				it << "\t   Time\t\t" << "     Low\t\t" << "   Open\t\t" << "\t Close\t\t" << "\tHigh" <<"    \t\t"<<s.I->getTy()<< std::endl;
				for (int i = 0; i < s.data.size(); i++)
				{
					if (s.getData()[i].getC() > s.getData()[i].getO()) {
						it << "\x1B[32m" << s.getData()[i] << "\033[0m\t\t" << "    "<<s.I->getD(i)<<'\n';
					}
					else {
						it << "\x1B[31m" << s.getData()[i] << "\033[0m\t\t" << "    " << s.I->getD(i) << '\n';

					}
				}
			
			
			
			
			}


			return it ;
		}

		
		
};

