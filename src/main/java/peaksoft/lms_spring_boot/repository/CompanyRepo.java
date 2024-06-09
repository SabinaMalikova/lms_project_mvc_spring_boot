package peaksoft.lms_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Company;
@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

}
