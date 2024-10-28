package com.bptn.pfms._services;

public class Expense extends Transaction {
 private String category;

 public Expense(double amount, String date, String description, String category) {
     super(amount, date, description);
     this.category = category;
 }

 public String getCategory() { 
	 return category;
	 }
 public void setCategory(String category) { 
	 this.category = category;
	 }
}
