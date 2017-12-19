package com.revature.ERS;

public class RequestForm 
{
	int requestID = 0;
	int requesterID = 0;
	String requesterName = null;
	double amount = 0.0;
	boolean approved = false;
	boolean completed = false;
	String description = null;
	
	//getters and setters
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public int getRequesterID() {
		return requesterID;
	}
	public void setRequesterID(int requesterID) {
		this.requesterID = requesterID;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}

