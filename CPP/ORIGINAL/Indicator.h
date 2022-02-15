#pragma once
#ifndef Indicator_h
#define Indicator_h
#include <string>
#include <vector>
#include "Candle.h"
class Indicator
{
	std::vector<double> vals;
	int time;
	std::string type;
public:
	void setT(int i) { this->time = i; };
	void setTy(std::string s) {this->type=s;};
	int getT() { return time; }
	void pushD(double d) {
		this->vals.push_back(d);
	
	}
	double getD(int i) {
		return this->vals[i];
	
	}
	std::string getTy() { return type; }
	virtual void calc(std::vector<Candle> c)=0;
};
#endif // !Indicator_h

