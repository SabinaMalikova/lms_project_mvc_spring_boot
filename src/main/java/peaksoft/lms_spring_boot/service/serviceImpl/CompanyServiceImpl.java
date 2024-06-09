package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Company;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.CompanyRepo;
import peaksoft.lms_spring_boot.service.CompanyService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;

    @Override
    public void saveCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public Company getById(Long id) {
        Company company = null;
        try {
            company = companyRepo.findById(id).orElseThrow(
                    () -> new MyException("company not found!")
            );
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepo.findAll();
    }

    @Override
    public void updateById(Long id, Company newCompany) {
        Company oldCompany = getById(id);
        oldCompany.setCompanyName(newCompany.getCompanyName());
        oldCompany.setCountry(newCompany.getCountry());
        oldCompany.setAddress(newCompany.getAddress());
        oldCompany.setPhoneNumber(newCompany.getPhoneNumber());
        companyRepo.save(oldCompany);
    }

    @Override
    public void deleteCompanyById(Long id) {
        companyRepo.deleteById(id);
    }

}
