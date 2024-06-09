package peaksoft.lms_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Course;

import java.util.List;
@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.company.id=:companyId")
    List<Course> getAllCoursesByCompanyId(Long companyId);

    List<Course> findAllByIdIn(List<Long> coursesIds);

}
