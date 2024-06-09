package peaksoft.lms_spring_boot.service;


import peaksoft.lms_spring_boot.entity.Company;

import java.util.List;

public interface CompanyService {
    void saveCompany(Company company);
    Company getById(Long id);
    List<Company> getAllCompany();
    void  updateById(Long id, Company newCompany);
    void deleteCompanyById(Long id);
}
