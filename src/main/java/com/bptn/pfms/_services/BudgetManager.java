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
	// budgetCollection.removeIf(budget.getCategory()::equals); // Method reference way of writing
	budgetCollection.add(budget);
}

 
 public List<Budget> getAllBudgets() {
     return new ArrayList<>(budgetCollection);  // Return a copy to preserve encapsulation
     
    // Returning a Copy: In this method, instead of directly returning budgetCollection, which is the actual collection that holds your data, you’re returning a new ArrayList created from it (new ArrayList<>(budgetCollection)). 
    //This creates a copy of budgetCollection that contains the same elements, but is separate from the original collection.


 }
 
 // Report generator uses this method
 public Budget getBudgetByCategory(String category) {
     return budgetCollection.stream()
                  .filter(b -> b.getCategory().equals(category))
                  .findFirst()
                  .orElse(null);
 }
 
 
 /*
  * 
  * Other way of writing the above method
   public Budget getBudgetByCategory(String category) {
    for (Budget b : budgetCollection) {
        if (b.getCategory().equals(category)) {
            return b;
        }
    }
    return null;  // Return null if no match is found
}

  * */
}
