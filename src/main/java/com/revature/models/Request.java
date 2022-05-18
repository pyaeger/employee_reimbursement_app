package com.revature.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Request {
	
	private int requ_id;
	private double amount;
	private LocalDate submitted;
	private LocalDate resolved;
	private String description;
	private byte[] receipt;
	private int author;
	private int resolver;
	private int status_id=1;
	private int type_id;
	
	
	
	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Request(int requ_id, double amount, LocalDate submitted, LocalDate resolved, String description,
			byte[] receipt, int author, int resolver, int status_id, int type_id) {
		super();
		this.requ_id = requ_id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status_id = status_id;
		this.type_id = type_id;
	}


	public Request(int requ_id, double amount, LocalDate submitted, String description, int author, int status_id,
			int type_id) {
		super();
		this.requ_id = requ_id;
		this.amount = amount;
		this.submitted = submitted;
		this.description = description;
		this.author = author;
		this.status_id = status_id;
		this.type_id = type_id;
	}


	public Request(int requ_id, double amount, String description, byte receipt, int author, int resolver,
			int status_id, int type_id) {
		// TODO Auto-generated constructor stub
	}


	public Request(int requ_id, double amount, LocalDate submitted, String description, byte receipt, int author,
			int resolver, int status_id, int type_id) {
		// TODO Auto-generated constructor stub
	}


	public Request(int requ_id, double amount, String description, int author, int resolver, int status_id, int type_id) {
	}


	public int getRequ_id() {
		return requ_id;
	}


	public void setRequ_id(int requ_id) {
		this.requ_id = requ_id;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public LocalDate getSubmitted() {
		return submitted;
	}


	public void setSubmitted(LocalDate submitted) {
		this.submitted = submitted;
	}


	public LocalDate getResolved() {
		return resolved;
	}


	public void setResolved(LocalDate resolved) {
		this.resolved = resolved;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public byte[] getReceipt() {
		return receipt;
	}


	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}


	public int getAuthor() {
		return author;
	}


	public void setAuthor(int author) {
		this.author = author;
	}


	public int getResolver() {
		return resolver;
	}


	public void setResolver(int resolver) {
		this.resolver = resolver;
	}


	public int getStatus_id() {
		return status_id;
	}


	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}


	public int getType_id() {
		return type_id;
	}


	public void setType_id(int type_id) {
		this.type_id = type_id;
	}


	@Override
	public String toString() {
		return "Request [requ_id=" + requ_id + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", receipt=" + receipt + ", author=" + author
				+ ", resolver=" + resolver + ", status_id=" + status_id + ", type_id=" + type_id + "]";
	}
	
	
	
	

}
