package com.aplazo.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.model.Credito;
import com.aplazo.model.Pago;

import com.aplazo.repository.PagoRepository;



/**
 * Clase servicio para Pago
 * @author juan carlos
 *
 */
@Service
@Transactional
public class PagoService {
	@Autowired
	private PagoRepository pagoRepository;
	
	private static final Logger log = LogManager.getLogger(PagoService.class);
	
	/**
	 * 
	 * @param pago
	 * @return
	 */
	public Pago savePago(Pago pago) {
		return pagoRepository.save(pago);
	}
		
	public List<Pago> findAll(){
		return pagoRepository.findAll();
	}
	public List<Pago> findByCredito(Credito credito){
		return pagoRepository.findByCredito(credito.getId());
	}
	public Optional<Pago> findById(Long id){
		return pagoRepository.findById(id);
	}
	
	public void deletePago(Long id) {
		pagoRepository.deleteById(id);
	}
	
	public Pago updatePago(Pago pago, Pago aux) {
		log.info("updatePago: {}",aux);
		aux.setAmount(pago.getAmount());
		aux.setPaymentDate(pago.getPaymentDate());
		aux.setPaymentNumber(pago.getPaymentNumber());
			return pagoRepository.save(aux);
		
		
			
		
	}
	public PagoRepository getPagoRepository() {
		return pagoRepository;
	}
	public void setPagoRepository(PagoRepository pagoRepository) {
		this.pagoRepository = pagoRepository;
	}
	

}
