package peaksoft.lms_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Group;

import java.util.List;
@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.company.id=:id")
    List<Group> getAllGroupsByCompanyId(Long id);

    @Query("select g from Group g inner join g.courses c where c.id=:id")
    List<Group> getAllGroupsByCourseId(Long id);

}
