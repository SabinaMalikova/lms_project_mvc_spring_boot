package peaksoft.lms_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.lms_spring_boot.entity.Task;

import java.util.List;
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.lesson.id=:lessonId")
    List<Task> getAllTasksByLessonId(Long lessonId);
}
