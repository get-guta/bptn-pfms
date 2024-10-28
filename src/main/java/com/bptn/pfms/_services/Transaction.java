package com.bptn.pfms._services;

public abstract class Transaction {
    protected double amount;
    protected String date;
    protected String description;

    public Transaction(double amount, String date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public double getAmount() { 
    	return amount; 
    	}
    public String getDate() 
    { 
    	return date;
    	}
    public String getDescription() { 
    	return description;
    	}

    public void setAmount(double amount) {
    	this.amount = amount;
    	}
    public void setDate(String date) { 
    	this.date = date; 
    	}
    public void setDescription(String description) { 
    	this.description = description; 
    	}
}





