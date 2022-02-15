#pragma once
#ifndef Transaction_h
#define Transaction_h
#include <iostream>
#include <string>
#include <conio.h>
#include <fstream>
#include <string> 
#include <map> 
#include <utility> 
#include <iterator>
#include<algorithm>
#include <iomanip>

class Transaction
{
	std::string name;
	std::string ID;
	int vol;
	double price;
public:
	Transaction(std::string s, std::string n, int i ,double d):ID(s),name(n),vol(i),price(d) {
	
	}
	std::string getID() {
		return this->ID;
	}
	std::string getName() {
		return this->name;
	}
	int getVol() {
		return vol;
	}
	double getPr() {
		return this->price;
	}
	void setVol(int v) {
		vol = v;
		
	}
	friend std::ostream& operator<<(std::ostream& it, Transaction& t) {
		std::string s = t.getID();
		
			it << t.getID() << "    " << t.getName() << "    " << t.getVol() << "    " << t.getPr() ;
		
		
		
		return it;
	}

};

#endif // !Transaction_h
