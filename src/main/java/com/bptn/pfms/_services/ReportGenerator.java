package com.bptn.pfms._services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bptn.pfms.utils.InvalidDateFormatException;


public class ReportGenerator {
    private ExpenseTracker expenseTracker;
    private BudgetManager budgetManager;
    
    public ReportGenerator(ExpenseTracker expenseTracker, BudgetManager budgetManager) {
        this.expenseTracker = expenseTracker;
        this.budgetManager = budgetManager;
    }
    
    public String generateMonthlyReport(String month, String year) throws InvalidDateFormatException {
        // Validate month and year format
        try {
            int monthNum = Integer.parseInt(month);
            int yearNum = Integer.parseInt(year);
            if (monthNum < 1 || monthNum > 12) {
                throw new InvalidDateFormatException("Month must be between 1 and 12");
            }
            if (yearNum < 1900 || yearNum > 2100) {
                throw new InvalidDateFormatException("Year must be between 1900 and 2100");
            }
        } catch (NumberFormatException e) {
            throw new InvalidDateFormatException("Month and year must be valid numbers");
        }
        
        StringBuilder report = new StringBuilder();
        List<Transaction> monthlyTransactions = expenseTracker.getTransactionsByMonth(month, year);
        
        // Calculate expenses per category
        Map<String, Double> categoryExpenses = new HashMap<>();
        for (Transaction t : monthlyTransactions) {
            if (t instanceof Expense) {
                Expense expense = (Expense) t;
                categoryExpenses.merge(expense.getCategory(), expense.getAmount(), Double::sum);
            }
        }
        
        // Generate report header
        report.append("******************************************************************************************************\n");
        report.append("*                                       Monthly Financial Report                                      *\n");
        report.append("******************************************************************************************************\n");
        report.append(String.format("Report Period: %s/%s\n\n", month, year));
        report.append(String.format("| %-3s | %-15s | %-10s | %-10s | %-8s |\n", "No.", "Category", "Expenses", "Budget", "Status"));
        report.append("-----------------------------------------------------------------------------------------------------------------------\n");
        
        // Generate report body
        int counter = 1;
        double totalExpenses = 0;
        double totalBudget = 0;
        
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            String category = entry.getKey();
            double expenses = entry.getValue();
            Budget budget = budgetManager.getBudgetByCategory(category);
            double budgetAmount = budget != null ? budget.getAmount() : 0.0;
            String status = expenses <= budgetAmount ? "OK" : "Exceed";
            
            totalExpenses += expenses;
            totalBudget += budgetAmount;
            
            report.append(String.format("| %-3d | %-15s | $%-9.2f | $%-9.2f | %-8s |\n",
                    counter++, category, expenses, budgetAmount, status));
        }
        
        // Add summary
        report.append("-----------------------------------------------------------------------------------------------------------------------\n");
        report.append(String.format("Total Expenses: $%.2f\n", totalExpenses));
        report.append(String.format("Total Budget: $%.2f\n", totalBudget));
        report.append(String.format("Monthly Balance: $%.2f\n", totalBudget - totalExpenses));
        
        return report.toString();
    }
}