package com.spring.graph.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.Patiententity;


@Repository
public interface PatientReportRepository extends JpaRepository <Patiententity, Long> {

	
	public boolean existsByEmail(String email);
	
	  public String save(PatientReportRepository PatientReportRepository);

	public Optional<Patiententity> findByPatientid(String id);

	public Optional<Patiententity> findByDocId(String id);
	
}
