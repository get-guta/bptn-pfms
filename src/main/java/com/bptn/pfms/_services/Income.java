package com.bptn.pfms._services;

import java.util.Date;

//Income class
public class Income extends Transaction {
 private String source;

 public Income(double amount, String date, String description, String source) {
     super(amount, date, description);
     this.source = source;
 }

 public String getSource() { return source; }
 public void setSource(String source) { this.source = source; }
}