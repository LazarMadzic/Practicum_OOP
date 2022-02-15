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


int main(void)
{
	Crawler* CC = new Crawler();
	CC->setReady(false);
	Parser* PP = new Parser();
	Stock* SS = new Stock();
	//load all users from txt
	std::map<std::string, std::string>::iterator itr;
	std::map<std::string, std::string>f_users;
	std::fstream if_users;
	if_users.open("users.txt");//std::ios::out | std::ios::app 
	User* Cur_User = NULL;//pointer to user obj

	std::string a, b;
	//read from file to map, line by line
	while (if_users >> a >> b) {
		f_users.insert(std::pair<std::string, std::string>(a, b));
	}
	if_users.close();
	std::ofstream of_users;
	while (1) {//login loop
		bool log_flag = false;
		int m = option_log();
		//log in
		if (m == 1) {
			std::string x, y;
			try
			{//check validity
				std::cout << "Name: ";
				std::cin >> x;
				std::cout << std::endl;
				std::cout << "Pass: ";
				std::cin >> y;
				itr = f_users.find(x);
				if (itr == f_users.end())throw 1;
				else {
					if (itr->second != y)throw 2;
					Cur_User = new User(x);
					Cur_User->load();
				}
			}
			//ERRORS
			catch (int e)
			{
				switch (e)
				{
				case 1:
					std::cout << "Wrong credentials" << std::endl;
					system("pause");
					break;
				case 2:
					std::cout << "Wrong password" << std::endl;
					system("pause");
					break;
				default:
					break;
				}

			}
		}
		//register
		else if (m == 2) {
			std::string xx, yy, zz;
			itr = f_users.find(xx);
			try
			{
				std::cout << "Name: ";
				std::cin >> xx;
				std::cout << std::endl;
				std::cout << "Pass: ";
				std::cin >> yy;

				itr = f_users.find(xx);
				if (itr != f_users.end())throw 1;
				else {
					f_users.insert(std::pair<std::string, std::string>(xx, yy));
					Cur_User = new User(xx);
					std::cout << "Funds: ";
					std::cin >> zz;
					std::cout << std::endl;
					Cur_User->setFunds(stod(zz));

				}
			}
			//ERRORS
			catch (int e)
			{
				switch (e)
				{
				case 1:
					std::cout << "Taken username" << std::endl;
					system("pause");
					break;
					/*case 2:
						std::cout << "Wrong password" << std::endl;
						break;*/
				default:
					break;
				}

			}
		}
		//EXIT
		else if (m == 0) {
			//checks and exit
			std::ofstream of_users;
			for (itr = f_users.begin(); itr != f_users.end(); ++itr)//vrite user name and pass to file
			{
				of_users << itr->first << " " << itr->second << '\n';
			}
			system("clear");
			std::cout << "\n Kraj \n";
			exit(1);
		}
		else {
			std::cout << "\n-!- Van opsega -!-\n";
		}

		if (Cur_User) {//proverava da li je login uspeo i ako jeste krece sa radom ako nije vraca se na login

			of_users.open("users.txt");
			while (1) {//main loop
				int n = 0;
				//print options
				n = option_main();
				if (n < 0 || n>5) {
					system("clear");
					std::cout << "\n-!- Van opsega -!-\n";
					system("pause");
				}
				else {
					std::string sname, st, ed;
					/*if (n == 1) {
					
					
					}else if (n == 2) {


					}
					else if (n == 3) {


					}
					else if (n == 4) {


					}
					else if (n == 5) {


					}*/
					

					switch (n){
						case 1://gather data
							try {
								std::cout << "\nEnter stock ticker" << std::endl;
								std::cin >> sname;
								std::cout << "\nEnter start of period(dd.mm.yyyy.)" << std::endl;
								std::cin >> st;
								std::cout << "\nEnter end of period (dd.mm.yyyy.)" << std::endl;
								std::cin >> ed;
								CC->setRaw(sname, st, ed);
								if (!CC->getReady())throw 1;
							}
							catch (int i) {
								switch (i)
								{
								case 1:
									std::cout << "ERROR fetching data" << std::endl;
									break;
								case 2:
									std::cout << "ERROR first date not valid" << std::endl;
									break;
								case 3:
									std::cout << "ERROR second date not valid" << std::endl;
									break;
								case 4:
									std::cout << "ERROR second date is before first" << std::endl;
									break;
								default:
									break;
								}
							}
							system("pause");
							break;
						case 2://show data
							try {

								std::cout << "\nEnter stock ticker" << std::endl;
								std::cin >> sname;
								std::cout << "\nEnter start of period(dd.mm.yyyy.)" << std::endl;
								std::cin >> st;
								std::cout << "\nEnter end of period (dd.mm.yyyy.)" << std::endl;
								std::cin >> ed;
								CC->setRaw(sname, st, ed);
								//CC->setRaw("TSLA", "1.4.2021.", "5.4.2021.");
								if (!CC->getReady())throw 1;
								PP->setPRaw(CC->getRaw());
								PP->pars(*SS);
								std::cout << "\nDo you want and indicator(MA/EMA/No)" << std::endl;
								std::cin >> sname;
								
								if (sname == "MA") {
									std::cout << "\nPeriod size" << std::endl;
									int broj = 24;
									//sname = "EMA";
									std::cin >> broj;
									SS->setI(broj,sname);
									SS->getI()->calc(SS->getCa());
									std::cout << *SS;
								}
								else if (sname == "EMA") {
									std::cout << "\nPeriod size" << std::endl;
									int broj = 24;
									//sname = "EMA";
									std::cin >> broj;
									SS->setI(broj,sname);
									SS->getI()->calc(SS->getCa());
									std::cout << *SS;
								}
								else if (sname == "No") {
									std::cout << *SS;
								
								}
								else throw 5;
								system("pause");
								break;
							}
							catch (int i) {
								switch (i)
								{
								case 1:
									std::cout << "ERROR fetching data" << std::endl;
									break;
								case 2:
									std::cout << "ERROR first date not valid" << std::endl;
									break;
								case 3:
									std::cout << "ERROR second date not valid" << std::endl;
									break;
								case 4:
									std::cout << "ERROR second date is before first" << std::endl;
									break;
								case 5:
									std::cout << "ERROR non existant indicator" << std::endl;
									break;
								default:
									break;
								}
							}
							system("pause");
							break;
						case 3://buy sell 
							try {
								std::string cc;
								std::cout << "(B)uy or (S)ell" << std::endl;
								std::cin >> cc;
								//if (cc == 'B' && cc != 'S') throw 1;
								Cur_User->BS(cc);
							}
							catch (int i) {
								switch (i)
								{
									/*case 1:
										std::cout << "Wrong command" << std::endl;
										break;*/
								case 2:
									std::cout << "Not enough funds to buy" << std::endl;
									break;
								case 3:
									std::cout << "Not enough stocks to sell" << std::endl;
									break;
								case 4:
									std::cout << "Wrong command" << std::endl;
									break;
								default:
									break;
								}
								//system("pause");
							}
							system("pause");
							break;
						case 4://show portfolio
							std::cout << *Cur_User;
							system("pause");
							break;
						case 5://log out
							Cur_User->save();
							delete (Cur_User);
							Cur_User = NULL;
							log_flag = false;
							break;
						case 0://exit and save credentials
							Cur_User->save();
							for (itr = f_users.begin(); itr != f_users.end(); ++itr)
							{
								of_users << itr->first << " " << itr->second << '\n';
							}
							delete(Cur_User);
							of_users.close();
							exit(1);
							break;
						default:
							break;
						}

					}
					if (Cur_User == NULL) {
						break;
					}
				}
			}
		}
		Cur_User->save();
		for (itr = f_users.begin(); itr != f_users.end(); ++itr)
		{
			of_users << itr->first << " " << itr->second << '\n';
		}
		of_users.close();

	return 0;
}