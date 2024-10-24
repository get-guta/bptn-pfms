package com.bptn.pfms._services;

public class Budget{
	  private String category;
	  private double amount;
	  


 public Budget(String category, double amount) {
	  
	  this.category = category;
	  this.amount = amount;
}
 public String getCategory() { return category; }
 public double getAmount() { return amount; }
}