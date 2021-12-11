package com.aplazo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.model.Credito;


@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long>{
	
	Optional<Credito> findById(Long id);	

}
