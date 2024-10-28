package com.bptn.pfms._services;

import com.bptn.pfms.utils.InvalidDateFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ReportOverBudgetTest {
    private BudgetManager budgetManager;
    private ExpenseTracker expenseTracker;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
        expenseTracker = new ExpenseTracker();
        reportGenerator = new ReportGenerator(expenseTracker, budgetManager);
    }

    @Test
    void testOverBudgetStatusInReport() throws InvalidDateFormatException {
        // Step 1: Set budget for "Entertainment" category
        Budget entertainmentBudget = new Budget("Entertainment", 300.0);
        budgetManager.setBudget(entertainmentBudget);

        // Step 2: Add an expense transaction in DD/MM/YYYY format
        Expense concertExpense = new Expense(350.0, "01/10/2024", "Movie", "Entertainment");
        expenseTracker.addTransaction(concertExpense);

        // Step 3: Add an income transaction in DD/MM/YYYY format
        Income salaryIncome = new Income(1000.0, "01/10/2024", "Salary", "Work");
        expenseTracker.addTransaction(salaryIncome);

        List<Transaction> transactions = expenseTracker.getAllTransactions();
        System.out.println("Transactions stored in ExpenseTracker:");
        for (Transaction t : transactions) {
            System.out.println(" - " + t.getDate() + ": " + t.getAmount() + " (" + t.getDescription() + ")");
        }
        assertEquals(2, transactions.size(), "ExpenseTracker should contain 2 transactions");

        // Step 4: Retrieve transactions by month/year and print
        List<Transaction> monthlyTransactions = expenseTracker.getTransactionsByMonth("10", "2024");
        System.out.println("Transactions retrieved for month 10/2024:");
        for (Transaction t : monthlyTransactions) {
            System.out.println(" - " + t.getDate() + ": " + t.getAmount() + " (" + t.getDescription() + ")");
        }
        assertEquals(2, monthlyTransactions.size(), "Expected to retrieve 2 transactions for month 10/2024");

        // Step 5: Generate report and verify "Exceed" status and income entries
        String report = reportGenerator.generateMonthlyReport("10", "2024");
        System.out.println("Generated Report:\n" + report);

        // Check that the report contains "Exceed" for the Entertainment category
        assertTrue(report.contains("Exceed"), 
            "Expected 'Exceed' status not found in report. Report content:\n" + report);

        // Check that the report includes the income entry
        assertTrue(report.contains("Total Income: $1000.00"), 
            "Expected 'Total Income: $1000.00' in report but was not found. Report content:\n" + report);

        // Check for overall financial health status
        assertTrue(report.contains("Overall Financial Health Status: Healthy") ||
                   report.contains("Overall Financial Health Status: Concerning"), 
            "Expected financial health status not found in report. Report content:\n" + report);
    }
}