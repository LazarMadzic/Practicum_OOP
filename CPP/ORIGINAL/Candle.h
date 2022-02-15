#pragma once
#ifndef Candle_h
#define Candle_h
#include <iostream>
#include <sstream>
#include <string>
#include <cstring>
#include<algorithm>
#include <ctime>
#include <time.h>
#include <vector>
#include <map>
#include <iomanip>


class Candle
{
	//Indicator ind[2];
	long int time;
	long double h;
	long double l;
	long double o;
	long double c;
public:
	void setT(long int tt) {
		this->time = tt;
	}
	void setH(long double hh) {
		this->h = hh;
	}
	void setL(long double ll) {
		this->l = ll;
	}
	void setO(long double oo) {
		this->o = oo;
	}
	void setC(long double cc) {
		this->c = cc;
	}
	long double getO() {
		return this->o;
	}
	long double getC() {
		return this->c;
	}
	long int getT() {
		return this->time;
	}
	std::string toDate(int x) {
		int sat = x % 3600;
	
	}
	friend std::ostream& operator<<(std::ostream& it, const Candle& c) {
		//it.width(5);
		return it << std::setprecision(17) << std::setw(18) <<  c.time <<"    " << std::setw(18) <<c.l <<"    " << std::setw(18) << c.o << "    " << std::setw(18) << c.c << "    " << std::setw(18) << c.h ;
	}

	//
};

#endif // !Candle_h
