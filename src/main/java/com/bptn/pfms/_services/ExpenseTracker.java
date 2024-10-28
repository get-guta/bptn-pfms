package com.bptn.pfms._services;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker {

	private ArrayList<Transaction> transactions;

	public ExpenseTracker() {
		this.transactions = new ArrayList<>();
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);

	}

	public List<Transaction> getAllTransactions() {
		return new ArrayList<>(transactions); // Return a copy to preserve encapsulation
	}
	
	public List<Transaction> getTransactionsByMonth(String month, String year) {
	    List<Transaction> monthlyTransactions = new ArrayList<>();

	    for (Transaction t : transactions) {
	        String[] dateParts = t.getDate().split("/");
	        if (dateParts.length == 3 && dateParts[1].equals(month) && dateParts[2].equals(year)) {
	            monthlyTransactions.add(t);
	        }
	    }
	    return monthlyTransactions;
	}


	public List<Transaction> getTransactionsByYear(String year) {
		List<Transaction> yearlyTransactions = new ArrayList<>();
		for (Transaction t : transactions) {
			String[] dateParts = t.getDate().split("/");
			if (dateParts.length == 3 && dateParts[2].equals(year)) {
				yearlyTransactions.add(t);
			}
		}
		return yearlyTransactions;
	}
}
