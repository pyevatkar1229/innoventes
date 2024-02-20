package com.innoventes.test.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.innoventes.test.app.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.error.ApplicationErrorCodes;
import com.innoventes.test.app.exception.ResourceNotFoundException;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.repository.CompanyRepository;
import com.innoventes.test.app.service.CompanyService;
import com.innoventes.test.app.util.ServiceHelper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceHelper serviceHelper;

    @Override
    public CompanyDTO getCompanyByCode(String companyCode){
        Company byCompanyCode = companyRepository.findByCompanyCode(companyCode);
        CompanyDTO companyDTO = convertToCompanyDTO(byCompanyCode);

        return companyDTO;
    }

    // Add a method to convert Company to CompanyDTO
    private CompanyDTO convertToCompanyDTO(Company company) {

        CompanyDTO companyDTO = new CompanyDTO();
        company.setId(companyDTO.getId());
        company.setCompanyName(companyDTO.getCompanyName());
        company.setEmail(company.getEmail());
        company.setCompanyCode(company.getCompanyCode());
        company.setWebSiteURL(company.getWebSiteURL());
        company.setStrength(company.getStrength());

        return companyDTO;
    }

    public CompanyDTO getCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        CompanyDTO companyDTO = companyOptional.map(this::convertToDTO).orElse(null);
        return companyDTO;
    }

    private CompanyDTO convertToDTO(Company company) {
        // Convert Company entity to CompanyDTO
        CompanyDTO companyDTO = new CompanyDTO();
        // Copy properties from Company entity to CompanyDTO
        // Example: companyDTO.setId(company.getId());
        return companyDTO;
    }
	@Override
	public List<Company> getAllCompanies() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyRepository.findAll().forEach(companyList::add);
		return companyList;
	}

	@Override
	public Company addCompany(Company company) throws ValidationException {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) throws ValidationException {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		company.setId(existingCompanyRecord.getId());
		return companyRepository.save(company);
	}

	@Override
	public void deleteCompany(Long id) {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		companyRepository.deleteById(existingCompanyRecord.getId());
	}

@Override	
 public Company partialUpdateCompany(Long companyId, Map<String, Object> updates) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return null;
        }

        // Update fields based on the provided map
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            switch (field) {
                case "companyName":
                    company.setCompanyName((String) value);
                    break;
                case "email":
                    company.setEmail((String) value);
                    break;
                case "strength":
                    if (value instanceof Integer) {
                        company.setStrength((Integer) value);
                    }
                    break;
                case "webSiteURL":
                    company.setWebSiteURL((String) value);
                    break;
                case "companyCode":
                    company.setCompanyCode((String) value);
                    break;
                default:
                    break;
            }
        }

        if (!isValidCompany(company)) {
            return null;
        }
        return companyRepository.save(company);
    }

	public boolean isValidCompany(@NotNull @Valid Company company) {
        // Validation constraints from Task 1
        if (company.getCompanyName() == null ||
            company.getCompanyName().length() < 5) {
            return false;
        }

        if (company.getEmail() == null ||
            !company.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return false;
        }

        if (company.getStrength() != null && company.getStrength() < 0) {
            return false;
        }

        // Validation constraints from Task 2
        if (company.getCompanyCode() != null &&
            !company.getCompanyCode().matches("^[A-Za-z]{2}\\d{2}[EN]$")) {
            return false;
        }

        // Custom validation from Task 6
        if (company.getStrength() != null && company.getStrength() % 2 != 0) {
            return false;
        }

        // Add additional validation as needed

        return true;
    }

}
