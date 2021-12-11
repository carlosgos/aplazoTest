package com.aplazo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.model.Credito;
import com.aplazo.model.Pago;
import com.aplazo.model.DTO.CreditoDTO;
import com.aplazo.repository.CreditoRepository;
import com.aplazo.util.Utileria;

/**
 * Clase de servicio para Credito
 * @author juan carlos
 *
 */
@Service
@Transactional
public class CreditoService {
	
	private static final Logger log = LogManager.getLogger(CreditoService.class);
	@Autowired
	private CreditoRepository creditoRepository;
	@Autowired
	private PagoService pagoService;
	
	/**
	 * Metodo para guardar un credito en BD a partit de un objeto DTO
	 * @param creditoDTO objeto que contine la informacion
	 * @return Credito guardadado
	 */
	public Credito saveCredito(CreditoDTO creditoDTO) {
		Credito credito= new Credito(creditoDTO.getAmount(), creditoDTO.getTerms(), creditoDTO.getRate());
		return creditoRepository.save(credito);
	}
	/**
	 * Metodo para guardar un credito en BD
	 * @param credito
	 * @return
	 */
	public Credito saveCredito(Credito credito) {
		return creditoRepository.save(credito);
	}
	public List<Credito> findAll(){
		return creditoRepository.findAll();
	}
	public Optional<Credito> findById(Long id){
		return creditoRepository.findById(id);
	}
	
	public void deleteCredito(Long id) {
		creditoRepository.deleteById(id);
	}
	
	public Credito updateCredito(Credito credito, Credito aux) {
		
		aux.setAmount(credito.getAmount());
		aux.setTerms(credito.getTerms());
		aux.setRate(credito.getRate());
			return creditoRepository.save(aux);
		
		
			
		
	}	
	
	/**
	 * Metodo para calcular el interes de un credito y regresar las n fechas de pago con sus respectivo monto, el monto total esta compuesto por el monto de credito mas el ineteres generado, el monto total ya se divide en la n semanas  
	 * @param credito objeto que contine el porcentaje de interes , el numero de pagos y el monto 
	 * @return List<Pago> lista que contine las fechas de pago con sus repectivo monto
	 */
	public List<Pago> calcularPagosRateAmount(Credito credito){
		log.info("credito: {}",credito);
		
		Double totalPago=credito.getAmount()+calcularInteres(credito);
		log.info("totalPago: {}",totalPago);
		Double pagoWeek=totalPago/credito.getTerms();
		log.info("pagoWeek: {}",pagoWeek);
		Date toDay=new Date();
		List<Pago> listaPagos=new ArrayList<>();
		for(int i=0;i<credito.getTerms() ;i++) {
			Pago pago=new Pago(i+1,pagoWeek,Utileria.getNextDateWeek(i,  toDay),credito);
			pago=pagoService.savePago(pago);
			listaPagos.add(pago);
		}
		/**
		List<Pago> listSave=new ArrayList<>();
		listaPagos.stream().forEach(p-> 
			listSave.add(savePago(p))
		);**/
		
		return listaPagos;
	}
	/**
	 * Metodo para calcular el interes de un credito y regresar las n fechas de pago con sus respectivo monto, el monto a dividir semanalnte solo es el interes generado sin el moto del credito
	 * @param credito objeto que contine el porcentaje de interes , el numero de pagos y el monto 
	 * @return List<Pago> lista que contine las fechas de pago con sus repectivo monto
	 */
	public List<Pago> calcularPagosRate(Credito credito){
		log.info("credito: {}",credito);
		
		Double totalRateAmount=calcularInteres(credito);
		log.info("totalRateAmount: {}",totalRateAmount);
		Double pagoWeekRate=totalRateAmount/credito.getTerms();
		log.info("pagoWeekRate: {}",pagoWeekRate);
		Date toDay=new Date();
		List<Pago> listaPagos=new ArrayList<>();
		for(int i=0;i<credito.getTerms() ;i++) {
			Pago pago=new Pago(i+1,pagoWeekRate,Utileria.getNextDateWeek(i,  toDay),credito);
			pago=pagoService.savePago(pago);
			listaPagos.add(pago);
		}
		/**
		List<Pago> listSave=new ArrayList<>();
		listaPagos.stream().forEach(p-> 
			listSave.add(savePago(p))
		);**/
		
		return listaPagos;
	}
    
	/**
	 * Funcion para calcular el interes simple
	 * @param credito objeto que contiene los datos para hacer el calculo
	 * @return interes simple calculado
	 */
	private Double calcularInteres(Credito credito) {
		log.info("amount: {} ,rate: {}",credito.getAmount(),credito.getRate());
		return (credito.getAmount()*credito.getRate())/100;
	}
}
