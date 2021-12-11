package com.aplazo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable{
	
	

	public Pago() {
		super();
	}

	public Pago(Integer paymentNumber, Double amount, Date paymentDate, Credito credito) {
		super();
		this.paymentNumber = paymentNumber;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.idCredito=credito;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3445017728325628538L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "payment_number")
	private Integer paymentNumber;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "payment_date")
	private Date paymentDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credito_id")
	private Credito idCredito;
	

	public Integer getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(Integer paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Override
	public String toString() {
		return "Pago [id=" + id + ", paymentNumber=" + paymentNumber + ", amount=" + amount + ", paymentDate="
				+ paymentDate + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
