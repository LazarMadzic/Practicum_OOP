#pragma once

int option_main() {
	int x;
	system("clear");
	std::cout << "1. Gather Stock data\n";
	std::cout << "2. Show Stock Data\n";
	//std::cout << "3. Indicators\n";
	std::cout << "3. Buy/Sell\n";
	std::cout << "4. Portfolio\n";
	std::cout << "5. LogOut\n";
	std::cout << "0. Exit\n";
	std::cin >> x;
	return x;
}

int option_log() {
	int x;
	system("clear");
	std::cout << "\n1. Log in\n";
	std::cout << "2. Register\n";
	std::cout << "0. Exit\n";
	std::cin >> x;
	return x;
}

bool login() {

	return true;
}