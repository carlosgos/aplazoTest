package com.aplazo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.aplazo.model.Pago;
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>  {
	
	Optional<Pago> findById(Long id);
	List<Pago> findByPaymentNumber(Integer paymentNumber);
	
	@Query("select u from Pago u where u.idCredito.id = ?1")	 
	List<Pago> findByCredito(Long idCredito);

	


}
