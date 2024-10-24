package com.bptn.pfms;

public class Main {

	
	
	public static void main(String [] args) {
		FinanceManager financeManager = new FinanceManager();
		ConsoleUI console = new ConsoleUI(financeManager);
		console.userChoiceHandler();
		
		
		
		
		
	}

}
