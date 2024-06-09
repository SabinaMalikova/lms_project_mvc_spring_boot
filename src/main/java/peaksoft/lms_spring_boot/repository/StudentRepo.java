package peaksoft.lms_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Student;

import java.util.List;
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query("select s from Student s join s.group g where g.id = :groupId")
    List<Student> getAllStudentsByGroupId(Long groupId);
}
