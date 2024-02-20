package com.innoventes.test.app.service;

import java.util.List;
import java.util.Map;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.exception.ValidationException;

public interface CompanyService {
 Company partialUpdateCompany(Long companyId, Map<String, Object> updates);
//
CompanyDTO getCompanyByCode(String companyCode);

CompanyDTO getCompanyById(Long id);

	List<Company> getAllCompanies();

	Company addCompany(Company company) throws ValidationException;

	Company updateCompany(Long id, Company company) throws ValidationException;
	
	void deleteCompany(Long id);
}