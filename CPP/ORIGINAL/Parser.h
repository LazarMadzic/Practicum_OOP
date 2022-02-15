#pragma once
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
#include "Stock.h"
#include "Candle.h"
class Parser
{
std::string raw;

public:
	
	Parser(){}
	void setPRaw(std::string r) {
		this->raw = r;
	}
	void pars(Stock &s) { 
		std::string delim_s_t = "\"timestamp\":[";
		std::string delim_e_t = "],\"indicators\":";

		std::string delim_s_l ="\"low\":[" ;
		
		std::string delim_s_c ="\"close\":[" ;
		
		
		
		std::string delim_s_o = "\"open\":[";

		std::string delim_s_h = "\"high\":[";
		
		std::string::size_type size;
		//finds timestamps
		unsigned fi = this->raw.find(delim_s_t);
		unsigned la = this->raw.find(delim_e_t);
		std::string strNew = raw.substr(fi+13, la - fi-13);
		//std::cout << strNew << std::endl<<std::endl;
		strNew = strNew + ",";
		std::replace(strNew.begin(), strNew.end(), ',', ' ');
		//std::cout << strNew << std::endl << std::endl;
		Candle* C = new Candle();
		auto start = 0U;
		
		auto end = strNew.find(" ");
		while (end!=std::string::npos) {
			long int x=std::stol(strNew.substr(start, end ));
			C->setT(x);
			s.getData().push_back(*C);
			C = new Candle();
			start = end + 1;

			end = strNew.find(" ", start);
		}
		

		//system("pause");
		//finds low
		fi = this->raw.find(delim_s_l);
		la = this->raw.find_first_of("]",fi+1);
		strNew = raw.substr(fi + 7, la - fi - 7);
		//std::cout << strNew << std::endl<<std::endl;
		std::replace(strNew.begin(), strNew.end(), ',', ' ');
		//std::cout << strNew << std::endl << std::endl;

		start = 0U;
		end = strNew.find(" ");
		for (int i = 0; i < s.getData().size();i++) {
			long double y = std::stold(strNew.substr(start, end - 1), &size);
			
			s.getData()[i].setL(y);
			
			start = end + 1;
			end = strNew.find(" ", start);
		}

		//find high
		fi = this->raw.find(delim_s_h);
		la = this->raw.find_first_of("]", fi + 1);
		strNew = raw.substr(fi + 8, la - fi - 8);
		//std::cout << strNew << std::endl << std::endl;
		std::replace(strNew.begin(), strNew.end(), ',', ' ');
		//std::cout << strNew << std::endl << std::endl;
		start = 0U;
		end = strNew.find(" ");
		for (int i = 0; i < s.getData().size(); i++) {
			long double y = std::stold(strNew.substr(start, end - 1), &size);

			s.getData()[i].setH(y);

			start = end + 1;
			end = strNew.find(" ", start);
		}

		//find open
		fi = this->raw.find(delim_s_o);
		la = this->raw.find_first_of("]", fi + 1);
		strNew = raw.substr(fi + 8, la - fi - 8);
		//std::cout << strNew << std::endl << std::endl;
		std::replace(strNew.begin(), strNew.end(), ',', ' ');
		//std::cout << strNew << std::endl << std::endl;
		start = 0U;
		end = strNew.find(" ");
		for (int i = 0; i < s.getData().size(); i++) {
			long double y = std::stold(strNew.substr(start, end - 1), &size);

			s.getData()[i].setO(y);

			start = end + 1;
			end = strNew.find(" ", start);
		}

		//find close
		fi = this->raw.find(delim_s_c);
		la = this->raw.find_first_of("]", fi + 1);
		strNew = raw.substr(fi + 9, la - fi - 9);
		//std::cout << strNew << std::endl << std::endl;
		std::replace(strNew.begin(), strNew.end(), ',', ' ');
		//std::cout << strNew << std::endl << std::endl;
		start = 0U;
		end = strNew.find(" ");
		for (int i = 0; i < s.getData().size(); i++) {
			long double y = std::stold(strNew.substr(start, end - 1),&size);

			s.getData()[i].setC(y);

			start = end + 1;
			end = strNew.find(" ", start);
		}

		/*std::vector<Candle>::iterator itr;
		for (itr = s.getData().begin(); itr != s.getData().end(); ++itr)
		{
			std::cout << '\t' << *itr << '\n';
		}*/
	}
	
};

