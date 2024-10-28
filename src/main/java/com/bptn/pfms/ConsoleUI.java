package com.bptn.pfms;
import java.util.Scanner;


public class ConsoleUI {
	
	private final FinanceManager financeManager;
	 
   
    public ConsoleUI(FinanceManager financeManager) {
        this.financeManager = financeManager;
        
    }
    
    public void userChoiceHandler() {
    	Scanner scanner = new Scanner(System.in);
    	
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                	financeManager.expenseHandler();
                    break;
                case 2:
                	financeManager.incomeHandler();
                    break;
                case 3:
                	financeManager.budgetPlanner();
                    break;
                case 4:
                	financeManager.generateFinancialReport();
                    break;   
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the Personal Finance Management System. Goodbye!");
                    break;
                default: 
                    System.out.print("Invalid input. Please enter a number between 1 and 5");

            }
        }
    }
    private void displayMainMenu() {
        System.out.println("\n===== Personal Finance Management System =====");
        System.out.println("1. Add an expense");
        System.out.println("2. Add Income");
        System.out.println("3. Set a budget");
        System.out.println("4. Generate financial report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }
	
	

}
