package com.aplazo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "creditos")
public class Credito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046967430031213795L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "terms")
	private Integer terms;
	@Column(name = "rate")
	private Double rate;
	
	
	public Credito() {
		super();
	}

	public Credito(Double amount, Integer terms, Double rate) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
