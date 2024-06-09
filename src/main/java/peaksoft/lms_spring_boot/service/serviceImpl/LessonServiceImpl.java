package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Lesson;
import peaksoft.lms_spring_boot.entity.Task;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.LessonRepo;
import peaksoft.lms_spring_boot.repository.TaskRepo;
import peaksoft.lms_spring_boot.service.LessonService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepo lessonRepo;
    private final TaskRepo taskRepo;

    @Override
    public void saveLesson(Lesson lesson) {
        lessonRepo.save(lesson);
    }

    @Override
    public Lesson getLessonById(Long id) {
        Lesson lesson = null;
        try {
            lesson = lessonRepo.findById(id).orElseThrow(
                    ()->new MyException("lesson not found!"));
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        return lesson;
    }

    @Override
    public List<Lesson> getAllLessonsByCourseId(Long id) {
        return lessonRepo.getAllLessonsByCourseId(id);
    }

    @Override
    public void updateLessonById(Long id, Lesson newLesson) {
        Lesson oldLesson = getLessonById(id);
        oldLesson.setLessonName(newLesson.getLessonName());
        lessonRepo.save(oldLesson);
    }

    @Override
    public void deleteLessonById(Long id) {
        Lesson lesson = getLessonById(id);
        lesson.setCourse(null);
        List<Task> tasks = lesson.getTasks();
        if (tasks != null){
            for (Task task : tasks) {
                taskRepo.delete(task);
            }
        }
        lessonRepo.delete(lesson);
    }
}
