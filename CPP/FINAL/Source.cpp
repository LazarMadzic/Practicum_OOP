#include <iostream>
#include <string>
#define CURL_STATICLIB
#include <curl/curl.h>
#include <conio.h>
#include <fstream>
#include <string> 
#include <map> 
#include <utility> 
#include <iterator>
#include "User.h"
#include "Crawler.h"
#include "Header.h"
#include "Parser.h"
#include<algorithm>
#include <iomanip>


int main(int argc, char* argv[])
{

	Crawler* CC = new Crawler();
	CC->setReady(false);
	//std::cout << argv[1] << " " << argv[2] << " " << argv[3] << std::endl;
	//system("pause");
	CC->setRaw(argv[1], argv[2], argv[3]);
	//CC->setRaw("GME", "01.06.2021.", "10.06.2021.");
	Parser* PP = new Parser();
	PP->setPRaw(CC->getRaw());
	PP->pars();
	//std::cout << CC->getRaw();
	return 0;
}