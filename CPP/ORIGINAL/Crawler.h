#pragma once
#pragma warning(disable:4996)
#ifndef Crawler_h
#define Crawler_h
#define CURL_STATICLIB

#include <curl/curl.h>
#include <iostream>
#include <sstream>
#include <string>
#include <cstring>
#include<algorithm>
#include <ctime>
#include <time.h>
#include <iomanip>

class Crawler
{
	std::string raw;
	bool ready;
public:
	std::string getRaw() {
		return raw;
	}
	static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp)
	{
		((std::string*)userp)->append((char*)contents, size * nmemb);
		return size * nmemb;
	}
	//check date format
	void checkF(std::string s, int x) {
		int year, month, day;
		char str[256];
		bool flag = false;
		strcpy(str, s.c_str());
		std::replace(str, str + strlen(str), '.', ' ');
		std::istringstream(str) >> day >> month >> year;
		//check if valid date 
		if (1000 <= year <= 3000) {
			if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 0 && day <= 31) {
				flag = false;
			}
			else if (month == 4 || month == 6 || month == 9 || month == 11 && day > 0 && day <= 30) {
				flag = false;
			}
			else if (month == 2) {
				if ((year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) && day > 0 && day <= 29) {
					flag = false;
				}
				else if (day > 0 && day <= 28) {
					flag = false;
				}
				else flag = true;
			}
			else flag = true;
			
		}
		if (x == 1 && flag == true) {
			throw 2;
		}
		if (x == 2 && flag == true) {
			throw 3;
		}
	}
	//time string to tm struct to seconds
	int strTotm(std::string s) {

		time_t rawtime;
		struct tm timeinfo = {0};
		int year, month, day;
		char str[256];
		strcpy(str, s.c_str());
		std::replace(str, str + strlen(str), '.', ' ');
		std::istringstream(str) >> day >> month >> year;

		//time(&rawtime);
		//timeinfo = localtime(&rawtime);
		timeinfo.tm_year = year - 1900;
		timeinfo.tm_mon = month - 1;
		timeinfo.tm_mday = day;
		mktime(&timeinfo);

		std::time_t timef = std::mktime(&timeinfo);
		return timef;
	}
	void setRaw(std::string stock, std::string start, std::string end) {
		std::string s = stock;
		std::transform(s.begin(), s.end(), s.begin(),
			[](unsigned char c) { return std::tolower(c); });//everything to lower
		//start & end da formatiram u tm time
		
		checkF(start, 1);
		int b = strTotm(start);

		checkF(end, 2);
		int e= strTotm(end);
		//std::cout << b << std::endl;
		//std::cout << e << std::endl;
		//std::cout << (e-b)/(60*60*24) << std::endl;
		
		
		if ((e - b) < 0)throw 4;
		std::string fh = "https://query1.finance.yahoo.com/v8/finance/chart/";
		fh = fh + s;
		std::string sh1 = "?period1="+std::to_string(b);
		std::string sh2 = "&period2=" + std::to_string(e);
		std::string la = "&interval=1h";
		fh = fh + sh1 + sh2 + la;
		//std::string readBuffer;
		//std::cout << fh << std::endl;
		
		CURL* curl;
		CURLcode res;
		std::string readBuffer;
		curl = curl_easy_init();
		if (curl) {
			curl_easy_setopt(curl, CURLOPT_URL, fh.c_str());
			curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
			curl_easy_setopt(curl, CURLOPT_WRITEDATA, &readBuffer);
			res = curl_easy_perform(curl);
			curl_easy_cleanup(curl);
		}
		//std::cout << readBuffer << std::endl;
		this->raw = readBuffer;
		//std::cout << this->getRaw() << std::endl;

		std::size_t found = this->raw.find("currency");
		std::string ss = this->raw.substr(found, found + 15);
		std::size_t fo = ss.find("null");
		if (fo == std::string::npos) {
			this->setReady(true);
		}else this->setReady(false);
		
	}
	
	void setReady(bool t) {
		this->ready = t;
	}
	bool getReady() {
		return this->ready;
	}
};

#endif // !Crawler_h
