package com.aplazo.model.DTO;

import java.io.Serializable;

public class CreditoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1038913900162407414L;
	
	private Double amount;
	private Integer terms;
	private Double rate;
	public CreditoDTO(Double amount, Integer terms, Double rate) {
		super();
		this.amount = amount;
		this.terms = terms;
		this.rate = rate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getTerms() {
		return terms;
	}
	public void setTerms(Integer terms) {
		this.terms = terms;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "CreditoDTO [amount=" + amount + ", terms=" + terms + ", rate=" + rate + "]";
	} 
	

}
