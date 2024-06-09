package peaksoft.lms_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Instructor;

import java.util.List;
@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long> {

    @Query("select i from Instructor i inner join i.companies c where c.id = :id")
    List<Instructor> getAllInstructorsByCompanyId(Long id);

    @Query("select i from Instructor i inner join Course c on i.course.id = c.id where c.id = :id")
    List<Instructor> getAllInstructorsByCourseId(Long id);



}
