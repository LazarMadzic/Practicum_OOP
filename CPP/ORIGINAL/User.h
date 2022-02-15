#ifndef User_h
#define User_h
#include <fstream> 
#include <string> 
#include <map>
#include <iomanip>
#include "Crawler.h"
#include "Candle.h"
#include "Parser.h"
#include "Stock.h"
#include "Transaction.h"
#include <vector>
#include <time.h>
class User
{
	std::string name;
	double funds;
	std::map<std::string, int>stocks;
	std::vector<Stock>looking;
	std::vector<Transaction>logs;
public:
	void setName(std::string s) {
		this->name = s;
	}
	std::string getName() {
		return this->name;
	}
	void setFunds(double f) {
		this->funds = f;
	}
	double getFunds() {
		return this->funds;
	}

	//saves to file data
	void save() {
		std::ofstream users;
		std::string path = "transaction\\" + this->name + ".txt";
		users.open(path);
		users << funds<<'\n';
		//system("pause");
		
		for (int i = 0; i < this->logs.size(); i++)
		{
			users << this->logs[i] << std::endl;
		}
		users.close();
	}

	//loads from file required data
	void load() {
		std::fstream if_users;
		std::string path = "transaction\\" + this->name + ".txt";
		if_users.open(path);
		double f;
		if_users >> f;
		this->setFunds(f);
		std::string ID,st;
		int vol;
		double bprice;
		while (if_users >> ID >> st>> vol >> bprice)
		{
			Transaction* T = new Transaction(ID, st, vol, bprice);
			this->logs.push_back(*T);
		}
	}

	//def constructor
	User(std::string s) :name(s) {
	
	
	}
	
	//standard output
	friend std::ostream& operator<<(std::ostream& it, User& u) {
		it << u.getFunds() << std::endl << std::endl;
		
		std::map<std::string, int>stocks;
		std::map<std::string, int>::iterator jj;
		std::vector<Transaction>::iterator ii;
		
		//counts num of stocks per ticker
		for (auto ih = u.logs.begin(); ih != u.logs.end(); ++ih) {
			jj=stocks.find((*ih).getName());
			if (jj!=stocks.end()) {
				if ((*ih).getID()[0]=='b') {
					(*jj).second = (*jj).second + (*ih).getVol();
				}
				else {
					(*jj).second = (*jj).second - (*ih).getVol();
				}

				
			}
			else {
				stocks.insert(std::pair<std::string, int>((*ih).getName(), (*ih).getVol()));
				}
		}
		//writes stocks per ticker
		for (auto ih = stocks.begin(); ih != stocks.end(); ++ih) {
			it<< (*ih).first <<"    "<< (*ih).second<< std::endl << std::endl;
		
		}

		it << std::endl;
		for (auto ih = u.logs.begin(); ih != u.logs.end(); ++ih) {
			double kol = ((u.trenutnacena((*ih).getName())) / ((*ih).getPr())) * 100;;
			//it << (*ih) << "    " << u.trenutnacena((*ih).getName()) << "    " << u.trenutnacena((*ih).getName()) - (*ih).getPr() << "    " << kol << std::endl;
			if ((*ih).getID()[0] == 'b') {
				if (kol>=100  ) {
					it << (*ih) << "\x1B[32m" << "    " << u.trenutnacena((*ih).getName()) << "    " << u.trenutnacena((*ih).getName()) - (*ih).getPr() << "    " << kol << "\033[0m\t\t" << '\n';
				}
				else {
					it << (*ih) << "\x1B[31m" << "    " << u.trenutnacena((*ih).getName()) << "    " << u.trenutnacena((*ih).getName()) - (*ih).getPr() << "    " << kol << "\033[0m\t\t" << '\n';

				}
			
			
			}


		}
		//ispis posedovanja po deonici i trenutna cena vs kupovna cena deonice
		return it;
	}
	double trenutnacena(std::string ticker) {
		Stock* ss = new Stock();
		Crawler* CC = new Crawler();
		CC->setReady(false);
		Parser* PP = new Parser();
		time_t seconds;
		seconds = time(NULL);//vrene za inicijalizaciju IDa
		struct tm* now;
		time(&seconds);
		//nalazenje trenutne cene
		now = localtime(&seconds);
		std::string st = std::to_string(now->tm_mday) + "." + std::to_string(now->tm_mon) + "." + std::to_string(now->tm_year) + ".";
		seconds = seconds - 86400;
		time(&seconds);
		now = localtime(&seconds);
		std::string ed = std::to_string(now->tm_mday) + "." + std::to_string(now->tm_mon) + "." + std::to_string(now->tm_year) + ".";
	
		ss->setName(ticker);


		CC->setRaw(ticker, st, ed);
		PP->setPRaw(CC->getRaw());
		PP->pars(*ss);
		return ss->getLast().getC();
	}
	//BUY OR SELL
	void BS(std::string c ) {
	std:: cout << *this;
		time_t seconds;
		seconds = time(NULL);//vrene za inicijalizaciju IDa
		struct tm* now;
		std::string s = std::to_string(seconds) + this->getName();
		std::string ticker;
		int vol,j;
		if (c == "B" || c == "Buy") {
			s = "b" + s;
			std::cout << "\nWhich stock to buy and how mych (ex. AAPL 200)" << std::endl;
			std::cin >> ticker;
			std::cin >> vol;
			double rez = this->trenutnacena(ticker);

			//std::cout<<ss->getLast();
			//system("pause");
			double sum = vol * rez;
			if (sum > this->getFunds())throw 2;
			this->setFunds(this->getFunds() - sum);
			Transaction* t = new Transaction(s, ticker, vol, rez);
			this->logs.push_back(*t);
		}
		else if(c=="S" || c == "Sell"){
			s = "s" + s;
			//std::string olid;
			std::cout << "\nWhich stock to sell and how mych (ex. AAPL 200)" << std::endl;
			std::cin >> ticker;
			std::cin >> vol;
			std::vector<Transaction>::iterator itr;
			int all_vol = 0;
			for (auto it = logs.begin(); it != logs.end(); ++it) {
				if ((*it).getName() == ticker) {
					if ((*it).getID()[0] == 'b') {
						all_vol = all_vol + (*it).getVol();
					}
					else {
						all_vol = all_vol - (*it).getVol();
					}
				}
			}
			if (vol <= all_vol) {
				double rez = this->trenutnacena(ticker);
				double sum = vol * rez;
				this->setFunds(sum + this->getFunds());
				//logs.erase(itr);


				Transaction* t = new Transaction(s, ticker, vol, sum);
				this->logs.push_back(*t);
			}else throw 3;

		}else throw 4;
	}
};

#endif // !User_h