package com.aplazo.web.rest;


import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aplazo.exception.ApiPagoException;
import com.aplazo.model.Credito;
import com.aplazo.model.Pago;
import com.aplazo.model.DTO.CreditoDTO;
import com.aplazo.service.CreditoService;
import com.aplazo.service.PagoService;
import com.aplazo.util.Utileria;
/**
 * Clase que es la api rest de servicios de calculo de interes simple
 * @author juan carlos
 * @category Api Rest 
 * @version 1
 */
@RestController
@RequestMapping("/api/aplazo")
public class CalculoCredito {
	
	private static final Logger log = LogManager.getLogger(CalculoCredito.class);
    @Autowired
	private PagoService pagoService;
    @Autowired
    private CreditoService creditoService;
    /**
     * Servicio rest post para crear un calculo de interes simple, el monto total a dividir samanalmente es el monto del cretido mas el monto del interes generado
     * @param creditoDTO objeto que los valores como el monto, terminos e interes
     * @return List<Pago> lista de pagos semanles
     * @throws ApiPagoException error en el tipo o entrada de dato
     */
    @PostMapping("/creditos")
   	public ResponseEntity<Object> createPagosRateAmount(@RequestBody CreditoDTO creditoDTO)  {
   		try {
   			String  errors=validateCredito(creditoDTO);
   			
   			if(errors.isEmpty()) {
   				List<Pago> pagosCredito;
   	   			Credito credito=creditoService.saveCredito(creditoDTO);
   				pagosCredito=creditoService.calcularPagosRateAmount(credito);
   				return new ResponseEntity<>(pagosCredito,  HttpStatus.CREATED);
   			}
   			throw new ApiPagoException(errors);
   			
   		} catch (Exception e) {
   			log.error(e.getMessage());
   			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
   		}
   	}
    
    /**
     * Servicio rest post para crear un calculo de interes simple, solo se divide el interes generado
     * @param creditoDTO objeto que los valores como el monto, terminos e interes
     * @return List<Pago> lista de pagos semanles
     * @throws ApiPagoException error en el tipo o entrada de dato
     */
    @PostMapping("/creditos/rate")
   	public ResponseEntity<Object> createPagosRate(@RequestBody CreditoDTO creditoDTO)  {
   		try {
   			String  errors=validateCredito(creditoDTO);
   			
   			if(errors.isEmpty()) {
   				List<Pago> pagosCredito;
   	   			Credito credito=creditoService.saveCredito(creditoDTO);
   				pagosCredito=creditoService.calcularPagosRate(credito);
   				return new ResponseEntity<>(pagosCredito,  HttpStatus.CREATED);
   			}
   			throw new ApiPagoException(errors);
   			
   		} catch (Exception e) {
   			log.error(e.getMessage());
   			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
   		}
   	}
    /**
     * 
     * @return
     */
    @GetMapping("/creditos")
	public ResponseEntity<List<Credito>> getAllCreditos() {
    	log.info("getAllCreditos");
		try {
			List<Credito> creditos = creditoService.findAll();		
			if (creditos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(creditos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/creditos/{id}")
	public ResponseEntity<Credito> getCreditosById(@PathVariable("id") long id) {
		Optional<Credito> creditoData = creditoService.findById(id);

		if (creditoData.isPresent()) {			
			return new ResponseEntity<>(creditoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    /**
     * 
     * @param idCredito
     * @return
     */
    @GetMapping("/pagosCredito/{idCredito}")
	public ResponseEntity<List<Pago>> getPagosCredito(@PathVariable("idCredito") long idCredito) {
    	Optional<Credito> creditoData = creditoService.findById(idCredito);
		if (creditoData.isPresent()) {
			List<Pago> pagos = pagoService.findByCredito(creditoData.get());
			return new ResponseEntity<>(pagos, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    
    /**
     * 
     * @return
     */
    @GetMapping("/pagos")
	public ResponseEntity<List<Pago>> getAllPagos() {
    	log.info("getAllPagos");
		try {
			List<Pago> pagos = pagoService.findAll();
		
			if (pagos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(pagos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/pagos/{id}")
	public ResponseEntity<Pago> getPagosById(@PathVariable("id") long id) {
		Optional<Pago> pagoData = pagoService.findById(id);

		if (pagoData.isPresent()) {
			return new ResponseEntity<>(pagoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}  
    /**
     * Funcion para validar el json de entrada para calcular el interes y pago semanal
     * @param credito
     * @return
     */
    private String validateCredito(CreditoDTO credito){
    	String error="";
    	if(null==credito) {
    		error="amount, terms and rate are required";
    	}else {
    		if(null!=credito.getAmount() && credito.getAmount()<=Utileria.DOUBLE_CERO) 
    			error="amount must be greater than 0.0 ";
    		if(null!=credito.getTerms() && credito.getTerms()<Utileria.INTEGER_ONE) {
    			error= error +" terms must be greater than 0";
    		}
    		if(null!=credito.getRate() && credito.getRate()<=Utileria.DOUBLE_CERO) {
    			error= error +" rate must be greater than 0.0";
    		}
    		
    	}
    	
    	return error;
    	
    }
    /**
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/pagos/{id}")
	public ResponseEntity<HttpStatus> deletePago(@PathVariable("id") long id) {
		try {
			pagoService.deletePago(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    /**
     * 
     * @param id
     * @param pago
     * @return
     */
    @PutMapping("/pagos/{id}")
	public ResponseEntity<Pago> updatePago(@PathVariable("id") long id, @RequestBody Pago pago) {
		Optional<Pago> pagoUpdate = pagoService.findById(id);
		if (pagoUpdate.isPresent()) {
			Pago p=pagoUpdate.get();			
			return new ResponseEntity<>(pagoService.updatePago(pago,p), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
  
    
}
