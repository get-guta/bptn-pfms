package com.bptn.pfms._services;

import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
	 ArrayList<Budget> budgetCollection;

public BudgetManager() {
	
	this.budgetCollection = new ArrayList<>();
}

public void setBudget(Budget budget) {
    // Remove existing budget for the same category if it exists
	budgetCollection.removeIf(b -> b.getCategory().equals(budget.getCategory()));
	budgetCollection.add(budget);
}

 
 public List<Budget> getAllBudgets() {
     return new ArrayList<>(budgetCollection);  // Return a copy to preserve encapsulation
 }
 
 public Budget getBudgetByCategory(String category) {
     return budgetCollection.stream()
                  .filter(b -> b.getCategory().equals(category))
                  .findFirst()
                  .orElse(null);
 }
}
