package com.bptn.pfms._services;


public class Income extends Transaction {
    private String source;

    public Income(double amount, String date, String description, String source) {
        super(amount, date, description); // Initialize common fields from Transaction
        this.source = source; // Initialize specific field for Income
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    // I was thinking to test print the data of instance income
   

//    @Override
//    public String toString() {
//        return "Income{" +
//                "amount=" + getAmount() +
//                ", date='" + getDate() + '\'' +
//                ", description='" + getDescription() + '\'' +
//                ", source='" + source + '\'' +
//                '}';
//    }
}

