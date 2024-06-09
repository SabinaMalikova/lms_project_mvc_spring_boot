package peaksoft.lms_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Lesson;

import java.util.List;
@Repository
public interface LessonRepo extends JpaRepository<Lesson, Long> {
    @Query("select l from Lesson l where l.course.id=:id")
    List<Lesson> getAllLessonsByCourseId(Long id);
}
