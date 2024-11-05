package com.bptn.pfms;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.bptn.pfms._services.Budget;
import com.bptn.pfms._services.BudgetManager;
import com.bptn.pfms._services.Expense;
import com.bptn.pfms._services.ExpenseTracker;
import com.bptn.pfms._services.Income;
import com.bptn.pfms._services.ReportGenerator;
import com.bptn.pfms.utils.InvalidDateFormatException;

public class FinanceManager {
	private final Scanner scanner;
	private final ExpenseTracker expenseTracker;
	private final BudgetManager budgetManager;
	private final ReportGenerator reportGenerator;

	public FinanceManager() {
		this.scanner = new Scanner(System.in);
		this.expenseTracker = new ExpenseTracker();
		this.budgetManager = new BudgetManager();
		this.reportGenerator = new ReportGenerator(expenseTracker, budgetManager);
	}

	public void expenseHandler() {
		try {
			System.out.println("\n\nPlease Enter Your New Expense Details:\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Category: ");
			String category = scanner.nextLine().trim();

			double amount = 0.0;
			// try should surround the amount part
			try {
				System.out.print(" Enter Amount: ");
				amount = Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error: Please enter a valid number for amount.");
				System.out.print("Re-enter Amount: ");
				amount = Double.parseDouble(scanner.nextLine());
			}

			System.out.print("Enter Date (DD/MM/YYYY): ");
			String date = scanner.nextLine().trim();

			System.out.print("Enter Description: ");
			String description = scanner.nextLine().trim();

			addExpense(category, amount, date, description);
			System.out.println("*** Transaction record successfully saved as Expense! ***");

		} catch (Exception e) {
			System.out.println("Error processing expense: " + e.getMessage());
		}
	}

	public void incomeHandler() {
		try {
			System.out.println("\n\n Please Enter Your New Income Details:\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Source: ");
			String source = scanner.nextLine().trim();

			double amount = 0.0;
			try {
				System.out.print("Enter Amount: ");
				amount = Double.parseDouble(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Error: Please enter a valid number for amount.");
				System.out.print("Re-enter Amount: ");
				amount = Double.parseDouble(scanner.nextLine().trim());
			}

			System.out.print("Enter Date (DD/MM/YYYY): ");
			String date = scanner.nextLine().trim();

			System.out.print("Enter Description: ");
			String description = scanner.nextLine().trim();

			addIncome(source, amount, date, description);
			System.out.println("*** Transaction record successfully saved as Income! ***");

		}  catch (Exception e) {
			System.out.println("Error processing income: " + e.getMessage());
		}
	}

	public void budgetPlanner() {
		try {
			System.out.println("\n\nPlease Enter Your Budget for Each Category\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Category: ");
			String category = scanner.nextLine().trim().toLowerCase();

			double amount = 0.0;
			try {
				System.out.print("Enter Budget Amount: ");
				amount = Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error: Please enter a valid number for budget amount.");
				System.out.print("Re-enter Budget Amount: ");
				amount = Double.parseDouble(scanner.nextLine());
			}

			Budget budget = new Budget(category, amount);
			budgetManager.setBudget(budget);
			System.out.println("*** Budget successfully set! ***");

		}  catch (Exception e) {
			System.out.println("Error setting budget: " + e.getMessage());
		}
	}

	public boolean hasExpenses() {
		return !expenseTracker.getAllTransactions().isEmpty();
	}

	public boolean hasBudgets() {
		return !budgetManager.getAllBudgets().isEmpty();
	}

	public void generateFinancialReport() {
		try {

			// Check if there are any entries in the trackers
			if (!hasExpenses() && !hasBudgets()) {
		
				System.out
						.println("No expenses or budget entries found. Please add data before generating the report.");
				return; // Stop the method if no data is found
			}
			System.out.println("\n----- Generate Monthly Financial Report -----");

			
			String month;
			while (true) {
				System.out.print("Enter month (1-12): ");
				month = scanner.nextLine().trim();

				// Check if the month is a valid integer within the range
				try {
					int monthInt = Integer.parseInt(month);
					if (monthInt >= 1 && monthInt <= 12) {
						break; // Valid month, exit the loop
					} else {
						System.out.println("Invalid month. Please enter a number between 1 and 12.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a numeric value for the month.");
				}
			}

			System.out.print("Enter year (YYYY): ");
			String year = scanner.nextLine().trim();
			try {
			    reportGenerator.generateMonthlyReport("13", "2024"); // Invalid month
			} catch (InvalidDateFormatException e) {
			    System.out.println(e.getMessage()); // This should print "Month must be between 1 and 12"
			}

			String report = reportGenerator.generateMonthlyReport(month, year);

			System.out.println("\nReport Generated:");
			System.out.println(report);

			// confirm saving
			System.out.print("Would you like to save this report to a file? (y/n): ");
			String saveChoice = scanner.nextLine().trim();

			if (saveChoice.equalsIgnoreCase("y")) {
				saveReportToFile(report);
			}

		} catch (InvalidDateFormatException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please try again with valid date format.");
		} catch (Exception e) {
			System.out.println("Error generating report: " + e.getMessage());
		}
	}

	private void saveReportToFile(String report) {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = "financial_report_" + timestamp + ".txt";

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			writer.print(report);
			System.out.println("Report saved to " + fileName);
		} catch (IOException e) {
			System.out.println("Error saving report: " + e.getMessage());
		}
		
		/*
		 
		 // Write the built report to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(report); 
            System.out.println("Report saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
		 
		 */
		
	}

	private void addExpense(String category, double amount, String date, String description) {
		Expense expense = new Expense(amount, date, description, category);
		expenseTracker.addTransaction(expense);
	}

	private void addIncome(String source, double amount, String date, String description) {
		Income income = new Income(amount, date, description, source);
		expenseTracker.addTransaction(income);
	}

}