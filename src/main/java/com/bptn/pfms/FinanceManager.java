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
import com.bptn.pfms._services.SavingsGoalTracker;
import com.bptn.pfms.utils.InvalidDateFormatException;

public class FinanceManager {
	private final Scanner scanner;
	private final ExpenseTracker expenseTracker;
	private final BudgetManager budgetManager;
	private final SavingsGoalTracker savingsGoalTracker;
	private final ReportGenerator reportGenerator;

	public FinanceManager() {
		this.scanner = new Scanner(System.in);
		this.expenseTracker = new ExpenseTracker();
		this.budgetManager = new BudgetManager();
		this.savingsGoalTracker = new SavingsGoalTracker();
		this.reportGenerator = new ReportGenerator(expenseTracker, budgetManager); // Pass dependencies
	}

	public void expenseHandler() {
		try {
			System.out.println("\n\nPlease Enter Your New Expense Details:\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Category: ");
			String category = scanner.nextLine().trim();

			System.out.print("Enter Amount: ");
			double amount = Double.parseDouble(scanner.nextLine()); // Fix scanner issue

			System.out.print("Enter Date (MM/DD/YYYY): ");
			String date = scanner.nextLine().trim();

			System.out.print("Enter Description: ");
			String description = scanner.nextLine().trim();

			addExpense(category, amount, date, description);
			System.out.println("*** Transaction record successfully saved as Expense! ***");

		} catch (NumberFormatException e) {
			System.out.println("Error: Please enter a valid number for amount.");
		} catch (Exception e) {
			System.out.println("Error processing expense: " + e.getMessage());
		}
	}

	public void incomeHandler() {
		try {
			System.out.println("\n\nPlease Enter Your New Income Details:\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Source: ");
			String source = scanner.nextLine().trim();

			System.out.print("Enter Amount: ");
			double amount = Double.parseDouble(scanner.nextLine()); // Fix scanner issue

			System.out.print("Enter Date (MM/DD/YYYY): ");
			String date = scanner.nextLine().trim();

			System.out.print("Enter Description: ");
			String description = scanner.nextLine().trim();

			addIncome(source, amount, date, description);
			System.out.println("*** Transaction record successfully saved as Income! ***");

		} catch (NumberFormatException e) {
			System.out.println("Error: Please enter a valid number for amount.");
		} catch (Exception e) {
			System.out.println("Error processing income: " + e.getMessage());
		}
	}

	public void budgetPlanner() {
		try {
			System.out.println("\n\nPlease Enter Your Budget for Each Category\n");
			System.out.println("***************************************\n");

			System.out.print("Enter Category: ");
			String category = scanner.nextLine().trim();

			System.out.print("Enter Budget Amount: ");
			double amount = Double.parseDouble(scanner.nextLine());

			Budget budget = new Budget(category, amount);
			budgetManager.setBudget(budget);
			System.out.println("*** Budget successfully set! ***");

		} catch (NumberFormatException e) {
			System.out.println("Error: Please enter a valid number for budget amount.");
		} catch (Exception e) {
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

			System.out.print("Enter month (1-12): ");
			String month = scanner.nextLine().trim();

			System.out.print("Enter year (YYYY): ");
			String year = scanner.nextLine().trim();

			String report = reportGenerator.generateMonthlyReport(month, year);

			// Display report
			System.out.println("\nReport Generated:");
			System.out.println(report);

			// Ask about saving
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
	}

	public void trackSavingsGoal() {
		System.out.println("\n----- Track Savings Goal -----");

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