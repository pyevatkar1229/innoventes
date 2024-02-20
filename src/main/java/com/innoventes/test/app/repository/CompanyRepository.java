package com.innoventes.test.app.repository;

import com.innoventes.test.app.dto.CompanyDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innoventes.test.app.entity.Company;

import java.util.Map;


public interface CompanyRepository extends JpaRepository<Company, Long> {
   // Company findByCompanyId(Long companyId);
   Company findByCompanyCode(String companyCode);

	
}