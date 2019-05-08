package com.moneytransfer.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccount 
{
  private String userAccountNumber;
  private int amount;
  
  public UserAccount( @JsonProperty("accountNo")String accountNumber,  @JsonProperty("amount")int amount)
  {
	  userAccountNumber=accountNumber;
	  this.amount=amount;
	  
  }
	
	public String getUserAccountNumber() {
		return userAccountNumber;
	}
	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
